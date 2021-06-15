package verification.verifycode;

import verification.verifycode.support.PicTypeEnum;
import verification.verifycode.support.TagEnum;
import verification.verifycode.support.VerifyCodeData;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-09 10:34
 */
public interface VerifyCode {

    TagEnum tag();

    VerifyCodeData generate(Integer width, Integer height, PicTypeEnum picTypeEnum);
}
