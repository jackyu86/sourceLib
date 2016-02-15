package com.jd.scrt.common.solr;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.StreamingResponseCallback;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * Solr操作模板
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public interface SolrTemplate {

    /**
     * 处理solr请求
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param solrRequest
     * @return
     * @throws Exception
     */
    public SolrResponse process(SolrRequest solrRequest) throws Exception;

    /**
     * 添加SolrInputDocument集合
     *
     * @param docs the collection of documents
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse add(Collection<SolrInputDocument> docs) throws Exception;

    /**
     * 添加SolrInputDocument集合并限制超时
     *
     * @param docs           the collection of documents
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse add(Collection<SolrInputDocument> docs, int commitWithinMs) throws Exception;

    /**
     * 添加Bean集合
     *
     * @param beans the collection of beans
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse addBeans(Collection<?> beans) throws Exception;

    /**
     * 添加Bean集合并限制超时
     *
     * @param beans          the collection of beans
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse addBeans(Collection<?> beans, int commitWithinMs) throws Exception;

    /**
     * 添加SolrInputDocument对象
     *
     * @param doc the input document
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse add(SolrInputDocument doc) throws Exception;

    /**
     * 添加SolrInputDocument对象并限制超时
     *
     * @param doc            the input document
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse add(SolrInputDocument doc, int commitWithinMs) throws Exception;

    /**
     * 添加Bean对象
     *
     * @param obj the input bean
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse addBean(Object obj) throws Exception;

    /**
     * 添加Bean对象并限制超时
     *
     * @param obj            the input bean
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse addBean(Object obj, int commitWithinMs) throws Exception;

    /**
     * 提交请求
     * <p/>
     * waitFlush=true and waitSearcher=true to be inline with the defaults for
     * plain HTTP access
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse commit() throws Exception;

    /**
     * 优化索引
     * <p/>
     * waitFlush=true and waitSearcher=true to be inline with the defaults for
     * plain HTTP access
     * <p/>
     * Note: In most cases it is not required to do explicit optimize
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse optimize() throws Exception;

    /**
     * 定制化提交请求
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse commit(boolean waitFlush, boolean waitSearcher) throws Exception;

    /**
     * 定制化提交请求
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @param softCommit   makes index changes visible while neither fsync-ing index
     *                     files nor writing a new index descriptor
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse commit(boolean waitFlush, boolean waitSearcher, boolean softCommit) throws Exception;

    /**
     * 定制化优化索引
     * <p/>
     * Note: In most cases it is not required to do explicit optimize
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse optimize(boolean waitFlush, boolean waitSearcher) throws Exception;

    /**
     * 定制化优化索引
     * <p/>
     * Note: In most cases it is not required to do explicit optimize
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @param maxSegments  optimizes down to at most this number of segments
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse optimize(boolean waitFlush, boolean waitSearcher, int maxSegments) throws Exception;

    /**
     * 回滚
     * <p/>
     * Note that this is not a true rollback as in databases. Content you have
     * previously added may have been committed due to autoCommit, buffer full,
     * other client performing a commit etc.
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse rollback() throws Exception;

    /**
     * 根据ID删除document
     *
     * @param id the ID of the document to delete
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse deleteById(String id) throws Exception;

    /**
     * 根据ID删除document并限制超时
     *
     * @param id             the ID of the document to delete
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since 3.6
     */
    public UpdateResponse deleteById(String id, int commitWithinMs) throws Exception;

    /**
     * 根据ID集合删除document
     *
     * @param ids the list of document IDs to delete
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse deleteById(List<String> ids) throws Exception;

    /**
     * 根据ID集合删除document并限制超时
     *
     * @param ids            the list of document IDs to delete
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since 3.6
     */
    public UpdateResponse deleteById(List<String> ids, int commitWithinMs) throws Exception;

    /**
     * 根据查询条件删除document
     *
     * @param query the query expressing what documents to delete
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse deleteByQuery(String query) throws Exception;

    /**
     * 根据查询条件删除document并限制超时
     *
     * @param query          the query expressing what documents to delete
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since 3.6
     */
    public UpdateResponse deleteByQuery(String query, int commitWithinMs) throws Exception;

    /**
     * 对Server进行ping操作
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public SolrPingResponse ping() throws Exception;

    /**
     * 查询
     *
     * @param params an object holding all key/value parameters to send along the
     *               request
     */
    public QueryResponse query(SolrParams params) throws Exception;

    /**
     * 查询并指定HttpMethod
     *
     * @param params an object holding all key/value parameters to send along the
     *               request
     * @param method specifies the HTTP method to use for the request, such as GET
     *               or POST
     */
    public QueryResponse query(SolrParams params, METHOD method) throws Exception;

    /**
     * Query solr, and stream the results. Unlike the standard query, this will
     * send events for each Document rather then add them to the QueryResponse.
     * <p/>
     * Although this function returns a 'QueryResponse' it should be used with
     * care since it excludes anything that was passed to callback. Also note
     * that future version may pass even more info to the callback and may not
     * return the results in the QueryResponse.
     *
     * @since solr 4.0
     */
    public QueryResponse queryAndStreamResponse(SolrParams params, StreamingResponseCallback callback) throws Exception;

    /**
     * SolrServer implementations need to implement how a request is actually
     * processed
     */
    public NamedList<Object> request(final SolrRequest request) throws Exception;

    /**
     * 销毁
     *
     * @since solr 4.0
     */
    public void shutdown() throws Exception;
}
