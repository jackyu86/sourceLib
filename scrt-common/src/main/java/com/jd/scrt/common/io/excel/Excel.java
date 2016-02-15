package com.jd.scrt.common.io.excel;

import org.apache.poi.ss.usermodel.Workbook;


/**
 * Excel 操作接口
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public interface Excel {

    /**
     * 初始化Workbook
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param workbook
     * @throws Exception
     */
    public void initWorkbook(Workbook workbook);

    /**
     * 获得Sheet名称
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetIndex
     * @return
     * @throws Exception
     */
    public String getSheetName(int sheetIndex) throws Exception;

    /**
     * 获得Sheet索引
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetName
     * @return
     * @throws Exception
     */
    public int getSheetIndex(String sheetName) throws Exception;

    /**
     * 重命名Sheet
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetIndex
     * @param newName
     */
    public void renameSheet(int sheetIndex, String newName) throws Exception;

    /**
     * 重命名Sheet
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetName
     * @param newName
     */
    public void renameSheet(String sheetName, String newName) throws Exception;

    /**
     * 删除Sheet
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetIndex
     * @throws Exception
     */
    public void removeSheet(int sheetIndex) throws Exception;

    /**
     * 删除Sheet
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetName
     * @throws Exception
     */
    public void removeSheet(String sheetName) throws Exception;

}
