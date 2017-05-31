package io.sited.db.impl.jdbc.dialect;

import java.util.List;

/**
 * Oralce/DB2/MSSQLServer数据库函数适配抽象接口，主要处理SQL脚本中常用的函数适配问题</br>
 * 由于函数传参时有时是常量，有时是字段名称，因此所有函数的参数都采用字符型参数，返回值也是字符型的。
 * 如果是参数本身就是字符型常量，请使用FormatUtil.formatStrForDB()进行数据格式化，也可以直接用‘号括起来。
 * 
 * @see FormatUtil.formatStrForDB()
 * @author Gavin Lee(lixb@hsit.com.cn) at Create on Jan 11, 2012 1:18:04 PM
 * @author Su
 */
public interface FuncAdapter {

    public final static int IBM_DB2 = 0;
    public final static int ORACLE = 1;
    public final static int MSSQL = 2;
    public final static int MYSQL = 3;
    public final static int SQLITE = 4;

    /**
     * 获得当前数据库类型常量
     * 
     * @return 当前数据库类型常量（int)
     */
    public int getCurrentDBType();

    /**
     * 类别：字符串处理</br> 字符串连接函数
     * 
     * @param params
     *            参数个数不限制
     * @return 对应数据库类型的字符串
     */
    public String concat(List params);

    /**
     * 类别：字符串处理</br> 两个字符串连接函数
     * 
     * @param str1
     * @param str2
     * @return
     */
    public String concat(String str1, String str2);

    /**
     * 类别：字符串处理</br> 字符串截取函数
     * 
     * @param str
     *            字符串
     * @param start
     *            开始位置，从1开始计数
     * @param count
     *            截取数量
     * @return
     */
    public String substr(String str, int start, int count);

    /**
     * 类别：字符串处理</br> 去除字符串左右两边的空格
     * 
     * @param str
     * @return
     */
    public String trim(String str);

    /**
     * 类别：类型转换</br> 数值型数据转字符型
     * 
     * @param str
     * @return
     */
    public String num2String(String str);

    /**
     * 类别：类型转换</br> 字符型数据转数值型。内部封装，不暴露给外部，请使用各个数据库风格的函数调用
     * 
     * @param str
     * @return
     */
    public String string2Num(String str);

    /**
     * 类别：类型转换</br> 字符型数据转数值型。等同于str2num,to_number,convert函数
     * 
     * @param str
     * @return
     */
    public String castString2Num(String str);

    /**
     * 类别：类型转换</br> yyyy-mm-dd hh24:mi:ss字符型数据转日期型。内部封装，不暴露给外部，请使用各个数据库风格的函数调用
     * 
     * @param str
     * @return
     */
    public String string2Date(String str);

    /**
     * 短日期格式</br> yyyy-mm-dd
     * 
     * @param str
     * @return
     */
    public String string2ShortDate(String str);

    /**
     * 日期格式由外面控制
     * 
     * @param str
     * @param format
     * @return
     */
    public String string2Date(String str, String format);

    /**
     * 日期转字符yyyy-mm-dd hh24:mi:ss
     * 
     * @param field
     * @return
     */
    public String date2String(String field);

    /**
     * 日期转字符，格式由调用者控制
     * 
     * @param field
     * @param format
     * @return
     */
    public String date2String(String field, String format);

    /**
     * 判断一个字段数据是否为null或者''
     * 
     * @param str
     * @return
     */
    public String isNull(String str);

    /**
     * 判断一个字段数据是否不为null或者''
     * 
     * @param str
     * @return
     */
    public String isNotNull(String str);

    /**
     * 获得组织结构树,递归取孩子
     * 
     * @param orgUniqueCd
     *            需要获取组织机构根节点
     * @param year
     *            年度
     * @param level
     *            树末级组织级别，0取全部孩子
     * @param orgTpye
     *            组织单位类型，''取全部组织类型
     * @return 简化版组织单位对象集合
     */
    public String getOrgChild(String orgUniqueCd, int year, int level, String orgTpye);

    /**
     * 获得组织结构树，递归取父
     * 
     * @param orgUniqueCd
     *            需要获取组织机构孩子节点
     * @param year
     *            年度
     * @param level
     *            树顶级组件基本，0取全部父亲
     * @param orgTpye
     *            组织单位类型，''取全部组织类型
     * @return 简化版组织单位对象集合
     */
    public String getOrgParent(String orgUniqueCd, int year, int level, String orgTpye);

    /**
     * 获得行政区划树，递归取孩子
     * 
     * @param orgUniqueCd
     *            需要获取行政区划根节点
     * @param year
     *            年度
     * @param level
     *            树末级行政级别，0取全部孩子
     * @return 简化版行政区划对象
     */
    public String getDivChild(String divUniqueCd, int year, int level);

