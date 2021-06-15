package com.snowbuffer.study.java.common.excel2.parser;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.style.StyleUtil;
import com.snowbuffer.study.java.common.excel2.ExcelConfigFactory;
import com.snowbuffer.study.java.common.excel2.annotation.ExcelColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Description:
 *
 * @author cjb
 * @since 2021-06-10 15:28
 */
@Slf4j
public class HutoolExcelParser<T> implements ExcelParser<T> {

    private Class<T> clazz;

    private Map<String, ExcelColumn> headMap;

    public HutoolExcelParser(Class<T> clazz) {
        this.clazz = clazz;
        this.headMap = ExcelConfigFactory.getExcelColumnConfig(clazz);
    }

    @Override
    public List<T> parse(String filePath) {
        ExcelReader excelReader = ExcelUtil.getReader(filePath, 0);
        for (Map.Entry<String, ExcelColumn> entry : headMap.entrySet()) {
            String fieldName = entry.getKey();
            ExcelColumn columnConfig = entry.getValue();
            excelReader.addHeaderAlias(columnConfig.columnName(), fieldName);
        }
        return excelReader.read(0, 1, Integer.MAX_VALUE, this.clazz);
    }

    @Override
    public File write(List<T> dataList) {
        String tempAbsolutePath = touchNewFile(System.getProperty("user.dir") + File.separator + "tmp");
        return doWrite(dataList, tempAbsolutePath);
    }

    private File doWrite(List<T> dataList, String tempAbsolutePath) {
        ExcelWriter writer = ExcelUtil.getWriter(tempAbsolutePath);

//        CellStyle style = writer.getStyleSet().getHeadCellStyle();
        CellStyle cellStyle = writer.getStyleSet().getCellStyle();
        StyleUtil.setBorder(cellStyle, BorderStyle.NONE, IndexedColors.BLACK);

        int counter = 0;
        for (Map.Entry<String, ExcelColumn> entry : headMap.entrySet()) {
            String fieldName = entry.getKey();
            ExcelColumn columnConfig = entry.getValue();
            writer.addHeaderAlias(fieldName, columnConfig.columnName());
            int width = columnConfig.width();
            if (width != -1) {
                writer.setColumnWidth(counter++, width);
            }
        }

        writer.write(dataList, true);

        writer.setRowHeight(0, 30);

        writer.close();
        return new File(tempAbsolutePath);
    }

    private String touchNewFile(String dir) {
        File srcFile = new File(dir);
        if (!srcFile.exists()) {
            srcFile.mkdirs();
        }
        return dir + File.separator + UUID.randomUUID() + ".xlsx";
    }

}
