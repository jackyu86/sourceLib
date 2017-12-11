package com.jd.scrt.common.io.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel 抽象读取实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public abstract class AbstractExcelReader extends AbstractExcel implements ExcelReader {

    public AbstractExcelReader() {
    }

    public AbstractExcelReader(Workbook workbook) {
        super(workbook);
    }

    @Override
    public List<List<Object>> readSheet(int sheetIndex) throws Exception {
        Sheet sheet = this.getSheetAt(sheetIndex);
        return this.readSheet(sheet);
    }

    @Override
    public List<Object> readRow(int sheetIndex, int rownum) throws Exception {
        Row row = this.getRowAt(sheetIndex, rownum);
        return this.readRow(row);
    }

    @Override
    public Object readCell(int sheetIndex, int rownum, int column) throws Exception {
        Cell cell = this.getCellAt(sheetIndex, rownum, column);
        return this.readCell(cell);
    }

    /**
     * 读取Sheet数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheet
     * @return
     * @throws Exception
     */
    protected List<List<Object>> readSheet(Sheet sheet) throws Exception {
        if (sheet == null) {
            return null;
        }
        List<List<Object>> sheet_data = new ArrayList<List<Object>>();
        List<Object> row_data = null;
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            row_data = this.readRow(row);
            sheet_data.add(row_data);
        }
        return sheet_data;
    }

    /**
     * 读取工作簿一行数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param row
     * @return
     * @throws Exception
     */
    protected List<Object> readRow(Row row) throws Exception {
        if (row == null) {
            return null;
        }
        List<Object> list = new ArrayList<Object>();
        int lastCellNum = row.getLastCellNum();
        for (int i = 0; i <= lastCellNum; i++) {
            Cell cell = row.getCell(i);
            list.add(this.readCell(cell));
        }
        return list;
    }

    /**
     * 读取工作簿单元格数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param cell
     * @return
     * @throws Exception
     */
    protected Object readCell(Cell cell) throws Exception {
        if (cell == null) {
            return null;
        }
        Object cellValue = null;

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = this.convertToString(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = this.convertToString(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = this.convertToString(cell.getArrayFormulaRange().formatAsString());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = this.convertToString(cell.getBooleanCellValue());
                break;

            default:
                cellValue = "";
                break;
        }
        return cellValue;
    }

}
