package com.jd.scrt.common.io.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel 抽象写入实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public abstract class AbstractExcelWriter extends AbstractExcel implements ExcelWriter {

    private OutputStream outputStream;

    public AbstractExcelWriter() {
    }

    public AbstractExcelWriter(Workbook workbook) {
        super(workbook);
    }

    @Override
    public void writeSheet(String sheetName, List<List<Object>> contents) throws Exception {
        Sheet sheet = this.createOrGetSheet(sheetName);
        this.writeSheet(sheet, contents);
    }

    @Override
    public void writeRow(String sheetName, int rownum, List<Object> contents) throws Exception {
        Row row = this.createOrGetRow(sheetName, rownum);
        this.writeRow(row, contents);
    }

    @Override
    public void writeCell(String sheetName, int rownum, int column, Object val) throws Exception {
        Cell cell = this.createOrGetCell(sheetName, rownum, column);
        this.writeCell(cell, val);
    }

    /**
     * 写入工作簿一sheet
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheet
     * @param content
     * @throws Exception
     */
    protected void writeSheet(Sheet sheet, List<List<Object>> contents) throws Exception {
        int contentsSize = contents.size();
        for (int i = 0; i < contentsSize; i++) {
            List<Object> content = contents.get(i);
            Row c_row = sheet.createRow(i);
            this.writeRow(c_row, content);
        }
    }

    /**
     * 写入工作簿一行数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param row
     * @param content
     * @throws Exception
     */
    protected void writeRow(Row row, List<Object> content) throws Exception {
        int contentSize = content.size();
        for (int i = 0; i < contentSize; i++) {
            Cell cell = row.createCell(i);
            Object val = content.get(i) == null ? "" : content.get(i);
            this.writeCell(cell, val);
        }
    }

    /**
     * 写入工作簿单元格数据
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cell
     * @param val
     * @throws Exception
     */
    protected void writeCell(Cell cell, Object value) throws Exception {
        cell.setCellValue(this.convertToString(value));
    }

    @Override
    public void flush() throws IOException {
        this.write(this.getOutputStream());
    }

    // ---------- getter and setter ----------//
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

}
