package verification.controller;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import verification.verifycode.VerifyCodeFactory;
import verification.verifycode.support.PicTypeEnum;
import verification.verifycode.support.VerifyCodeData;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-02 22:07
 */
@RestController
public class TestController {

    /**
     * 验证码：https://www.jianshu.com/p/22004d424183
     */
    @CrossOrigin(origins = "*")
    @GetMapping(path = "/getPictureCode",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDataDto getPictureCode() {
        VerifyCodeData verifyCodeData = VerifyCodeFactory.getDefaultVerifyCode().generate(110, 40, PicTypeEnum.JPEG);
        return ResultDataDto.of(null, verifyCodeData.getPicContent());
    }

    @Data
    public static class ResultDataDto {
        private String id;
        private String picCode;

        public static ResultDataDto of(String id, String picCode) {
            ResultDataDto instance = new ResultDataDto();
            instance.setId(id);
            instance.setPicCode(picCode);
            return instance;
        }
    }
}
