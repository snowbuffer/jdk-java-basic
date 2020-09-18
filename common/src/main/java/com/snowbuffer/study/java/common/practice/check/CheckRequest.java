package com.snowbuffer.study.java.common.practice.check;

import java.lang.reflect.Field;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-15 23:48
 */
public class CheckRequest {


    public static void main(String[] args) throws IllegalAccessException {
        Request request = new Request();
        request.setStr1("1");
        request.setStr2("");
        request.setStr3("3");
        request.setExpectContent("1|2");

        check(request);
    }

    public static boolean check(Request request) throws IllegalAccessException {

        String expectContent = request.getExpectContent();
        int length = expectContent.split("\\|").length;

        StringBuilder actualContent = new StringBuilder();
        for (int i = 1; i <= length; i++) {
            Field[] declaredFields = Request.class.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.getName().equals("str" + i)) {
                    declaredField.setAccessible(true);
                    Object fieldValue = declaredField.get(request);
                    String tempFieldValue = null;
                    if (fieldValue != null) {
                        tempFieldValue = fieldValue.toString();
                    }
                    actualContent.append(tempFieldValue);
                    break;
                }
            }
            if (i == length) {
                break;
            }
            actualContent.append("|");
        }

        if (expectContent.equals(actualContent.toString())) {
            System.out.println("对比成功");
            return true;
        }

        System.out.println(String.format("expectContent: %s, actualContent: %s", expectContent, actualContent));
        return false;
    }

}
