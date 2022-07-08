package com.xbhy.workorder.util;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: lin kq
 * @Date: 2020/11/27
 * @Version: 1.0.0
 */
public class ArgumentUtil {

    public static boolean notEmpty(String... args) {
        for(String arg : args) {
            if(StringUtils.isEmpty(arg)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNull(Object... args) {
        for(Object arg : args) {
            if(arg == null) {
                return false;
            }
        }
        return true;
    }

    public static Integer[] insertIntArr(Integer[] src, Integer e) {
        if(src == null || src.length == 0) {
            Integer[] target = {e};
            return target;
        } else {
            Integer[] target = new Integer[src.length + 1];
            System.arraycopy(src, 0, target, 0, src.length);
            target[src.length] = e;
            return target;
        }
    }

    public static Integer[] removeIntArr(Integer[] src, Integer e) {
        if(src != null || src.length != 0 && e != null) {
            List<Integer> list = new ArrayList<>(src.length);
            Collections.addAll(list, src);
            if(list.contains(e)) {
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Integer curVal = iterator.next();
                    if (e.equals(curVal)) {
                        list.remove(curVal);
                        break;
                    }
                }
            }
            return list.toArray(new Integer[list.size()]);
        } else {
            return src;
        }
    }
    /**
     * 排列组合（字符重复排列）<br>
     * 内存占用：需注意结果集大小对内存的占用（list:10位，length:8，结果集:[10 ^ 8 = 100000000]，内存占用:约7G）
     * @param list 待排列组合字符集合
     * @param length 排列组合生成的字符串长度
     * @return 指定长度的排列组合后的字符串集合
     * @author www@yiynx.cn
     */
    public static List<String> permutation(List<String> list, int length) {
        Stream<String> stream = list.stream();
        for (int n = 1; n < length; n++) {
            stream = stream.flatMap(str -> list.stream().map(str::concat));
        }
        return stream.collect(Collectors.toList());
    }
    /**
     * 排列组合(字符不重复排列)<br>
     * 内存占用：需注意结果集大小对内存的占用（list:10位，length:8，结果集:[10! / (10-8)! = 1814400]）
     * @param list 待排列组合字符集合(忽略重复字符)
     * @param length 排列组合生成长度
     * @return 指定长度的排列组合后的字符串集合
     * @author www@yiynx.cn
     */
    public static List<String> permutationNoRepeat(List<String> list, int length) {
        Stream<String> stream = list.stream().distinct();
        for (int n = 1; n < length; n++) {
            stream = stream.flatMap(str -> list.stream()
                    .filter(temp -> !str.contains(temp))
                    .map(str::concat));
        }
        return stream.collect(Collectors.toList());
    }
    public static List<String> buildContentCombinations(String instr, StringBuffer outstr, int index) {
        List<String> contentSet = Lists.newArrayList();
        for (int i = index; i < instr.length(); i++) {
            outstr.append(instr.charAt(i));
            contentSet.add(outstr.toString());
            contentSet.addAll(buildContentCombinations(instr, outstr, i + 1));
            outstr.deleteCharAt(outstr.length()-1);
        }
        return contentSet;
    }
    public static List<String> buildRemarkText(String instr, List<String> contentSet){
        List<String> remarkList = Lists.newArrayList();
        for(String content: contentSet){
            final String[] data = {instr};
            Arrays.stream(content.split("")).forEach(e->{
                data[0] = data[0].replace(e, "");
            });
            remarkList.add(data[0]);
        }
        return remarkList;
    }
    /**
     * 生成字符串的所有组合
     * @param
     * @param
     * @param index
     */
   /* public static List<List<String>> combine(String instr, StringBuffer outstr, int index) {
        List<List<String>> result = Lists.newArrayList();
        List<String> contextList = Lists.newArrayList();
        List<String> remarkList = Lists.newArrayList();
        for (int i = index; i < instr.length(); i++) {
            outstr.append(instr.charAt(i));
            final String[] data = {instr};
            contextList.add(outstr.toString());
            Arrays.stream(outstr.toString().split("")).forEach(e ->{
                data[0] = data[0].replace(e, "");
            });
            remarkList.add(data[0]);
            //System.out.println(outstr.toString() + "   " + data[0]);
            combine(instr, outstr, i + 1);
            outstr.deleteCharAt(outstr.length() - 1);
        }
        result.add(contextList);
        result.add(remarkList);
        return result;
    }*/


    void combine(List<String> keyWords, Set<String> contentList,Set<String> remarkList, int index){
        //List<String> outstr = Lists.newArrayList();
        for (int i = index; i < keyWords.size(); i++){
            contentList.add(keyWords.get(i));
            //outstr.append(keyWords.get(i));
           //System.out.println(outstr);
            //keyWords.stream().forEach(e->{data.remove(e);});
            combine(keyWords, contentList,remarkList, i + 1);
            contentList.remove(contentList.size() - 1);
        }
    }

//    Set getCombinations(List<String> keyWords, Set<String> remarkList, int index) {
//        Set combinations = new HashSet();
//        for (int i = index; i < keyWords.size(); i++) {
//            remarkList.add(keyWords.get(i));
//            combinations.add(outstr.toString());
//            combinations.addAll(getCombinations(keyWords, outstr, i + 1));
//            outstr.deleteCharAt(outstr.length()-1);
//        }
//        return combinations;
//    }
    /**
     * 生成字符串的所有组合
     * @param
     * @param
     * @return
     */
//    public static List<List<String>> combine(List<String> keyWords,int index) {
//        //StringBuffer outstr = new StringBuffer();
//        List<String> outstr = Lists.newArrayList();
//        List<String> data = Lists.newArrayList();
//        data.addAll(keyWords);
//        List<List<String>> result = Lists.newArrayList();
//        List<List<String>> contextList = Lists.newArrayList();
//        List<List<String>> remarkList = Lists.newArrayList();
//        for (int i = index; i < keyWords.size(); i++) {
//            outstr.add(keyWords.get(i));
//           // final String[] data = {instr};
//            contextList.add(outstr.toString());
//            outstr.stream().forEach(e->{data.remove(e);});
////            Arrays.stream(outstr.toString().split("")).forEach(e ->{
////                data[0] = data[0].replace(e, "");
////            });
//            remarkList.add(data[0]);
//            //System.out.println(outstr.toString() + "   " + data[0]);
//            combine(keyWords, i + 1);
//            //outstr.deleteCharAt(outstr.length() - 1);
//        }
//        result.add(contextList);
//        result.add(remarkList);
//        return result;
//    }

//    List<List<String>> contentList = Lists.newArrayList();
//    List<List<String>> remarkList = Lists.newArrayList();
//            for(String contentIdx:contentIdxList){
//        List<String> content = Lists.newArrayList();
//        contentIdx.chars().forEach(e->{
//            content.add(subKeyWords.get(e));
//        });
//        contentList.add(content);
//    }
//            orderInfo.setContentList(contentList);
//            for(String remarkIdx:remarkIdxList){
//        List<String> remark = Lists.newArrayList();
//        remarkIdx.chars().forEach(e->{
//            remark.add(subKeyWords.get(e));
//        });
//        remarkList.add(remark);
//    }
//    orderInfo.setRemarkList(remarkList);
    public static void main(String[] args) {
        String instr = "0123456789";
    }
}
