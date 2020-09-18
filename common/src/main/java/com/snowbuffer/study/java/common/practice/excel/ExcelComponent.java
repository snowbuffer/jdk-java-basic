package com.snowbuffer.study.java.common.practice.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description: excel组件
 *
 * @author cjb
 * @since 2020-07-14 22:38
 */
public class ExcelComponent {

    private ThreadLocal<ExcelContext> local = new ThreadLocal<>();

    public void initExcelContext() {
        ExcelContext excelContext = new ExcelContext();
        local.set(excelContext);
    }

    public void touchWorkbook() {
        Workbook wb = new XSSFWorkbook();
        getConext().setWorkbook(wb);
    }

    private Row createRow(int rownum) {
        return getConext().getSheet().createRow(rownum);
    }

    public void setSheetTotalCellNum(int sheetTotalCellNum) {
        getConext().setSheetTotalCellNum(sheetTotalCellNum);
    }

    public void createRow(String[] cellValues) {
        getConext().increateCurrentRowNum();
        Row row = createRow(getConext().getCurrentRowNum());
        createRow(row, CellType.STRING, cellValues);
    }

    private void createRow(Row row, CellType cellType, Object[] cellValues) {
        int totalCellNum = getConext().getSheetTotalCellNum();
        for (int i = 0; i < totalCellNum; i++) {
            Cell cell = row.createCell(i, cellType);
            if (CellType.STRING == cellType) {
                Object cellValue = cellValues[i];
                if (cellValue == null) {
                    cellValue = "";
                } else {
                    cellValue = cellValue.toString();
                    if (cellValue.equals("-")) {
                        cellValue = "";
                    }
                }
                cell.setCellValue(cellValue.toString());
            }
        }
    }

    public void touchSheet(String sheetName) {
        Sheet sheet = getConext().getWorkbook().createSheet(sheetName);
        getConext().setSheet(sheet);
    }

    public void openLocation(String absoluteFileName) {
        try {
            File file = new File(absoluteFileName);
            if (file.exists()) {
                System.out.println("文件存在：" + absoluteFileName + ", 删除结果：" + file.delete());
            }
            FileOutputStream out = new FileOutputStream(absoluteFileName);
            getConext().setFileOutputStream(out);
            getConext().setGeneratePath(absoluteFileName);
        } catch (FileNotFoundException e) {
            String erroMsg = String.format("打开文件: %s 发生错误", absoluteFileName);
            throw new OpenFileException(erroMsg, e);
        }
    }

    public void generateExcel() {
        try {
            getConext().getWorkbook().write(getConext().getFileOutputStream());
            System.out.println("excel生成完成：" + getConext().getGeneratePath());
        } catch (IOException e) {
            String erroMsg = String.format("生成excel: %s 发生错误", getConext().getGeneratePath());
            throw new GenerateException(erroMsg, e);
        }
    }

    public void close() {
        try {
            getConext().getFileOutputStream().close();
        } catch (IOException e) {
            String erroMsg = String.format("关闭资源: %s 发生错误", getConext().getGeneratePath());
            throw new CloseException(erroMsg, e);
        } finally {
            local.remove();
        }

    }

    public void setSheetCellColumnWith(int cellColumnWith) {
        int width = cellColumnWith * 256;
        for (int i = 0; i < getConext().getSheetTotalCellNum(); i++) {
            getConext().getSheet().setColumnWidth(i, width);
        }
    }

    private ExcelContext getConext() {
        return local.get();
    }
}
