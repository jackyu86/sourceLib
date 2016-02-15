package com.jd.scrt.common.io.excel;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel 抽象实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public abstract class AbstractExcel implements Excel {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private Workbook workbook = null;

    public AbstractExcel() {
    }

    public AbstractExcel(Workbook workbook) {
        this.initWorkbook(workbook);
    }

    @Override
    public void initWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public String getSheetName(int sheetIndex) throws Exception {
        return this.workbook.getSheetName(sheetIndex);
    }

    @Override
    public int getSheetIndex(String sheetName) throws Exception {
        return this.workbook.getSheetIndex(sheetName);
    }

    @Override
    public void renameSheet(int sheetIndex, String newName) throws Exception {
        this.workbook.setSheetName(sheetIndex, newName);
    }

    @Override
    public void renameSheet(String sheetName, String newName) throws Exception {
        int sheetIndex = this.workbook.getSheetIndex(sheetName);
        this.workbook.setSheetName(sheetIndex, newName);
    }

    @Override
    public void removeSheet(int sheetIndex) throws Exception {
        this.workbook.removeSheetAt(sheetIndex);
    }

    @Override
    public void removeSheet(String sheetName) throws Exception {
        int sheetIndex = this.workbook.getSheetIndex(sheetName);
        this.workbook.removeSheetAt(sheetIndex);
    }

    protected Sheet getSheet(String sheetName) throws Exception {
        return this.workbook.getSheet(sheetName);
    }

    protected Sheet getSheetAt(int sheetIndex) throws Exception {
        return this.workbook.getSheetAt(sheetIndex);
    }

    protected Row getRowAt(int sheetIndex, int rownum) throws Exception {
        Sheet sheet = this.getSheetAt(sheetIndex);
        if (sheet == null) {
            return null;
        }
        return sheet.getRow(rownum);
    }

    protected Cell getCellAt(int sheetIndex, int rownum, int column) throws Exception {
        Row row = this.getRowAt(sheetIndex, rownum);
        if (row == null) {
            return null;
        }
        return row.getCell(column);
    }

    protected Sheet createSheet(String sheetName) throws Exception {
        return this.workbook.createSheet(sheetName);
    }

    protected Sheet createOrGetSheet(String sheetName) throws Exception {
        Sheet sheet = this.workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = this.workbook.createSheet(sheetName);
        }
        return sheet;
    }

    protected Row createOrGetRow(String sheetName, int rownum) throws Exception {
        Sheet sheet = this.createOrGetSheet(sheetName);
        Row row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }
        return row;
    }

    protected Cell createOrGetCell(String sheetName, int rownum, int column) throws Exception {
        Row row = this.createOrGetRow(sheetName, rownum);
        Cell cell = row.getCell(column);
        if (cell == null) {
            cell = row.createCell(column);
        }
        return cell;
    }

    protected void write(OutputStream outputStream) throws IOException {
        this.workbook.write(outputStream);
    }

    protected String convertToString(Object value) throws Exception {
        String str = String.valueOf(value).trim();
        return ("null".equals(str)) ? "" : str;
    }

    // ---------- getter and setter ----------//
    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

}
