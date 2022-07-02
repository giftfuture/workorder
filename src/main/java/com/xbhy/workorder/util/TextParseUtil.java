package com.xbhy.workorder.util;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.xbhy.workorder.vo.OmegaSearchItem;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created on 2021/8/24.
 *
 * @author zz_dxm
 */
public class TextParseUtil {
    private static final String MONEY_CN = "¥";
    private static final String PRICE_QUERY = "咨询";
    private static final String DURATION_CN = "货期:";
    private static final String PLUS_CLICK = "Click to expand/minimize a list of what others bought with the";

    public static String parseSearchResult(String text) {
        List<OmegaSearchItem> result = parseSearchResultCore(text);
        StringBuilder ret = new StringBuilder();
        for (OmegaSearchItem item : result) {
            ret.append(item.getCode());
            ret.append("\t\t");
            if (!CollectionUtils.isEmpty(item.getDesc())) {
                ret.append(Joiner.on("，").join(item.getDesc()));
            }
            ret.append('\t');
            ret.append(item.getPrice());
            ret.append("\t\t");
            ret.append(item.getDuration());
            ret.append('\n');
        }
        return ret.toString();
    }

    /**
     * 去除开票信息中的空格
     * @param text
     * @return
     */
    public static String parseInvoice(String text) {
        return RegExUtils.replaceAll(RegExUtils.replaceAll(StringUtils.trimToEmpty(text), ":", "："), "[ \\t]+", "");
    }

    /**
     * 无价格和货期
     * @param text
     * @return
     */
    public static String parseSearchResultSimple(String text) {
        List<OmegaSearchItem> result = parseSearchResultCore(text);
        StringBuilder ret = new StringBuilder();
        for (OmegaSearchItem item : result) {
            ret.append(item.getCode());
            ret.append("  ");
            if (!CollectionUtils.isEmpty(item.getDesc())) {
                ret.append(Joiner.on("，").join(item.getDesc()));
            }
//            ret.append('\t');
//            ret.append(item.getPrice());
//            ret.append("\t\t");
//            ret.append(item.getDuration());
            ret.append('\n');
        }
        return ret.toString();
    }

    private static List<OmegaSearchItem> parseSearchResultCore(String text) {
        List<OmegaSearchItem> result = new ArrayList<>();
        List<String> lines = Splitter.on('\n').omitEmptyStrings().splitToList(text);
        Iterator<String> iter = lines.iterator();
        while (iter.hasNext()) {
            String line = iter.next();
            if (StringUtils.equalsAny(line,"产品编号数量产品描述", "面板仪表")) {
                continue;
            }
            if (!StringUtils.contains(line, DURATION_CN)) {
                OmegaSearchItem pre = CollectionUtils.lastElement(result);
                if (Objects.nonNull(pre)) {
                    List<String> ext = pre.getDesc();
                    if (Objects.isNull(ext)) {
                        ext = new ArrayList<>();
                        pre.setDesc(ext);
                    }
                    ext.add(line.trim());
                }
                continue;
            }
            if (StringUtils.contains(line, PLUS_CLICK)) {
                line = StringUtils.replace(line, PLUS_CLICK, "注意修改重复型号");
            }
            String code = StringUtils.substringBefore(line, MONEY_CN).trim();
            if (StringUtils.contains(code, DURATION_CN)) {
                code = StringUtils.substringBefore(line, PRICE_QUERY).trim();
            }
            String price = StringUtils.substringBetween(line, code, DURATION_CN).trim();
            String duration = StringUtils.substringAfter(line, DURATION_CN).trim();
            if (StringUtils.equals(price, PRICE_QUERY)) {
                duration = "";
            }
            if (StringUtils.startsWith(duration, PRICE_QUERY)) {
                duration = PRICE_QUERY;
            }
            OmegaSearchItem item = OmegaSearchItem.builder().code(trimCode(code)).price(price).duration(duration).build();
            result.add(item);
            if (StringUtils.equals(price, PRICE_QUERY)) {
                String desc = StringUtils.substringAfter(line, DURATION_CN).trim();
                item.setDesc(CollectionUtil.toList(desc));
            } else if (StringUtils.equals(duration, PRICE_QUERY)) {
                String desc = StringUtils.substringAfter(line, PRICE_QUERY).trim();
                item.setDesc(CollectionUtil.toList(desc));
            } else if (iter.hasNext()) {
                String desc = iter.next().trim();
                if (StringUtils.equals(desc, "0")) {
                    if (iter.hasNext()) {
                        desc = iter.next();
                    }
                }
                item.setDesc(CollectionUtil.toList(desc.trim()));
            }
        }
        return result;
    }

