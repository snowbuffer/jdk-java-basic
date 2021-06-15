package verification.verifycode;

import com.google.common.collect.Maps;
import verification.verifycode.impl.KaptchaVerifyCode;
import verification.verifycode.support.TagEnum;

import java.util.Map;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-09 10:43
 */
public class VerifyCodeFactory {

    private static Map<TagEnum, VerifyCode> mapping = Maps.newHashMap();

    static {
        register(new KaptchaVerifyCode());
    }

    public static VerifyCode getVerifyCode(TagEnum tagEnum) {
        return mapping.get(tagEnum);
    }

    public static VerifyCode getDefaultVerifyCode() {
        return mapping.get(TagEnum.KAPTCHA);
    }

    private static void register(VerifyCode verifyCode) {
        mapping.put(verifyCode.tag(), verifyCode);
    }

}
