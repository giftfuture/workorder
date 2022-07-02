package com.xbhy.workorder.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xbhy.workorder.config.BaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created on 2021/3/31.
 *
 * @author zz_dxm
 */
@Slf4j
@Service
public class DataExchangeUtil {


    private final static Pattern COUNT_PATTERN = Pattern.compile("(?<count>\\d+)");
    private final static Pattern LENGTH_PATTERN = Pattern.compile("(?<len>\\d+(\\.\\d+)?)米");
    private final static Pattern WIRE_PATTERN = Pattern.compile("^(?<wire>[A-Z0-9a-z\\-/]+)-(?<len>\\d+(\\.\\d+)?)米$");
    private final static Pattern HANZI_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5]+$");


    private static Map<String, String> unitMap = MapUtil.builder("B", "本")
            .put("T", "条")
            .put("G", "个")
            .put("H", "盒")
            .put("J", "卷")
            .put("M", "米")
            .put("Z", "只")
            .put("TAO", "套")
            .build();
    private static String defaultUnit = "件";

    private static Pattern unitPattern = Pattern.compile("(\\d+(\\.\\d+)?)\\S*");

    public static String simpleCalc(String input) {
        if (StringUtils.isNotBlank(input)) {
            List<List<String>> lines = Splitter.on("\n").omitEmptyStrings().splitToStream(input)
                    .filter(StringUtils::isNotBlank).map(s -> left(s, '='))
                    .map(s -> Splitter.onPattern("[\\s\\u00A0¥]").omitEmptyStrings().splitToList(s))
                    .collect(Collectors.toList());
            int maxLen = lines.stream().mapToInt(List::size).max().orElse(0);
            if (maxLen < 3) {
                double sum = 0;
                double multi = 1;
                StringBuilder sb = new StringBuilder();
                if (maxLen == 1) {
                    for (List<String> num : lines) {
                        if (num.size() == 0) {
                            continue;
                        }
                        String numStr = num.get(0);
                        if (isParsable(numStr)) {
                            sum += parseDouble(numStr);
                            sb.append(NumberUtil.decimalFormat("#.00", parseDouble(numStr))).append('\n');
                        } else {
                            if (numStr.startsWith("*") && numStr.length() > 1 && isParsable(numStr.substring(1))) {
                                multi = parseDouble(numStr.substring(1));
                            } else {
                                return numStr + "@@@格式非法@@@";
                            }
                        }
                    }
                } else {
                    for (List<String> line : lines) {
                        if (line.size() == 2) {
                            String numStr = line.get(1);
                            if (isParsable(numStr)) {
                                sum += parseDouble(numStr);
                                sb.append(line.get(0)).append('\t').append(NumberUtil.decimalFormat("#.00", parseDouble(numStr))).append('\n');
                            } else {
                                return numStr + "@@@格式非法@@@";
                            }
                        } else {
                            String numStr = line.get(0);
                            if (isParsable(numStr)) {
                                sum += parseDouble(numStr);
                                sb.append(NumberUtil.decimalFormat("#.00", parseDouble(numStr))).append('\n');
                            } else {
                                if (numStr.startsWith("*") && numStr.length() > 1 && isParsable(numStr.substring(1))) {
                                    multi = parseDouble(numStr.substring(1));
                                } else {
                                    return numStr + "@@@格式非法@@@";
                                }
                            }
                        }
                    }
                }
                sb.append("合计：").append(NumberUtil.decimalFormat("#.00", sum)).append('\n');
                if (multi != 1.0) {
                    sb.append('*').append(multi).append('\n');
                    sum *= multi;
                    sb.append("合计2：").append(NumberUtil.decimalFormat("#.00", sum));
                }
                return sb.toString();
            } else {
                return "@@@格式非法@@@";
            }
        }
        return input;
    }

    public static String miniCalc(String input) {
        Integer totalCount = 0;
        if (StringUtils.isNotBlank(input)) {
            StringBuilder sb = new StringBuilder();
            List<List<String>> lines = Splitter.on("\n").omitEmptyStrings().splitToStream(input)
                    .filter(StringUtils::isNotBlank).map(s -> left(s, '*'))
                    .map(s -> Splitter.onPattern("[\\s\\u00A0]").omitEmptyStrings().splitToList(s))
                    .collect(Collectors.toList());
            Map<String, Double> wireMap = Maps.newLinkedHashMap();
            Map<String, Integer> plugMap = Maps.newLinkedHashMap();
            List<List<String>> printLines = Lists.newArrayList();
            for (List<String> line : lines) {
                if (line.size() > 0 && line.get(0).startsWith("合计")) {
                    continue;
                }
                if (line.size() != 2) {
                    return "@@@格式非法@@@";
                }
                // 数量
                Matcher mRight = COUNT_PATTERN.matcher(line.get(1));
                if (!mRight.find()) {
                    return "@@@格式非法@@@";
                }
                Integer count = Integer.parseInt(mRight.group("count"));

                // 型号
                List<String> modelNumbers = Splitter.on('+').omitEmptyStrings().splitToList(line.get(0));
                if (modelNumbers.size() == 0) {
                    return "@@@格式非法@@@";
                }
                for (String modelNumber : modelNumbers) {
                    Matcher mLeft = WIRE_PATTERN.matcher(modelNumber);
                    if (mLeft.find()) {
                        // 线材
                        Double len = Double.parseDouble(mLeft.group("len"));
                        Double totalLen = len * count;
                        String wire = mLeft.group("wire");
                        if (wireMap.containsKey(wire)) {
                            wireMap.compute(wire, (k, v) -> v + totalLen);
                        } else {
                            wireMap.put(wire, totalLen);
                        }
                    } else {
                        mLeft = HANZI_PATTERN.matcher(modelNumber);
                        if (mLeft.find()) {
                            //纯汉字
                            continue;
                        } else {
                            // 型号
                            if (plugMap.containsKey(modelNumber)) {
                                plugMap.compute(modelNumber, (k, v) -> v + count);
                            } else {
                                plugMap.put(modelNumber, count);
                            }
                        }
                    }
                }
//                Matcher mLeft = LENGTH_PATTERN.matcher(line.get(0));
//                if (!mLeft.find()) {
//                    return "@@@格式非法@@@";
//                }
                printLines.add(line);
                totalCount += count;
            }
            for (Map.Entry<String, Double> wire : wireMap.entrySet()) {
                sb.append(wire.getKey()).append(NumberUtil.decimalFormat("\t合计：0.###米", wire.getValue())).append("\n");
            }

            for (Map.Entry<String, Integer> plug : plugMap.entrySet()) {
                sb.append(plug.getKey()).append(NumberUtil.decimalFormat("\t合计：0.###个", plug.getValue())).append("\n");
            }
            sb.append("\n").append(printLines.stream().map(line -> line.stream().collect(Collectors.joining("  "))).collect(Collectors.joining("\n")));
            sb.append(NumberUtil.decimalFormat("\n合计：0.###条", totalCount)).append("\n\n");
            return sb.toString();
        }
        return "";
    }

    public static String exchangeTextFormat(String input) throws IOException {
        if (StringUtils.isNotBlank(input)) {
            List<List<String>> lines = Splitter.on("\n").omitEmptyStrings().splitToStream(input)
                .filter(StringUtils::isNotBlank)
                .map(s -> RegExUtils.replacePattern(s, "=\\s*\\d+(\\.\\d+)?", " "))
                .map(s -> Splitter.onPattern("[\\s\\u00A0¥*]").omitEmptyStrings().splitToList(s))
                .collect(Collectors.toList());

            StringBuilder sb = new StringBuilder();
            List<List<String>> results = new ArrayList<>(lines.size());
            double sum = 0.0;
            double discount = 0.0;
            // 折扣比例
            double discountRate = 1.0;
            // 运费
            double freight = 0.0;
            // 税率
            double taxRate = 0.0;
            for (List<String> datum : lines) {
                String memo = "";
                List<String> memos = Collections.EMPTY_LIST;
//                String duration = datum.get(datum.size()-1);
//                if (StringUtils.endsWithAny(duration,"月", "周", "天", "现货", "库存")) {
//                    datum = datum.subList(0, datum.size() - 1);
//                } else {
//                    duration = "";
//                }
                if (datum.size() > 3) {
                    if (isParsable(datum.get(1)) && isParsable(datum.get(3)) && !isParsable(datum.get(2))) {
                        // 型号 数量 量词 单价
                        if (datum.size() > 4) {
                            if (!isParsable(datum.get(4))) {
                                // 型号 数量 量词 单价 备注/货期
                                memos = datum.subList(4, datum.size());
                                datum = Arrays.asList(datum.get(0), datum.get(1) + datum.get(2), datum.get(3));
                            } else {
                                // 型号 数量 量词 单价 折扣
                                if (datum.size() > 5) {
                                    memos = datum.subList(5, datum.size());
                                }
                                datum = Arrays.asList(datum.get(0), datum.get(1) + datum.get(2),
                                        NumberUtil.roundStr(parseDouble(datum.get(3)) * parseDouble(datum.get(4)), 2));
                            }
                        } else {
                            datum = Arrays.asList(datum.get(0), datum.get(1) + datum.get(2), datum.get(3));
                        }
                    } else if (isParsable(datum.get(2)) && isParsable(datum.get(3))) {
                        // 型号 数量 单价 折扣
                        if (datum.size() > 4) {
                            memos = datum.subList(4, datum.size());
                        }
                        datum = Arrays.asList(datum.get(0), datum.get(1),
                                NumberUtil.roundStr(parseDouble(datum.get(2)) * parseDouble(datum.get(3)), 2));
                    }
                }
                if (datum.size() != 3) {
                    if (datum.size() == 1) {
                        if (datum.get(0).startsWith("-")) {
                            discount += parseDouble(datum.get(0));
                        } else if (datum.get(0).startsWith("+")) {
                            freight += parseDouble(datum.get(0));
                        } else if (datum.get(0).startsWith("/")) {
                            taxRate = parseDouble(datum.get(0).substring(1));
                        } else if (isParsable(datum.get(0))) {
                            discountRate = parseDouble(datum.get(0));
                        }
                        continue;
                    } else if (datum.size() == 2){
                        log.error("invalid data {}", Joiner.on("\t").join(datum));
                        return Joiner.on("\t").join(datum) + "\t\t@@@格式非法@@@";
                    } else {
                        memos = datum.subList(3, datum.size());
                        datum = datum.subList(0, 3);
                    }
                }
                if (CollectionUtils.isNotEmpty(memos)) {
                    memo = Joiner.on(" ").join(memos);
                    memo = RegExUtils.replacePattern(memo, "[01]\\.\\d{2}\\s+[01]\\.\\d{2}\\s*\\S*$", "");
                }
                List<String> result = new ArrayList<>(5);
                results.add(result);
                // 0 型号
                result.add(datum.get(0));
                String count = datum.get(1);
                String unit = defaultUnit;
                Matcher matcher = unitPattern.matcher(count);
                if (matcher.matches()) {
                    String num = matcher.group(1);
                    if (num.length() < count.length()) {
                        unit = count.substring(num.length()).toUpperCase();
                        if (unitMap.containsKey(unit)) {
                            unit = unitMap.get(unit);
                        }
                    }
                    count = num;
                } else {
                    log.error("invalid count {}", count);
                    return Joiner.on("\t").join(datum) + "\t\t@@@格式非法@@@";
                }
                // 1 数量
                result.add(count);
                // 2 单位
                result.add(unit);
                // 3 单价
                result.add(NumberUtil.decimalFormat("#.00", parseCount(datum.get(2))));
                // 4 小计
                Double amount = parseCount(count)* parseCount(datum.get(2));
                result.add(NumberUtil.roundStr(amount, 2));
                // 5 货期/备注
                if (StringUtils.isNotBlank(memo)) {
                    result.add(memo);
                }
            }
            File recordFile = new File(BaseConfig.historyFilePath);
            for (List<String> result : results) {
                FileUtils.writeStringToFile(recordFile,
                        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ','
                                + Joiner.on(',').join(result) + '\n', Charset.defaultCharset(), true);
                // 单价
                double unitPrice = BigDecimal.valueOf(parseDouble(result.get(3)) * discountRate)
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (taxRate > 0.0) {
                    unitPrice = BigDecimal.valueOf(unitPrice / taxRate)
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
//                if (Double.compare(unitPrice, 100d) > 0) {
                    // 舍弃小数
//                    unitPrice = Math.round(unitPrice);
//                }
                result.set(3, NumberUtil.roundStr(unitPrice, 2));
                // 小计
                Double amount = unitPrice * parseCount(result.get(1));
                result.set(4, NumberUtil.roundStr(amount, 2));
                sum += amount;
            }

            for (List<String> result : results) {
                result = new ArrayList<>(result);
                result.set(3, "¥" + result.get(3));
//                result.remove(4);
                sb.append(Joiner.on("\t").join(result));
                sb.append("\n");
            }
            sb.append("\t\t合计\t").append(NumberUtil.roundStr(sum, 2));
            if (freight > 0) {
                sb.append("\n\t\t运费\t").append(NumberUtil.decimalFormat("#.00", freight));
                sb.append("\n\t\t加运费后合计\t").append(NumberUtil.roundStr(sum + freight, 2));
            }
            if (discount < 0) {
                sb.append("\n\t\t优惠\t").append(NumberUtil.decimalFormat("#.00", discount));
                sb.append("\n\t\t优惠后合计\t").append(NumberUtil.roundStr(sum + freight + discount, 2));
            }

//            if (taxRate > 0) {
//                sb.append("\n\t\t\t/").append(NumberUtil.decimalFormat("#.00", taxRate));
//                sb.append("\n\t\t未税合计\t").append(BigDecimal.valueOf((sum + freight + discount)/taxRate)
//                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//            }
            sb.append("\n\n");
            for (List<String> result : results) {
                result = new ArrayList<>(result);
                result.add(3, "*");
                result.add(5, "=");
                sb.append(result.get(0)).append("   ")
                        .append(result.get(1)).append(result.get(2)).append(" ")
                        .append(result.get(3)).append(" ")
                        .append(result.get(4)).append(" ")
                        .append(result.get(5)).append(" ")
                        .append(result.get(6));
                if (result.size() > 7) {
                    sb.append(" ").append(result.get(7));
                }
                sb.append("\n");
            }
            sb.append("合计").append(NumberUtil.roundStr(sum, 2));
            if (freight > 0) {
                sb.append("\n运费").append(NumberUtil.decimalFormat("#.00", freight));
                sb.append("\n加运费后合计").append(NumberUtil.roundStr(sum + freight, 2));
            }
            if (discount < 0) {
                sb.append("\n优惠").append(NumberUtil.roundStr(discount, 2));
                sb.append("\n优惠后合计").append(NumberUtil.roundStr(sum + discount + freight, 2));
            }

//            if (taxRate > 0) {
//                sb.append("\n/").append(NumberUtil.decimalFormat("#.00", taxRate));
//                sb.append("\n未税合计").append(NumberUtil.decimalFormat("#.00", (sum + discount + freight)/taxRate));
//            }
            sb.append("\n\n");
            for (List<String> result : results) {
                sb.append(result.get(0)).append(' ').append(result.get(1));
                sb.append('\n');
            }
            sb.append("\n");
            for (List<String> result : results) {
                sb.append(result.get(0)).append("  ").append(result.get(1)).append(result.get(2));
                sb.append('\n');
            }
            sb.append("\n");
            return sb.toString();
        }
        return input;
    }

    private static String left(String src, int searchChar) {
        if (StringUtils.isBlank(src)) {
            return src;
        }
        int idx = StringUtils.lastIndexOf(src, searchChar);
        if (idx > 0) {
            return src.substring(0, idx);
        }
        return src;
    }

    private static Double parseCount(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        if (StringUtils.contains(input, '/')) {
            return parseDouble(StringUtils.substringBefore(input, "/")) / parseDouble(StringUtils.substringAfter(input, "/"));
        } else {
            return parseDouble(input);
        }
    }
    
    private static Double parseDouble(String raw) {
        if (StringUtils.isBlank(raw)) {
            return null;
        }
        return Double.parseDouble(raw.replaceAll(",", ""));
    }
    
    private static Boolean isParsable(String raw) {
        if (StringUtils.isBlank(raw)) {
            return null;
        }
        return NumberUtils.isParsable(raw.replaceAll(",", ""));
    }

    /**
     * 输入数据来源为 https://cn.omega.com/shop/additem.asp 购物车报价 粘贴
     * @param input
     * @return
     */
    public static String exchangeInput(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        }
        StringBuilder sb = new StringBuilder();
        List<String> lines = Splitter.on('\n').trimResults().omitEmptyStrings().splitToList(input);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (NumberUtils.isDigits(line)) {
                // 数量
                int amount = Integer.parseInt(line);
                i++;
                if (i >= lines.size()) {
                    log.error("invalid end of input {}", line);
                    break;
                }
                line = lines.get(i);
                List<String> elements = Splitter.onPattern("[\\s\\u00A0¥*]").omitEmptyStrings().splitToList(line);
                // 型号 数量 单价 总价 货期 delete
                if (elements.size() >= 4 && isParsable(elements.get(1)) && isParsable(elements.get(2))) {
                    double discount = parseDouble(elements.get(2)) / (amount * parseDouble(elements.get(1))) * 0.95;
                    // 型号 数量  单价  单项合计    货期  叠加折扣。。。
                    sb.append(elements.get(0)).append('\t')
                            .append(amount).append('\t')
                            .append(elements.get(1)).append('\t')
                            .append(elements.get(3)).append('\t')
                            .append(NumberUtil.roundStr(discount, 2)).append("  ")
                            .append(NumberUtil.roundStr(discount*1.13, 2)).append("\n");
                } else {
                    log.error("invalid input {} {}", amount, line);
                    i--;
                }
            }
        }
        String output = sb.toString();
        if (StringUtils.isNotBlank(output)) {
            return output;
        }
        return "@@@格式非法@@@";
    }
}
