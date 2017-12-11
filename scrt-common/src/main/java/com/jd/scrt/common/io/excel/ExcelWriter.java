package com.jd.scrt.common.io.excel;

import java.io.Flushable;
import java.io.OutputStream;
import java.util.List;

/**
 * Excel 写入接口
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface ExcelWriter extends Excel, Flushable {

    /**
     * 写入sheet数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetName
     * @param contents
     * @throws Exception
     */
    public void writeSheet(String sheetName, List<List<Object>> contents) throws Exception;

    /**
     * 写入row数据
     *
     * @param sheetName
     * @param contents
     * @param rownum
     * @throws Exception
     */
    public void writeRow(String sheetName, int rownum, List<Object> contents) throws Exception;

    /**
     * 写入cell数据
     *
     * @param sheetName
     * @param contents
     * @param rownum
     * @param column
     * @throws Exception
     */
    public void writeCell(String sheetName, int rownum, int column, Object val) throws Exception;

    /**
     * 设置输出流
     *
     * @param outputStream
     */
    public void setOutputStream(OutputStream outputStream);


}