    /**
     * 获得行政区划树，递归取父亲
     * 
     * @param orgUniqueCd
     *            需要获取行政区划孩子节点
     * @param year
     *            年度
     * @param level
     *            树顶级行政级别，0取全部父亲
     * @return 简化版行政区划对象
     */
    public String getDivParent(String divUniqueCd, int year, int level);

    /**
     * 获取组织单位树的ID集合，用于外层SQL的in中
     * 
     * @param orgUniqueCd
     * @param year
     * @param level
     * @param orgTpye
     * @return
     */
    public String getOrgChildId(String orgUniqueCd, int year, int level, String orgTpye);

    /**
     * 获取组织单位树的ID集合，用于外层SQL的in中
     * 
     * @param orgUniqueCd
     * @param year
     * @param level
     * @param orgTpye
     * @return
     */
    public String getOrgParentId(String orgUniqueCd, int year, int level, String orgTpye);

    /**
     * 获取组织单位树的CD集合，用于外层SQL的in中
     * 
     * @param orgUniqueCd
     * @param year
     * @param level
     * @param orgTpye
     * @return
     */
    public String getOrgChildCd(String orgUniqueCd, int year, int level, String orgTpye);

    /**
     * 获取组织单位树的CD集合，用于外层SQL的in中
     * 
     * @param orgUniqueCd
     * @param year
     * @param level
     * @param orgTpye
     * @return
     */
    public String getOrgParentCd(String orgUniqueCd, int year, int level, String orgTpye);

    /**
     * 获取组织单位树的表名称
     * 
     * @param orgUniqueCd
     * @param year
     * @param level
     * @param orgTpye
     * @return
     */
    public String getOrgChildTb(String orgUniqueCd, int year, int level, String orgTpye);

    /**
     * 获取行政区划树的ID集合，用于外层SQL的in中
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getDivChildId(String divUniqueCd, int year, int level);

    /**
     * 获取行政区划树的ID集合，用于外层SQL的in中
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getDivParentId(String divUniqueCd, int year, int level);

    /**
     * 获取行政区划的CD，用于外层的SQL中
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getDivChildCd(String divUniqueCd, int year, int level);

    /**
     * 获取行政区划的CD，用于外层的SQL中
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getDivParentCd(String divUniqueCd, int year, int level);

    /**
     * 获取行政区划的表名称，同其他表可以做关联查询
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getDivChildTb(String divUniqueCd, int year, int level);

    /**
     * 获得行政区划管辖范围(业务类型为CM公共资源）
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getOrgManagerDiv(String divUniqueCd, int year, int level);

    /**
     * 获得行政区划管辖范围
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @param bizTypeEk
     *            业务类型[CM-公共资源,PC-计划合同,CA-合同分配，MS-物资,PT-生产]
     * @return
     */
    public String getOrgManagerDiv(String divUniqueCd, int year, int level, String bizTypeEk);

    /**
     * 获得行政区划管辖范围(不进行排序，执行效率是毫秒级)(业务类型为CM公共资源）
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getOrgManagerDivNotOrder(String divUniqueCd, int year, int level);

    /**
     * 获得行政区划管辖范围(不进行排序，执行效率是毫秒级)
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @param bizTypeEk
     *            业务类型[CM-公共资源,PC-计划合同,CA-合同分配，MS-物资,PT-生产]
     * @return
     */
    public String getOrgManagerDivNotOrder(String divUniqueCd, int year, int level, String bizTypeEk);

    /**
     * 获得行政区划管辖范围的CD(不进行排序，执行效率是毫秒级)(业务类型为CM公共资源）
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getOrgManagerDivCdNotOrder(String divUniqueCd, int year, int level);

    /**
     * 获得行政区划管辖范围的CD(不进行排序，执行效率是毫秒级)
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @param bizTypeEk
     *            业务类型[CM-公共资源,PC-计划合同,CA-合同分配，MS-物资,PT-生产]
     * @return
     */
    public String getOrgManagerDivCdNotOrder(String divUniqueCd, int year, int level, String bizTypeEk);

    /**
     * 获得行政区划管辖范围表名称(业务类型为CM公共资源）
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @return
     */
    public String getOrgManagerDivTb(String divUniqueCd, int year, int level);

    /**
     * 获得行政区划管辖范围表名称
     * 
     * @param divUniqueCd
     * @param year
     * @param level
     * @param bizTypeEk
     *            业务类型[CM-公共资源,PC-计划合同,CA-合同分配，MS-物资,PT-生产]
     * @return
     */
    public String getOrgManagerDivTb(String divUniqueCd, int year, int level, String bizTypeEk);

    /**
     * 获取数据库主键UUID
     * 
     * @return
     */
    public String getUUID();

    /**
     * 如果field的值为空，用val替换，只能用于Select语句
     * 
     * @param field
     * @param val
     * @return
     */
    public String replaceNull(String field, String val);

}