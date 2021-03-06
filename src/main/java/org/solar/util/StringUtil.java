package org.solar.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.font.TextAttribute.UNDERLINE;

/**
 * Created by xianchuanwu on 2017/9/22.
 */
public class StringUtil {
    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //裁剪字符串 如果长度小于需要裁剪的长度 则返回原字符串
    public static String substring(String s,int len){
        if (s==null){
            return null;
        }
        if (s.length()<=len){
            return s;
        }
        return s.substring(0,len);
    }

    public static boolean isNotEmpty(Object str) {
        if (str==null||"".equals(str)){
            return false;
        }
        return true;
    }
    public static boolean isEmpty(Object str) {
        if (str==null||"".equals(str)){
            return true;
        }
        return false;
    }
    /**
     * 下划线转驼峰法
     * @param line 源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
    public static String underline2Camel(String line){
       return underline2Camel(line,true);
    }
    /**
     * 驼峰法转下划线
     * @param
     * @return 转换后的字符串
     */
    public static String camel2Underline(String param){

        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static String numStringAddNum(String str,int num) {
        if (str==null){
            return null;
        }
        if (!isNumeric(str)){
            return null;
        }
        if (num==0){
            return str;
        }
        String resultStr=Integer.valueOf(str)+num+"";
        int length=str.length()-resultStr.length();
        for (int i = 0; i <length ; i++) {
            resultStr="0"+resultStr;
        }
        return resultStr;
    }
    public static String numConvert3Length(int num) {
        if (num<10){
            return "00"+num;
        }
        if (num<100){
            return "0"+num;
        }
        return num+"";
    }

    public static String removeEmoji(String str){
        return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
    }

}
