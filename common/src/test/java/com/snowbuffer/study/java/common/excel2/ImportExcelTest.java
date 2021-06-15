package com.snowbuffer.study.java.common.excel2;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.snowbuffer.study.java.common.excel2.annotation.ExcelColumn;
import com.snowbuffer.study.java.common.excel2.listener.LifeListener;
import com.snowbuffer.study.java.common.excel2.listener.ObservableRunnable;
import com.snowbuffer.study.java.common.excel2.parser.HutoolExcelParser;
import com.snowbuffer.study.java.common.excel2.upload.UploadRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-11 01:35
 */
@Slf4j
public class ImportExcelTest {

    private static String TEMPLATE_EXCEL = "excel2/order_wait_send.xlsx";

    // 运行此段程序，需要使用main方法
    @Test
//    @Ignore
    public void test() throws InterruptedException {

        File tempFile = copyTemplate();

        ExcelExecutors.execute(
                new AbstractImportExcel<OrderExcelParam>(
                        new HutoolExcelParser<>(OrderExcelParam.class),
                        new LogLifeListener<>(),
                        new DefaultUploadRepository()
                ) {

                    @Override
                    protected boolean checkDataWithoutThrowException(List<OrderExcelParam> dataList) {
                        for (OrderExcelParam data : dataList) {
                            data.setErrorMsg("这条记录不正确");
                        }
                        return false;
                    }

                    @Override
                    protected List<OrderExcelParam> importDataWithoutThrowException(List<OrderExcelParam> dataList) {
                        return null;
                    }

                }, tempFile.getAbsolutePath());

        ExcelExecutors.close();

        Thread.currentThread().join();

    }

    @Test
    public void excelColumnTest() {
        Map<String, ExcelColumn> columnMap = ExcelConfigFactory.getExcelColumnConfig(OrderExcelParam.class);
        for (Map.Entry<String, ExcelColumn> entry : columnMap.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
    }


    private static File copyTemplate() {
        String path = ImportExcelTest.class.getClassLoader().getResource(TEMPLATE_EXCEL).getPath();
        return FileUtil.copy(path, System.getProperty("user.dir") + File.separator + "tmp" + File.separator + UUID.randomUUID() + ".xlsx", true);
    }


    static class DefaultUploadRepository implements UploadRepository {

        @Override
        public String upload(File file) {
            log.debug("待上传文件路径 => {}", file.getPath());
            if (file != null) {
                try {
                    FileUtil.del(file);
                } catch (IORuntimeException e) {
                    log.error("清理存储库临时文件失败,路径 => {}", file.getAbsolutePath(), e);
                }
            }
            return "mockUrl";
        }
    }

    static class LogLifeListener<T> implements LifeListener<T> {

        @Override
        public void onEvent(ObservableRunnable.RunnableEvent<T> event) {
            if (event.getUniqueId() == null) {
                event.setUniqueId(new Random().nextLong());
                log.error("uniqueId:{},添加记录,初始状态 => [{}]", event.getUniqueId(), event.getRunnableState().getMsg());
            } else {
                if (event.getCause() != null) {
                    log.error("uniqueId:{},更新记录,最终状态 => [{}], 产生原因: \n", event.getUniqueId(), event.getRunnableState().getMsg(), event.getCause());
                } else {
                    if (event.getRunnableState() == ObservableRunnable.RunnableState.UPLOADING_SUCCESS) {
                        log.error("uniqueId:{},更新记录,最终状态 => [{}], url => {}", event.getUniqueId(), event.getRunnableState().getMsg(), event.getUploadUrl());
                    } else {
                        log.error("uniqueId:{},更新记录,最终状态 => [{}]", event.getUniqueId(), event.getRunnableState().getMsg());
                    }
                }
            }
        }
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    static class OrderExcelParam implements Serializable {

        private static final long serialVersionUID = -8763744659849653266L;

        /**
         * 商品id
         */
        @ExcelColumn(index = 0, columnName = "商品id", width = 20)
        private Long itemId;

        /**
         * 商品名称
         */
        @ExcelColumn(index = 1, columnName = "商品名称", width = 20)
        private String itemName;

        /**
         * sku编码
         */
        @ExcelColumn(index = 2, columnName = "sku编码", width = 20)
        private String skuCode;

        /**
         * 商品sku
         */
        @ExcelColumn(index = 3, columnName = "商品sku", width = 20)
        private Long skuSmallCategoryCombId;

        /**
         * 发货件数
         */
        @ExcelColumn(index = 4, columnName = "发货件数", width = 20)
        private Integer waitSendCount;

        /**
         * 收件人姓名
         */
        @ExcelColumn(index = 5, columnName = "收件人姓名", width = 20)
        private String receiverName;

        /**
         * 收件人手机号
         */
        @ExcelColumn(index = 6, columnName = "收件人手机号", width = 20)
        private String receiverTelephone;

        /**
         * 收件人地址
         */
        @ExcelColumn(index = 7, columnName = "收件人地址", width = 20)
        private String receiverAddress;

        /**
         * 错误信息
         */
        @ExcelColumn(index = 8, columnName = "错误信息")
        private String errorMsg;
    }
}
