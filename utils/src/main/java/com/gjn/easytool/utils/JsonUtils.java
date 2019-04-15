package com.gjn.easytool.utils;

import android.text.TextUtils;

/**
 * @author gjn
 * @time 2019/4/2 16:38
 */

public class JsonUtils {

    public static String formatString(String json) {
        return formatString("", json);
    }

    public static String formatString(String tab, String json) {
        if (TextUtils.isEmpty(json)) {
            return "";
        }
        json = json.replaceAll("\\s*|\t|\r|\n", "");
        StringBuilder sb = new StringBuilder(tab);
        char current;
        char ln = '\n';
        int line = 0;
        for (int i = 0; i < json.length(); i++) {
            current = json.charAt(i);
            switch (current) {
                //换行 并且加一级tab
                case '{':
                case '[':
                    sb.append(current).append(ln).append(tab);
                    line++;
                    addTab(sb, line);
                    break;
                //换行 并且减一级tab
                case ']':
                case '}':
                    sb.append(ln).append(tab);
                    line--;
                    addTab(sb, line);
                    sb.append(current);
                    break;
                //换行 并且同级tab
                case ',':
                    sb.append(current).append(ln).append(tab);
                    addTab(sb, line);
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    private static void addTab(StringBuilder sb, int lien) {
        for (int i = 0; i < lien; i++) {
            sb.append('\t');
        }
    }
}
