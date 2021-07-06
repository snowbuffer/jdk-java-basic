package verification.verifycode.impl;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.Configurable;
import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;
import com.jhlabs.image.WaterFilter;
import org.springframework.util.Assert;
import verification.verifycode.VerifyCode;
import verification.verifycode.support.PicTypeEnum;
import verification.verifycode.support.TagEnum;
import verification.verifycode.support.VerifyCodeData;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-09 10:37
 */
public class KaptchaVerifyCode implements VerifyCode {

    private static DefaultKaptcha defaultKaptcha;

    static {
        initKaptcha();
    }

    @Override
    public TagEnum tag() {
        return TagEnum.KAPTCHA;
    }

    @Override
    public VerifyCodeData generate(Integer width, Integer height, PicTypeEnum picTypeEnum) {
        Assert.notNull(defaultKaptcha, "Kaptcha未设置");

        Properties properties = defaultKaptcha.getConfig().getProperties();

        properties.put("kaptcha.image.width", width + "");
        properties.put("kaptcha.image.height", height + "");

        return doGenerate(picTypeEnum);
    }

    private VerifyCodeData doGenerate(PicTypeEnum picTypeEnum) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            VerifyCodeData.VerifyCodeDataBuilder<?, ?> builder = VerifyCodeData.builder();

            String code = defaultKaptcha.createText();
            builder.code(code);

            BufferedImage image = defaultKaptcha.createImage(code);
            ImageIO.write(image, picTypeEnum.getCode(), outputStream);
            byte[] captchaChallengeAsJpeg = outputStream.toByteArray();
            builder.picContent(picTypeEnum.getDataPrefix() + Base64.getEncoder().encodeToString(captchaChallengeAsJpeg));

            return builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initKaptcha() {
        defaultKaptcha = new DefaultKaptcha();
        /**
         * 属性配置：https://www.cnblogs.com/louis80/p/5230507.html
         */
        Properties properties = new Properties();
//        properties.setProperty("kaptcha.border", "no");
//        properties.setProperty("kaptcha.border.color", "white");
//        properties.setProperty("kaptcha.textproducer.font.color", "255,192,55");
//        properties.setProperty("kaptcha.image.width", "125");
//        properties.setProperty("kaptcha.image.height", "45");
//        properties.setProperty("kaptcha.session.key", "code");
//        properties.setProperty("kaptcha.textproducer.font.size", "38");
//        properties.setProperty("kaptcha.noise.color","21,113,171");
//        properties.setProperty("kaptcha.background.clear.from","0,154,255");
//        properties.setProperty("kaptcha.background.clear.to","0,202,255");
//        properties.setProperty("kaptcha.textproducer.char.length", "4");
//        properties.setProperty("kaptcha.textproducer.font.names", "Arial");
        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "white");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "110");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "0,122,255"); //
        properties.setProperty("kaptcha.textproducer.char.string", "123456789");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 干扰实现类
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
//        properties.setProperty("kaptcha.noise.color", "21,113,171");
        // 背景颜色渐变，开始颜色
        properties.setProperty("kaptcha.background.clear.from", "white");
        // 背景颜色渐变，结束颜色
        properties.setProperty("kaptcha.background.clear.to", "white");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "Microsoft YaHei");
        /**
         * 图片样式：
         * 水纹com.google.code.kaptcha.impl.WaterRipple
         * 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
         * 阴影com.google.code.kaptcha.impl.ShadowGimpy
         */
        properties.setProperty("kaptcha.obscurificator.impl", "verification.verifycode.support.NoWaterRipple");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
    }

}
