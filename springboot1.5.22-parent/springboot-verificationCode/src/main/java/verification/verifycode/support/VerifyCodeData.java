package verification.verifycode.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-09 14:12
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class VerifyCodeData {

    private String picContent;

    private String code;
}
