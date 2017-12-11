package com.jd.scrt.common.io.excel;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel 简单的写入实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class SimpleExcelWriter extends AbstractExcelWriter {

    public SimpleExcelWriter() {
        super();
    }

    public SimpleExcelWriter(Workbook workbook) {
        super(workbook);
    }

}
