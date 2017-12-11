package com.jd.scrt.common.solr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jd.scrt.common.orm.page.Page;
import com.jd.scrt.common.solr.support.SolrDateConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;


/**
 * 默认Solr操作模板
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public class DefaultSolrTemplate extends AbstractSolrTemplate {

    public static final String EXCLUDE_FIELDS = "class";

    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";

    private BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
    private SolrDateConverter dateConverter = new SolrDateConverter();

    private String excludeFields = "";

    public DefaultSolrTemplate() {
        this.initBeanUtilsBean();
    }

    public void init() throws Exception {
        super.init();
        this.initBeanUtilsBean();
    }

    protected void initBeanUtilsBean() {
        this.getBeanUtilsBean().getConvertUtils().register(this.getDateConverter(), Date.class);// 注册一个日期转换类
        this.getBeanUtilsBean().getConvertUtils().register(this.getDateConverter(), String.class);// 注册一个日期转换类
    }

    /**
     * 根据ID查询
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param id
     * @param clazzT
     * @return
     * @throws Exception
     */
    public <T> T queryById(String id, Class<T> clazzT) throws Exception {
        String query = "id:" + id;
        return this.queryOne(query, clazzT);
    }

    /**
     * 查询单个数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param query
     * @param clazzT
     * @return
     * @throws Exception
     */
    public <T> T queryOne(String query, Class<T> clazzT) throws Exception {
        SolrQuery queryParams = this.buildSolrQuery(query, null, 0, 1);
        QueryResponse rsp = this.query(queryParams);
        if (rsp == null || CollectionUtils.isEmpty(rsp.getResults())) {
            return null;
        }
        SolrDocumentList docsList = rsp.getResults();
        SolrDocument doc = docsList.get(0);
        T t = clazzT.newInstance();
        this.convertToBean(t, doc);
        return t;
    }

    /**
     * 常规查询
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param query
     * @param sortMap
     * @param start
     * @param rows
     * @return
     * @throws Exception
     */
    public QueryResponse query(String query, Map<String, String> sortMap, Integer start, Integer rows) throws Exception {
        SolrQuery queryParams = this.buildSolrQuery(query, sortMap, start, rows);
        return this.query(queryParams);
    }

    /**
     * 分页查询
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param query
     * @param sortMap
     * @param page
     * @param clazzT
     * @return
     * @throws Exception
     */
    public <T> Page<T> query(String query, Map<String, String> sortMap, Page<T> page, Class<T> clazzT) throws Exception {
        SolrQuery queryParams = this.buildSolrQuery(query, sortMap, page.getOffset(), page.getPageSize());
        return this.query(queryParams, page, clazzT);
    }

    /**
     * 分页查询
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param queryParams
     * @param page
     * @param clazzT
     * @return
     * @throws Exception
     */
    public <T> Page<T> query(SolrQuery queryParams, Page<T> page, Class<T> clazzT) throws Exception {
        queryParams.setStart(page.getOffset());
        queryParams.setRows(page.getPageSize());
        QueryResponse rsp = this.query(queryParams);
        if (rsp == null || rsp.getResults() == null) {
            return page;
        }
        SolrDocumentList docsList = rsp.getResults();
        if (!CollectionUtils.isEmpty(docsList)) {
            List<T> entites = this.convertToBeans(clazzT, docsList);
            page.setResult(entites);
        }

        if (docsList.getNumFound() >= Integer.MAX_VALUE) {
            page.setTotalRow(Integer.MAX_VALUE);
        } else {
            page.setTotalRow((int) docsList.getNumFound());
        }
        page.refresh();
        return page;
    }

    /**
     * 条件查询
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param query
     * @param sortMap
     * @param offset
     * @param rows
     * @param clazzT
     * @return
     * @throws Exception
     */
    public <T> List<T> query(String query, Map<String, String> sortMap, int offset, int rows, Class<T> clazzT) throws Exception {
        SolrQuery queryParams = this.buildSolrQuery(query, sortMap, offset, rows);
        return this.query(queryParams, clazzT);
    }

    /**
     * 条件查询
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param queryParams
     * @param clazzT
     * @return
     * @throws Exception
     */
    public <T> List<T> query(SolrQuery queryParams, Class<T> clazzT) throws Exception {
        QueryResponse rsp = this.query(queryParams);
        if (rsp == null || CollectionUtils.isEmpty(rsp.getResults())) {
            return null;
        }
        return this.convertToBeans(clazzT, rsp.getResults());
    }

    /**
     * 构建SolrQuery对象
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param query
     * @param sortMap
     * @param start
     * @param rows
     * @return
     * @throws Exception
     */
    protected SolrQuery buildSolrQuery(String query, Map<String, String> sortMap, Integer start, Integer rows) throws Exception {
        SolrQuery queryParams = new SolrQuery();
        // 设置查询条件
        queryParams.setQuery(query);
        // 设置排序条件
        if (sortMap != null && !sortMap.isEmpty()) {
            for (String key : sortMap.keySet()) {
                String val = sortMap.get(key);
                if (StringUtils.isEmpty(key) || StringUtils.isEmpty(val)) {
                    continue;
                }
                if (ORDER_ASC.equalsIgnoreCase(val)) {
                    queryParams.addSort(key, SolrQuery.ORDER.asc);
                } else if (ORDER_DESC.equalsIgnoreCase(val)) {
                    queryParams.addSort(key, SolrQuery.ORDER.desc);
                }
            }
        }
        queryParams.setStart(start);
        queryParams.setRows(rows);
        return queryParams;
    }

    /**
     * 将SolrDocumentList转换为List<Bean>
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param clazzT
     * @param list
     * @return
     * @throws Exception
     */
    protected <T> List<T> convertToBeans(Class<T> clazzT, SolrDocumentList list) throws Exception {
        List<T> result = new ArrayList<T>();
        for (SolrDocument doc : list) {
            if (doc == null) {
                continue;
            }
            T t = clazzT.newInstance();
            this.convertToBean(t, doc);
            result.add(t);
        }
        return result;
    }

    /**
     * 将SolrDocument转换为普通Bean
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @param doc
     * @return
     * @throws Exception
     */
    protected <T> T convertToBean(T t, SolrDocument doc) throws Exception {
        if (doc == null) {
            return null;
        }
        this.getBeanUtilsBean().populate(t, doc);
        return t;
    }

    /**
     * 将List<Object>转换为List<SolrInputDocument>
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param list
     * @return
     * @throws Exception
     */
    protected List<SolrInputDocument> convertToSolrInputDocumentList(List<?> list) throws Exception {
        List<SolrInputDocument> result = new ArrayList<SolrInputDocument>();
        for (Object obj : list) {
            if (obj == null) {
                continue;
            }
            SolrInputDocument doc = this.convertToSolrInputDocument(obj);
            if (doc != null) {
                result.add(doc);
            }
        }
        return result;
    }

    /**
     * 将Object转换为SolrInputDocument
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param obj
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected SolrInputDocument convertToSolrInputDocument(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        SolrInputDocument doc = new SolrInputDocument();
        Map<String, Object> map = this.getBeanUtilsBean().describe(obj);
        for (String key : map.keySet()) {
            if (StringUtils.isEmpty(key) || this.isExcludeField(key) || map.get(key) == null) {
                continue;
            }
            doc.addField(key, map.get(key));
        }
        return doc;
    }

    /**
     * 将Date对象转换为String
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param value
     * @return
     * @throws Exception
     */
    protected String convertToString(Date value) throws Exception {
        return (String) this.getDateConverter().convert(String.class, value);
    }

    /**
     * 将String对象转换为Date
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param value
     * @return
     * @throws Exception
     */
    protected Date convertToDate(String value) throws Exception {
        return (Date) this.getDateConverter().convert(Date.class, value);
    }

    /**
     * 是否排除字段的转换
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param field
     * @return
     * @throws Exception
     */
    protected boolean isExcludeField(String field) throws Exception {
        if (EXCLUDE_FIELDS.contains(field)) {
            return true;
        }
        return this.getExcludeFields().contains(field);
    }

    // ---------- getter and setter ----------//
    public BeanUtilsBean getBeanUtilsBean() {
        return beanUtilsBean;
    }

    public void setBeanUtilsBean(BeanUtilsBean beanUtilsBean) {
        this.beanUtilsBean = beanUtilsBean;
    }

    public SolrDateConverter getDateConverter() {
        return dateConverter;
    }

    public void setDateConverter(SolrDateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    public String getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(String excludeFields) {
        this.excludeFields = excludeFields;
    }

}
