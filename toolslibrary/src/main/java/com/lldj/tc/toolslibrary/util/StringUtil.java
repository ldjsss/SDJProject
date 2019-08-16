package com.lldj.tc.toolslibrary.util;


import android.text.TextUtils;

/**
 * 去除字符串前后各种编码类型的空格
 * update 2019

 */

public class StringUtil {

    /**普通的英文半角空格Unicode编码*/
    private static final int SPACE_32 = 32;

    /**中文全角空格Unicode编码(一个中文宽度)*/
    private static final int SPACE_12288 = 12288;

    /**普通的英文半角空格但不换行Unicode编码(== &nbsp; == &#xA0; == no-break space)*/
    private static final int SPACE_160 = 160;

    /**半个中文宽度(== &ensp; == en空格)*/
    private static final int SPACE_8194 = 8194;

    /**一个中文宽度(== &emsp; == em空格)*/
    private static final int SPACE_8195 = 8195;

    /**四分之一中文宽度(四分之一em空格)*/
    private static final int SPACE_8197 = 8197;

    /**窄空格*/
    private static final int SPACE_8201 = 8201;

    /**
     * 去除字符串前后的空格, 包括半角空格和全角空格(中文)等各种空格, java的string.trim()只能去英文半角空格
     * @param str
     */
    public static String trim(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        char[] val = str.toCharArray();
        int st = 0;
        int len=val.length;
        while ((st < len) && isSpace(val[st])) {
            st++;
        }
        while ((st < len) && isSpace(val[len - 1])) {
            len--;
        }

//        for (int i = val.length; i > 0; i++) {
//            if (TextUtils.isEmpty(val[i] + "")) {
//                str = str.substring(i, i+1);
//            }
//        }
        return ((st > 0) || (len < val.length)) ? str.substring(st, len) : str;
    }

    public static boolean isSpace(char aChar) {
        return aChar == SPACE_32 || aChar == SPACE_12288 || aChar == SPACE_160 || aChar == SPACE_8194
                || aChar == SPACE_8195 || aChar == SPACE_8197 || aChar == SPACE_8201;
    }

    public static String convertString(String str, Boolean beginUp){
        char[] ch = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for(int i=0; i< ch.length; i++){
            if(i == 0 && beginUp){//如果首字母需大写
                sbf.append(charToUpperCase(ch[i]));
            }else{
                sbf.append(charToLowerCase(ch[i]));
            }
        }
        return sbf.toString();
    }

    public static String convertUp(String str){
        char[] ch = str.toCharArray();
        StringBuffer sbf = new StringBuffer();
        for(int i=0; i< ch.length; i++){
            sbf.append(charToUpperCase(ch[i]));
        }
        return sbf.toString();
    }

    /**转大写**/
    private static char charToUpperCase(char ch){
        if(ch <= 122 && ch >= 97){
            ch -= 32;
        }
        return ch;
    }
    /***转小写**/
    private static char charToLowerCase(char ch){
        if(ch <= 90 && ch >= 65){
            ch += 32;
        }
        return ch;
    }


}
