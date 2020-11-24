package com.snowbuffer.study.java.common.excel.support;

import cn.hutool.core.date.DateUtil;
import com.snowbuffer.study.java.common.excel.enums.FlowStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-11-23 20:28
 */
@Slf4j
public abstract class AbstractHandlerSupport {

    /**
     * 切换状态
     */
    protected void switchStatus(
            Long taskId, FlowStatus fromStatus, FlowStatus toStatus,
            String ossUrl, String remark) {

        this.log.info("切换状态：taskId:{}, from:{}, to:{}, ossUrl:{}, remark:{}",
                taskId, fromStatus, toStatus, ossUrl, remark);
    }

    /**
     * 上传二进制流到oss
     */
    protected String upload(
            String logMessage, String fileName, byte[] dataBytesArray) throws Exception {

        if (dataBytesArray == null || dataBytesArray.length <= 0) {
            log.warn(logMessage, "文件大小为0，无需上传OSS");
            return null;
        }
        String ossUrl;
//        if (ossHandle != null) {
//            ossUrl = ossHandle.uploadToPrivate(dataBytesArray, fileName);
//        } else {
        String pattern = DateUtil.format(new Date(), "yyyy_MM_dd_HH_mm_ss");
        String destFile = pattern + fileName;
        File file = new File(destFile);
        ossUrl = file.getAbsolutePath();
        FileCopyUtils.copy(dataBytesArray, file);
//        }
        log.info(logMessage, String.format("文件上传完成,地址：%s", ossUrl));
        return ossUrl;
    }

    /**
     * 关闭流程
     */
    protected void closeAllStream(String errorMessage, Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    log.error(String.format(errorMessage, " IO流关闭失败 => error: "), e);
                }
            }
        }
    }
}
