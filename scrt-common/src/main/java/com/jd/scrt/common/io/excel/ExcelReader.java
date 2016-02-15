package com.jd.scrt.common.io.excel;

import java.util.List;

/**
 * Excel 读取接口
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface ExcelReader extends Excel {

    /**
     * 读取excel中sheet
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetIndex
     * @return
     * @throws Exception
     */
    public List<List<Object>> readSheet(int sheetIndex) throws Exception;

    /**
     * 读取excel中row
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetIndex
     * @param rownum
     * @return
     * @throws Exception
     */
    public List<Object> readRow(int sheetIndex, int rownum) throws Exception;

    /**
     * 读取excel中cell
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetIndex
     * @param rownum
     * @param column
     * @return
     * @throws Exception
     */
    public Object readCell(int sheetIndex, int rownum, int column) throws Exception;

}
