package com.snowbuffer.study.java.common.hutool;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-27 17:28
 */
public class ExcelReader {

    public static void main(String[] args) {
        cn.hutool.poi.excel.ExcelReader reader = ExcelUtil.getReader("/Users/snowbuffer/Downloads/跳板机/塘下中学-学生信息表.xlsx");
        List<List<Object>> readAll = reader.read();
        System.out.println(readAll.size());

        // curl -X GET --header 'Accept: application/json' 'http://localhost:10001/uc/student/familyregister?levelId=181&userName=beibei&phone=1234567'
        // http://localhost:10001/uc/student/familyregister?levelId=1359&userName=beibei&phone=1234567
        String urlTemplate = "curl -X GET --header 'Accept: application/json' 'http://192.168.45.241:10001/uc/student/familyregister?levelId=1359&userName=%s&phone=%s'";
        List<String> urlList = new ArrayList<>();
        int success = 0;
        for (int i = 1; i < readAll.size() - 1; i++) {
            System.out.println(readAll.get(i));
            String userName = readAll.get(i).get(1).toString();
            String phone = readAll.get(i).get(4).toString();
            if (userName == null || phone == null) {
                System.err.println(String.format("userNaem=%s, phone=%s", userName, phone));
                continue;
            }
            userName = userName.trim();
            phone = phone.trim();
            if (userName.equals("") || phone.equals("")) {
                System.err.println(String.format("userNaem=%s, phone=%s", userName, phone));
                continue;
            }

            phone = StrUtil.cleanBlank(phone);
            if (phone.length() == 11) {
                success++;
                urlList.add(String.format(urlTemplate, userName, phone));
            } else {
                System.err.println(String.format("userNaem=%s, phone=%s", userName, phone));
            }

        }


        System.out.println(urlList);
        System.out.println(success);

        System.out.println("最终地址");

        for (String url : urlList) {

            System.out.println(url);
        }

    }
}
