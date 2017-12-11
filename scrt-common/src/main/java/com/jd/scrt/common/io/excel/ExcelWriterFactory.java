package com.jd.scrt.common.io.excel;

import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 写入工厂
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class ExcelWriterFactory {

    public static final String EXCEL_2003 = ".xls";
    public static final String EXCEL_2007 = ".xlsx";

    /**
     * 创建一个ExcelWriter对象
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param outputStream
     * @param version
     * @return
     * @throws Exception
     */
    public static ExcelWriter newExcelWriter(OutputStream outputStream, String version) throws Exception {
        return newExcelWriter(outputStream, version, SimpleExcelWriter.class);
    }

    public static ExcelWriter newExcelWriter(OutputStream outputStream, String version, Class<? extends ExcelWriter> excelWriterClass) throws Exception {
        if (!isSupportedVersion(version)) {
            throw new UnsupportedOperationException("无法创建ExcelWriter，不支持的Excel版本[" + version + "]!");
        }
        if (excelWriterClass == null) {
            throw new IllegalArgumentException("the argument[excelWriterClass] is required; it must not be null!");
        }
        Workbook workbook = null;
        version = version.trim().toLowerCase();
        if (version.endsWith(EXCEL_2003)) {
            workbook = new HSSFWorkbook();
        } else if (version.endsWith(EXCEL_2007)) {
            workbook = new XSSFWorkbook();
        } else {
            throw new UnsupportedOperationException("无法创建ExcelWriter，不支持的Excel版本[" + version + "]");
        }
        ExcelWriter excelWriter = excelWriterClass.newInstance();
        excelWriter.initWorkbook(workbook);
        excelWriter.setOutputStream(outputStream);
        return excelWriter;
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
