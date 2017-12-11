package com.jd.scrt.common.io.excel;

import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 读取工厂
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class ExcelReaderFactory {

    public static final String EXCEL_2003 = ".xls";
    public static final String EXCEL_2007 = ".xlsx";

    /**
     * 创建一个ExcelReader对象
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param inputStream
     * @param version
     * @return
     * @throws Exception
     */
    public static ExcelReader newExcelReader(InputStream inputStream, String version) throws Exception {
        return newExcelReader(inputStream, version, SimpleExcelReader.class);
    }

    public static ExcelReader newExcelReader(InputStream inputStream, String version, Class<? extends ExcelReader> excelReaderClass) throws Exception {
        if (inputStream == null) {
            throw new IllegalArgumentException("the argument[inputStream] is required; it must not be null!");
        }
        if (!isSupportedVersion(version)) {
            throw new UnsupportedOperationException("无法创建ExcelReader，不支持的Excel版本[" + version + "]!");
        }
        if (excelReaderClass == null) {
            throw new IllegalArgumentException("the argument[excelReaderClass] is required; it must not be null!");
        }
        Workbook workbook = null;
        version = version.trim().toLowerCase();
        if (version.endsWith(EXCEL_2003)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (version.endsWith(EXCEL_2007)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new UnsupportedOperationException("无法创建ExcelReader，不支持的Excel版本[" + version + "]");
        }
        ExcelReader excelReader = excelReaderClass.newInstance();
        excelReader.initWorkbook(workbook);
        return excelReader;
    }

    /**
     * 是否支持的版本
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param version
     * @return
     * @throws Exception
     */
    public static boolean isSupportedVersion(String version) throws Exception {
        if (version == null || version.length() < 1) {
            return false;
        }
        version = version.trim().toLowerCase();
        if (version.endsWith(EXCEL_2003) || version.endsWith(EXCEL_2007)) {
            return true;
        }
        return false;
    }

}
