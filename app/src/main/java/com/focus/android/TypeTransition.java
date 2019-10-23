package com.focus.android;

public class TypeTransition {
    /**
     * int类型的数组转换为String
     * @param arrayint
     * @return arrString
     */
    public static String inttostring(int[] arrayint){
        //定义String
        String arrString = "";
        //将int数组转换为String
        for (int i = 0; i < arrayint.length ; i++) {
            arrString = arrString + Integer.toString(arrayint[i]) +",";
        }
        return arrString;
    }

    /**
     * string转换为int类型的数组
     * @param arrayString
     * @return arrayint
     */
    public static int[] stringtoint(String arrayString){
        //分割字符串
        String[] s1 = arrayString.split(",");
        //定义int数组
        int[] arrayint = new int[s1.length];
        for (int i = 0; i < arrayint.length ; i++) {
            arrayint[i] = Integer.parseInt(s1[i]);
        }
        return arrayint;
    }
    /**
     * 把用逗号隔开的String字符串转换成String数组
     * @param s
     * @return
     */
    public static String[] stringToStringArray(String s){
        return s.split(",");
    }

    /**
     * 把String数组转换成用逗号隔开的String字符串
     * @param sarr
     * @return
     */
    public static String stringArrayToString(String[] sarr){
        String s = "";
        for (int i = 0; i < sarr.length; i++) {
            if (i != sarr.length-1){
                s=s+sarr[i]+',';
            }else {
                s=s+sarr[i];
            }
        }
        return s;
    }
}

