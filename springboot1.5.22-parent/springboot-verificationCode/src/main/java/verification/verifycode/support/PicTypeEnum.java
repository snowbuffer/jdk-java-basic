package verification.verifycode.support;

/**
 * Description:
 * <p>
 * 验证码：https://www.jianshu.com/p/22004d424183
 * <p>
 * data:,文本数据
 * data:text/plain,文本数据
 * data:text/html,HTML代码
 * data:text/html;base64,base64编码的HTML代码
 * data:text/css,CSS代码
 * data:text/css;base64,base64编码的CSS代码
 * data:text/javascript,Javascript代码
 * data:text/javascript;base64,base64编码的Javascript代码
 * data:image/gif;base64,base64编码的gif图片数据
 * data:image/png;base64,base64编码的png图片数据
 * data:image/jpeg;base64,base64编码的jpeg图片数据
 * data:image/x-icon;base64,base64编码的icon图片数据
 *
 * @author cjb
 * @since 2021-06-09 10:36
 */
public enum PicTypeEnum {

    PNG("png", "data:image/png;base64,"),

    JPEG("jpeg", "data:image/jpeg;base64,"),

    未知("未知", "未知"),
    ;

    private String code;

    private String dataPrefix;

    PicTypeEnum(String code, String dataPrefix) {
        this.code = code;
        this.dataPrefix = dataPrefix;
    }

    public String getCode() {
        return code;
    }

    public String getDataPrefix() {
        return dataPrefix;
    }

    public static PicTypeEnum getByCode(String code) {
        for (PicTypeEnum value : values()) {
            if (value.code.equals(code.toLowerCase())) {
                return value;
            }
        }
        return 未知;
    }
}
