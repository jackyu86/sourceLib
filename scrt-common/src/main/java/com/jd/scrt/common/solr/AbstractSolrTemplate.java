package com.jd.scrt.common.solr;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.StreamingResponseCallback;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;

/**
 * 抽象Solr操作模板
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public abstract class AbstractSolrTemplate implements SolrTemplate {

    private SolrServer solrServer;

    public void init() throws Exception {
    }

    public void destroy() throws Exception {
    }

    public SolrResponse process(SolrRequest solrRequest) throws Exception {
        return solrRequest.process(this.getSolrServer());
    }

    // ***************************************************************************/

    /**
     * Adds a collection of documents
     *
     * @param docs the collection of documents
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse add(Collection<SolrInputDocument> docs) throws Exception {
        return this.getSolrServer().add(docs);
    }

    /**
     * Adds a collection of documents, specifying max time before they become
     * committed
     *
     * @param docs           the collection of documents
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse add(Collection<SolrInputDocument> docs, int commitWithinMs) throws Exception {
        return this.getSolrServer().add(docs, commitWithinMs);
    }

    /**
     * Adds a collection of beans
     *
     * @param beans the collection of beans
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse addBeans(Collection<?> beans) throws Exception {
        return this.getSolrServer().addBeans(beans);
    }

    /**
     * Adds a collection of beans specifying max time before they become
     * committed
     *
     * @param beans          the collection of beans
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse addBeans(Collection<?> beans, int commitWithinMs) throws Exception {
        return this.getSolrServer().addBeans(beans, commitWithinMs);
    }

    /**
     * Adds a single document
     *
     * @param doc the input document
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse add(SolrInputDocument doc) throws Exception {
        return this.getSolrServer().add(doc);
    }

    /**
     * Adds a single document specifying max time before it becomes committed
     *
     * @param doc            the input document
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse add(SolrInputDocument doc, int commitWithinMs) throws Exception {
        return this.getSolrServer().add(doc, commitWithinMs);
    }

    /**
     * Adds a single bean
     *
     * @param obj the input bean
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse addBean(Object obj) throws Exception {
        return this.getSolrServer().addBean(obj);
    }

    /**
     * Adds a single bean specifying max time before it becomes committed
     *
     * @param obj            the input bean
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since solr 3.5
     */
    public UpdateResponse addBean(Object obj, int commitWithinMs) throws Exception {
        return this.getSolrServer().addBean(obj, commitWithinMs);
    }

    /**
     * Performs an explicit commit, causing pending documents to be committed
     * for indexing
     * <p/>
     * waitFlush=true and waitSearcher=true to be inline with the defaults for
     * plain HTTP access
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse commit() throws Exception {
        return this.getSolrServer().commit();
    }

    /**
     * Performs an explicit optimize, causing a merge of all segments to one.
     * <p/>
     * waitFlush=true and waitSearcher=true to be inline with the defaults for
     * plain HTTP access
     * <p/>
     * Note: In most cases it is not required to do explicit optimize
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse optimize() throws Exception {
        return this.getSolrServer().optimize();
    }

    /**
     * Performs an explicit commit, causing pending documents to be committed
     * for indexing
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse commit(boolean waitFlush, boolean waitSearcher) throws Exception {
        return this.getSolrServer().commit(waitFlush, waitSearcher);
    }

    /**
     * Performs an explicit commit, causing pending documents to be committed
     * for indexing
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @param softCommit   makes index changes visible while neither fsync-ing index
     *                     files nor writing a new index descriptor
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse commit(boolean waitFlush, boolean waitSearcher, boolean softCommit) throws Exception {
        return this.getSolrServer().commit(waitFlush, waitSearcher, softCommit);
    }

    /**
     * Performs an explicit optimize, causing a merge of all segments to one.
     * <p/>
     * Note: In most cases it is not required to do explicit optimize
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse optimize(boolean waitFlush, boolean waitSearcher) throws Exception {
        return this.getSolrServer().optimize(waitFlush, waitSearcher);
    }

    /**
     * Performs an explicit optimize, causing a merge of all segments to one.
     * <p/>
     * Note: In most cases it is not required to do explicit optimize
     *
     * @param waitFlush    block until index changes are flushed to disk
     * @param waitSearcher block until a new searcher is opened and registered as the
     *                     main query searcher, making the changes visible
     * @param maxSegments  optimizes down to at most this number of segments
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse optimize(boolean waitFlush, boolean waitSearcher, int maxSegments) throws Exception {
        return this.getSolrServer().optimize(waitFlush, waitSearcher, maxSegments);
    }

    /**
     * Performs a rollback of all non-committed documents pending.
     * <p/>
     * Note that this is not a true rollback as in databases. Content you have
     * previously added may have been committed due to autoCommit, buffer full,
     * other client performing a commit etc.
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse rollback() throws Exception {
        return this.getSolrServer().rollback();
    }

    /**
     * Deletes a single document by unique ID
     *
     * @param id the ID of the document to delete
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse deleteById(String id) throws Exception {
        return this.getSolrServer().deleteById(id);
    }

    /**
     * Deletes a single document by unique ID, specifying max time before commit
     *
     * @param id             the ID of the document to delete
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since 3.6
     */
    public UpdateResponse deleteById(String id, int commitWithinMs) throws Exception {
        return this.getSolrServer().deleteById(id, commitWithinMs);
    }

    /**
     * Deletes a list of documents by unique ID
     *
     * @param ids the list of document IDs to delete
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse deleteById(List<String> ids) throws Exception {
        return this.getSolrServer().deleteById(ids);
    }

    /**
     * Deletes a list of documents by unique ID, specifying max time before
     * commit
     *
     * @param ids            the list of document IDs to delete
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since 3.6
     */
    public UpdateResponse deleteById(List<String> ids, int commitWithinMs) throws Exception {
        return this.getSolrServer().deleteById(ids, commitWithinMs);
    }

    /**
     * Deletes documents from the index based on a query
     *
     * @param query the query expressing what documents to delete
     * @throws IOException If there is a low-level I/O error.
     */
    public UpdateResponse deleteByQuery(String query) throws Exception {
        return this.getSolrServer().deleteByQuery(query);
    }

    /**
     * Deletes documents from the index based on a query, specifying max time
     * before commit
     *
     * @param query          the query expressing what documents to delete
     * @param commitWithinMs max time (in ms) before a commit will happen
     * @throws IOException If there is a low-level I/O error.
     * @since 3.6
     */
    public UpdateResponse deleteByQuery(String query, int commitWithinMs) throws Exception {
        return this.getSolrServer().deleteByQuery(query, commitWithinMs);
    }

    /**
     * Issues a ping request to check if the server is alive
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public SolrPingResponse ping() throws Exception {
        return this.getSolrServer().ping();
    }

    /**
     * Performs a query to the Solr server
     *
     * @param params an object holding all key/value parameters to send along the
     *               request
     */
    public QueryResponse query(SolrParams params) throws Exception {
        return this.getSolrServer().query(params);
    }

    /**
     * Performs a query to the Solr server
     *
     * @param params an object holding all key/value parameters to send along the
     *               request
     * @param method specifies the HTTP method to use for the request, such as GET
     *               or POST
     */
    public QueryResponse query(SolrParams params, METHOD method) throws Exception {
        return this.getSolrServer().query(params, method);
    }

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
    public QueryResponse queryAndStreamResponse(SolrParams params, StreamingResponseCallback callback) throws Exception {
        return this.getSolrServer().queryAndStreamResponse(params, callback);
    }

    /**
     * SolrServer implementations need to implement how a request is actually
     * processed
     */
    public NamedList<Object> request(final SolrRequest request) throws Exception {
        return this.getSolrServer().request(request);
    }

    /**
     * Release allocated resources.
     *
     * @since solr 4.0
     */
    public void shutdown() throws Exception {
        this.getSolrServer().shutdown();
    }

    // ---------- getter and setter ----------//
    public SolrServer getSolrServer() {
        return solrServer;
    }

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }

}
