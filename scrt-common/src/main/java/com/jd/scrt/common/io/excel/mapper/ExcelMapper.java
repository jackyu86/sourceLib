package com.jd.scrt.common.io.excel.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jd.scrt.common.io.excel.ExcelReader;
import com.jd.scrt.common.io.excel.ExcelWriter;
import com.jd.scrt.common.io.excel.converter.ExcelDateConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;


/**
 * Excel文件与Bean映射类
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class ExcelMapper {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
    private ExcelDateConverter dateConverter = new ExcelDateConverter();

    private ExcelReader excelReader;
    private ExcelWriter excelWriter;

    public ExcelMapper() {
        this.initBeanUtilsBean();
    }

    public void init() {
        this.initBeanUtilsBean();
    }

    protected void initBeanUtilsBean() {
        this.getBeanUtilsBean().getConvertUtils().register(this.getDateConverter(), Date.class);// 注册一个日期转换类
        this.getBeanUtilsBean().getConvertUtils().register(this.getDateConverter(), String.class);// 注册一个日期转换类
    }

    public void initDateFormatPattern(String dateFormatPattern) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        this.getDateConverter().setDateFormat(dateFormat);
        this.getBeanUtilsBean().getConvertUtils().register(this.getDateConverter(), Date.class);// 注册一个日期转换类
        this.getBeanUtilsBean().getConvertUtils().register(this.getDateConverter(), String.class);// 注册一个日期转换类
    }

    // ------------------------ read part ------------------------//

    /**
     * 将Excel读入bean对象
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetIndex
     * @param beanClass
     * @param propertyNames
     * @return
     * @throws Exception
     */
    public <T> List<T> readSheet(int sheetIndex, Class<T> beanClass, List<String> propertyNames) throws Exception {
        List<List<Object>> data = this.getExcelReader().readSheet(sheetIndex);
        return this.convertToBeans(data, beanClass, propertyNames);
    }

    /**
     * 将Excel一行数据读入bean对象
     *
     * @param sheetIndex
     * @param rownum
     * @param beanClass
     * @param propertyNames
     * @return
     * @throws Exception
     */
    public <T> T readRow(int sheetIndex, int rownum, Class<T> beanClass, List<String> propertyNames) throws Exception {
        List<Object> data = this.getExcelReader().readRow(sheetIndex, rownum);
        return this.convertToBean(data, beanClass, propertyNames);
    }

    public <T> List<T> convertToBeans(List<List<Object>> sheetData, Class<T> beanClass, List<String> propertyNames) throws Exception {
        if (sheetData == null || beanClass == null || propertyNames == null) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (List<Object> rowData : sheetData) {
            T t = this.convertToBean(rowData, beanClass, propertyNames);
            result.add(t);
        }
        return result;
    }

    public <T> T convertToBean(List<Object> rowData, Class<T> beanClass, List<String> propertyNames) throws Exception {
        if (rowData == null || beanClass == null || propertyNames == null) {
            return null;
        }
        Map<String, Object> data_map = this.convertToMap(rowData, propertyNames);
        return this.convertToBean(beanClass.newInstance(), data_map);
    }

    protected Map<String, Object> convertToMap(List<Object> rowData, List<String> propertyNames) throws Exception {
        if (rowData == null || propertyNames == null) {
            return null;
        }
        int data_leg = rowData.size();
        int prop_leg = propertyNames.size();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < data_leg && i < prop_leg; i++) {
            map.put(propertyNames.get(i), rowData.get(i));
        }
        return map;
    }

    protected <T> T convertToBean(T t, Map<String, Object> dataMap) throws Exception {
        if (t == null || dataMap == null) {
            return null;
        }
        // 因为要注册converter,所以不能再使用BeanUtils的静态方法了，必须创建BeanUtilsBean实例
        // BeanUtils.populate(t, dataMap);
        this.getBeanUtilsBean().populate(t, dataMap);
        return t;
    }

    // ------------------------ write part ------------------------//

    /**
     * 将bean对象集合写入Excel
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sheetName
     * @param beans
     * @param propertyNames
     * @throws Exception
     */
    public void writeSheet(String sheetName, List<?> beans, List<String> propertyNames) throws Exception {
        List<List<Object>> data = this.convertToDatas(beans, propertyNames);
        this.getExcelWriter().writeSheet(sheetName, data);
        this.getExcelWriter().flush();
    }

    /**
     * 将bean对象写入Excel一行
     *
     * @param sheetName
     * @param rownum
     * @param bean
     * @param propertyNames
     * @throws Exception
     */
    public void writeRow(String sheetName, int rownum, Object bean, List<String> propertyNames) throws Exception {
        List<Object> data = this.convertToData(bean, propertyNames);
        this.getExcelWriter().writeRow(sheetName, rownum, data);
        this.getExcelWriter().flush();
    }

    public List<List<Object>> convertToDatas(List<?> beans, List<String> propertyNames) throws Exception {
        if (beans == null || propertyNames == null) {
            return null;
        }
        List<List<Object>> result = new ArrayList<List<Object>>();
        for (Object bean : beans) {
            List<Object> row = this.convertToData(bean, propertyNames);
            result.add(row);
        }
        return result;
    }

    public List<Object> convertToData(Object bean, List<String> propertyNames) throws Exception {
        if (bean == null || propertyNames == null) {
            return null;
        }
        Map<String, Object> map = this.convertToMap(bean);
        if (map == null || map.isEmpty()) {
            logger.warn("convertToData: obj convertToMap return empty,return null!");
            return null;
        }
        List<Object> result = new ArrayList<Object>();
        for (String prop : propertyNames) {
            if (prop == null || prop.isEmpty()) {
                result.add("");
            } else {
                result.add(map.get(prop));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> convertToMap(Object bean) throws Exception {
        if (bean == null) {
            return null;
        }
        return this.getBeanUtilsBean().describe(bean);
    }

    // ---------- setter and getter ----------//
    public BeanUtilsBean getBeanUtilsBean() {
        return beanUtilsBean;
    }

    public void setBeanUtilsBean(BeanUtilsBean beanUtilsBean) {
        this.beanUtilsBean = beanUtilsBean;
    }

    public ExcelDateConverter getDateConverter() {
        return dateConverter;
    }

    public void setDateConverter(ExcelDateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    public ExcelReader getExcelReader() {
        return excelReader;
    }

    public void setExcelReader(ExcelReader excelReader) {
        this.excelReader = excelReader;
    }

    public ExcelWriter getExcelWriter() {
        return excelWriter;
    }

    public void setExcelWriter(ExcelWriter excelWriter) {
        this.excelWriter = excelWriter;
    }

}
