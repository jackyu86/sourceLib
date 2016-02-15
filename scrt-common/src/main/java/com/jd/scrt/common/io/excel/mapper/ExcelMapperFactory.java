package com.jd.scrt.common.io.excel.mapper;

import com.jd.scrt.common.io.excel.ExcelReader;
import com.jd.scrt.common.io.excel.ExcelReaderFactory;
import com.jd.scrt.common.io.excel.ExcelWriter;
import com.jd.scrt.common.io.excel.ExcelWriterFactory;

import java.io.InputStream;
import java.io.OutputStream;


/**
 * ExcelMapper创建工厂
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class ExcelMapperFactory {

    public static final String EXCEL_2003 = ".xls";
    public static final String EXCEL_2007 = ".xlsx";

    public static ExcelMapper newExcelMapper(InputStream inputStream, String version) throws Exception {
        return newExcelMapper(inputStream, null, version);
    }

    public static ExcelMapper newExcelMapper(OutputStream outputStream, String version) throws Exception {
        return newExcelMapper(null, outputStream, version);
    }

    private static ExcelMapper newExcelMapper(InputStream inputStream, OutputStream outputStream, String version) throws Exception {
        ExcelMapper excelMapper = new ExcelMapper();
        if (inputStream != null) {
            ExcelReader excelReader = ExcelReaderFactory.newExcelReader(inputStream, version);
            excelMapper.setExcelReader(excelReader);
        }
        if (outputStream != null) {
            ExcelWriter excelWriter = ExcelWriterFactory.newExcelWriter(outputStream, version);
            excelMapper.setExcelWriter(excelWriter);
        }
        return excelMapper;
    }

}
