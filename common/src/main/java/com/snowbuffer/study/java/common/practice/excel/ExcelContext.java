package com.snowbuffer.study.java.common.practice.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

/**
 * Description:
 *
 * @author cjb
 * @since 2020-07-14 22:52
 */
public class ExcelContext {

    private Workbook workbook;

    private Sheet sheet;

    private int sheetTotalCellNum;

    private FileOutputStream fileOutputStream;

    private String generatePath;

    private Integer currentRowNum = -1;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getSheetTotalCellNum() {
        return sheetTotalCellNum;
    }

    public void setSheetTotalCellNum(int sheetTotalCellNum) {
        this.sheetTotalCellNum = sheetTotalCellNum;
    }

    public FileOutputStream getFileOutputStream() {
        return fileOutputStream;
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    public String getGeneratePath() {
        return generatePath;
    }

    public void setGeneratePath(String generatePath) {
        this.generatePath = generatePath;
    }

    public Integer getCurrentRowNum() {
        return currentRowNum;
    }

    public void increateCurrentRowNum() {
        this.currentRowNum++;
    }
}
