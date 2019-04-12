package com.gjn.easytool.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * @author gjn
 * @time 2019/4/3 17:22
 */

public class StringUtils {

    public static String deleteLastStr(String str, int len) {
        return str.substring(0, str.length() - len);
    }

    public static String deleteLastStr(String str) {
        return deleteLastStr(str, 1);
    }

    public static String changeLastStr(String str, String change, int len) {
        return deleteLastStr(str, len) + change;
    }

    public static String changeLastStr(String str, String change) {
        return changeLastStr(str, change, 1);
    }

    public static String getLastStr(String str) {
        return String.valueOf(str.charAt(str.length() - 1));
    }

    public static String getFirstStr(String str) {
        return String.valueOf(str.charAt(0));
    }

    public static String gsMethodName(String gors, String name){
        return gors + getFirstStr(name).toUpperCase() + name.substring(1);
    }

    public static String getSizeStr(long size) {
        String result;
        if (size >= 1024 * 1024 * 1024) {
            result = doubleFormat(size / 1024 / 1024 / 1024.00) + "GB";
        } else if (size >= 1024 * 1024) {
            result = doubleFormat(size / 1024 / 1024.00) + "MB";
        } else if (size >= 1024) {
            result = doubleFormat(size / 1024.00) + "KB";
        } else {
            result = size + "B";
        }
        return result;
    }

    public static String msecFormat(long msec) {
        String time = "";
        long minutes = Math.abs(msec) / 60 / 1000;
        if (minutes > 60 * 24 * 3) {
            time = "三天以上";
        }
        if (minutes > 60 * 24 * 2) {
            time = "两天前";
        }
        if (minutes > 60 * 24) {
            time = "一天前";
        } else if(minutes > 60){
            time += minutes / 60 + "时";
            time += minutes % 60 + "分";
        }else {
            time += minutes % 60 + "分";
        }
        return time;
    }

    public static String systemFormat(long time) {
        long msec = System.currentTimeMillis() - time;
        String result = dataFormat(time, "yyyy-MM-dd");
        if (msec >= 0) {
            long second = msec / 1000;
            if (second >= 60) {
                long minutes = second / 60;
                if (minutes >= 60) {
                    long hour = minutes / 60;
                    if (hour <= 48) {
                        result = hour + "小时前";
                    }
                } else {
                    result = minutes + "分钟前";
                }
            } else if (second > 3) {
                result = second + "秒前";
            } else {
                result = "刚刚";
            }
        }
        return result;
    }

    public static String doubleFormat(double d) {
        return doubleFormat(d, 2);
    }

    public static String doubleFormat(double d, int len) {
        StringBuilder pattern = new StringBuilder("0.");
        for (int i = 0; i < len; i++) {
            pattern.append("0");
        }
        DecimalFormat format = new DecimalFormat(pattern.toString());
        return format.format(d);
    }

    public static String dataFormat(long time) {
        return dataFormat(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dataFormat(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(time);
    }

    public static boolean isChineseChar(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean hasChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (isChineseChar(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!isChineseChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