    /**
     * 型号处理
     *
     * @param src
     * @return
     */
    private static String trimCode(String src) {
        if (!StringUtils.startsWith(src, "注意修改重复型号")) {
            return src;
        }
        src = src.substring(8);
        if (src.length() % 2 == 0) {
            String left = src.substring(0, src.length() / 2);
            String right = src.substring(src.length() / 2);
            if (StringUtils.equals(left, right)) {
                return left;
            }
        }
        return "注意修改重复型号" + src;
    }
    public static void main(String[] args) {
        String text = "产品编号数量产品描述\n" +
                "  FPD2001¥15,015.00货期: 库存\n" +
                "0\n" +
                " ¼ NPT,  0.01 ~ 3 LPM(0.003 ~ 0.8 GPM)  \n" +
                "铝质齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2002¥12,475.00货期: 5 周\n" +
                "0\n" +
                " ¼ NPT, 0.04 ~ 7.5LPM(0.01 ~ 2 GPM)    \n" +
                "铝质齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2003¥13,920.00货期: 2 周\n" +
                "0\n" +
                " ½ NPT, 0.11 ~ 26.4 LPM(0.03 ~ 7GPM)   \n" +
                "铝质齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2004¥15,945.00货期: 5 周\n" +
                "0\n" +
                " ¾ NPT,  0.19 ~ 75 LPM(0.05 ~ 20GPM)   \n" +
                "铝质齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2005¥30,120.00货期: 5 周\n" +
                "0\n" +
                " 1¼ NPT, 1.9 ~ 227 LPM(0.5 ~ 60 GPM)   \n" +
                "铝质齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2001-A¥16,745.00货期: 2 周\n" +
                "0\n" +
                " ¼ NPT, 0.01 ~ 3 LPM(0.003 ~ 0.8 GPM)  \n" +
                "铝质齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2002-A¥14,575.00货期: 5 周\n" +
                "0\n" +
                " ¼ NPT,  0.04 ~ 7.5 LPM(0.01 ~ 2 GPM)    \n" +
                "铝质齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2003-A¥15,945.00货期: 5 周\n" +
                "0\n" +
                " ½  NPT,  0.11 ~ 26.4 LPM(0.03 ~ 7 GPM)   \n" +
                "铝质齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2004-A¥17,475.00货期: 5 周\n" +
                "0\n" +
                " ¾  NPT, 0.19 ~ 75 LPM(0.05 ~ 20 GPM)    \n" +
                "铝质齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2005-A¥31,700.00货期: 5 周\n" +
                "0\n" +
                " 1¼ NPT, 1.9 ~ 227 LPM(0.5 ~ 60 GPM)    \n" +
                "铝质齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2011¥18,005.00货期: 5 周\n" +
                "0\n" +
                " ¼ NPT, 0.01 ~ 3 LPM(0.003 ~ 0.8 GPM)    \n" +
                "303不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2012¥15,550.00货期: 5 周\n" +
                "0\n" +
                " ¼ NPT, 0.04 ~ 7.5 LPM(0.01 ~ 2 GPM)    \n" +
                "303不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2013¥18,345.00货期: 5 周\n" +
                "0\n" +
                " ½ NPT,  0.11 ~ 26.4 LPM(0.03 ~ 7 GPM)     \n" +
                "303不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2014¥22,475.00货期: 5 周\n" +
                "0\n" +
                " ¾ NPT, 0.19 ~ 75 LPM(0.05 ~ 20 GPM)    \n" +
                "303不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2015¥39,595.00货期: 5 周\n" +
                "0\n" +
                " 1¼ NPT, 1.9 ~ 227 LPM(0.5 ~ 60 GPM)    \n" +
                "303不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2011-A¥20,015.00货期: 5 周\n" +
                "0\n" +
                " ¼ NPT,  0.01 ~ 3 LPM(0.003 ~ 0.8 GPM)    \n" +
                "303不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2012-A¥17,650.00货期: 2 周\n" +
                "0\n" +
                " ¼ NPT, 0.04 ~ 7.5 LPM(0.01 ~ 2 GPM)     \n" +
                "303不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2013-A¥20,635.00货期: 5 周\n" +
                "0\n" +
                " ½ NPT, 0.11 ~ 26.4 LPM(0.03 ~ 7 GPM)   \n" +
                "303不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2014-A¥24,020.00货期: 5 周\n" +
                "0\n" +
                " ¾ NPT, 0.19 ~ 75 LPM(0.05 ~ 20 GPM)    \n" +
                "303不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2015-A¥41,195.00货期: 5 周\n" +
                "0\n" +
                " 1¼ NPT, 1.9 ~ 227 LPM(0.5 ~ 60 GPM)   \n" +
                "303不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2021¥20,465.00货期: 5 周\n" +
                "0\n" +
                " ¼ NPT, 0.01 ~ 3 LPM(0.003 ~ 0.8GPM)   \n" +
                "316不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2022¥18,000.00货期: 5 周\n" +
                "0\n" +
                " ¼ NPT, 0.04 ~ 7.5 LPM(0.01 ~ 2 GPM)  \n" +
                "316不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2023¥21,350.00货期: 5 周\n" +
                "0\n" +
                " ½  NPT,  0.11 ~ 26.4 LPM(0.03 ~ 7 GPM)   \n" +
                "316不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2024¥27,100.00货期: 5 周\n" +
                "0\n" +
                " ¾  NPT,  0.19 ~ 75 LPM(0.05 ~ 20 GPM)    \n" +
                "316不锈钢齿轮流量计，提供脉冲输出信号\n" +
                "  FPD2021-A¥22,475.00货期: 5 周\n" +
                "0\n" +
                " ¼  NPT, 0.01 ~ 3 LPM(0.003 ~ 0.8 GPM)    \n" +
                "316不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2022-A¥19,935.00货期: 5 周\n" +
                "0\n" +
                " ¼  NPT,  0.04 ~ 7.5 LPM(0.01 ~ 2 GPM)   \n" +
                "316不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2023-A¥23,335.00货期: 5 周\n" +
                "0\n" +
                " ½  NPT,  0.11 ~ 26.4 LPM(0.03 ~ 7 GPM)  \n" +
                "316不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "  FPD2024-A¥28,600.00货期: 5 周\n" +
                "0\n" +
                " ¾  NPT,  0.19 ~ 75 LPM(0.05 ~ 20 GPM)    \n" +
                "316不锈钢齿轮流量计，提供4 ~ 20 mA输出信号\n" +
                "配件\n" +
                "Click to expand/minimize a list of what others bought with theDPF701DPF701咨询  货期:   有关脉冲输入6位数字面板式安装仪表的流速或累加流量计，请访问omega.com/dpf701\n" +
                "  FPD2000-CONNECTOR¥485.00货期: 5 周\n" +
                "0\n" +
                " 脉冲输出FPD2000型号流量计的备用6针连接器\n" +
                "  FPD2000A-CONNECTOR¥490.00货期: 2 周\n" +
                "0\n" +
                " 4 ~ 20 mA输出FPD2000-A型号流量计的备用3针连接器";
        System.out.println(text);
        System.out.println(parseSearchResultSimple(text));
    }
}
