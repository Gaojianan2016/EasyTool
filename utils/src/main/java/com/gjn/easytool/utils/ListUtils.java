package com.gjn.easytool.utils;

import java.util.List;

/**
 * @author gjn
 * @time 2019/4/4 10:03
 */

public class ListUtils {

    public static boolean isEmpty(List list){
        return list == null || list.size() == 0;
    }

    /**
     * 最少需要2个数据才是list
     */
    public static boolean isList(List list) {
        return list != null && list.size() > 1;
    }


}
