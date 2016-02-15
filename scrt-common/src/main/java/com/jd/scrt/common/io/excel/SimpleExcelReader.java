package com.jd.scrt.common.io.excel;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel 简单的读取实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class SimpleExcelReader extends AbstractExcelReader {

    public SimpleExcelReader() {
        super();
    }

    public SimpleExcelReader(Workbook workbook) {
        super(workbook);
    }

}
