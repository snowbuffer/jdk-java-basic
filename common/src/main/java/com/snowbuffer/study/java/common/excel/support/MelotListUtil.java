package com.snowbuffer.study.java.common.excel.support;


import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by helei3669 on 18/11/23 下午8:15.
 */
public class MelotListUtil {


    public static <T> List<List<T>> split(List<T> list, int unitSize) {
        ArrayList<List<T>> al = new ArrayList<List<T>>();
        if (isEmpty(list)) {
            return al;
        } else if (list.size() < unitSize) {
            al.add(list);
            return al;
        } else {
            int step = 0;
            while (step * unitSize < list.size()) {

                List<T> templist = new ArrayList<T>();
                int floorsize = (step * unitSize + unitSize < list.size() ? step * unitSize
                        + unitSize : list
                        .size());
                // list copy
                for (int i = step * unitSize; i < floorsize; i++) {
                    templist.add(list.get(i));
                }

                al.add(templist);
                step++;
            }
            return al;
        }
    }


    public static String getStringIdListString(List<String> list) {
        if (isEmpty(list)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String st : list) {
            sb.append("," + st);
        }
        sb.append(",");

        return sb.toString();
    }

    public static String getIdListString(List<Long> list) {
        if (isEmpty(list)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Long st : list) {
            sb.append("," + st);
        }
        sb.append(",");

        return sb.toString();
    }


    public static List<String> splitStringToList(String str) {
        List<String> rs = new ArrayList();
        if (StringUtils.isEmpty(str)) {
            return rs;
        }
        String[] arr = str.split(",");
        for (String st : arr) {
            if (StringUtils.isEmpty(StringUtils.trim(st))) {
                continue;
            }
            rs.add(StringUtils.trim(st));
        }
        return rs;
    }

    public static List<Long> splitStringToLongList(String str) {
        List<Long> rs = new ArrayList();
        if (StringUtils.isEmpty(str)) {
            return rs;
        }
        String[] arr = str.split(",");
        for (String st : arr) {
            if (StringUtils.isEmpty(st)) {
                continue;
            }
            rs.add(Long.valueOf(StringUtils.trim(st)));
        }
        return rs;
    }


    public static String getIntegerListString(List<Integer> list) {
        if (isEmpty(list)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Integer st : list) {
            sb.append("," + st);
        }
        sb.append(",");

        return sb.toString();
    }


    public static List<Integer> splitStringToIntegerList(String str) {
        List<Integer> rs = new ArrayList();
        if (StringUtils.isEmpty(str)) {
            return rs;
        }
        String[] arr = str.split(",");
        for (String st : arr) {
            if (StringUtils.isEmpty(st)) {
                continue;
            }
            rs.add(Integer.valueOf(StringUtils.trim(st)));
        }
        return rs;
    }


    public static List<Long> numberListToLongList(List<Number> numberList) {
        if (numberList == null) {
            return null;
        }
        List<Long> longList = new ArrayList<Long>(numberList.size());
        for (Number number : numberList) {
            longList.add(number.longValue());
        }
        return longList;
    }

    public static List<Integer> numberListToIntegerList(List<Number> numberList) {
        if (numberList == null) {
            return null;
        }
        List<Integer> integerList = new ArrayList<Integer>(numberList.size());
        for (Number number : numberList) {
            integerList.add(number.intValue());
        }
        return integerList;
    }


    public static boolean isEmpty(List<?> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }


    public static List<Number> longListToNumberList(List<Long> longList) {
        if (longList == null) {
            return null;
        }
        List<Number> numberList = new ArrayList<Number>();
        for (Long longx : longList) {
            numberList.add(longx);
        }
        return numberList;
    }


    public static <T> T last(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static <T> T first(List<T> list) {

        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
