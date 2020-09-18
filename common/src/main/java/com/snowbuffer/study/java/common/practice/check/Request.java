package com.snowbuffer.study.java.common.practice.check;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-15 23:47
 */
public class Request {

    private String str1;

    private String str2;

    private String str3;

    private String expectContent; // |

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getExpectContent() {
        return expectContent;
    }

    public void setExpectContent(String expectContent) {
        this.expectContent = expectContent;
    }
}
