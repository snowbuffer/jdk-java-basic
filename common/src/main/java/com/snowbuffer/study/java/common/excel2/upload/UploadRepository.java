package com.snowbuffer.study.java.common.excel2.upload;

import java.io.File;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 18:26
 */
public interface UploadRepository {

    String upload(File file);
}
