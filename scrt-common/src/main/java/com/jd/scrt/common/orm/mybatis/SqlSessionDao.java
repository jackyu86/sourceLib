package com.jd.scrt.common.orm.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.jd.scrt.common.orm.page.Page;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;


/**
 * Mybatis SqlSession实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class SqlSessionDao {

    private static final Logger logger = Logger.getLogger(SqlSessionDao.class);

    private SqlSession sqlSession;

    /**
     * 插入操作
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @return
     * @throws Exception
     */
    public int insert(String statement, Object parameter) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>insert: statement[" + statement + "]");
        }
        return this.getSqlSession().insert(statement, parameter);
    }

    /**
     * 更新操作
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @return
     * @throws Exception
     */
    public int update(String statement, Object parameter) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>update: statement[" + statement + "]");
        }
        return this.getSqlSession().update(statement, parameter);
    }

    /**
     * 删除操作
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @return
     * @throws Exception
     */
    public int delete(String statement, Object parameter) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>delete: statement[" + statement + "]");
        }
        return this.getSqlSession().delete(statement, parameter);
    }

    /**
     * 查询单条记录
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @return
     * @throws Exception
     */
    public Object selectOne(String statement, Object parameter) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>selectOne: statement[" + statement + "]");
        }
        return this.getSqlSession().selectOne(statement, parameter);
    }

    /**
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @param mapKey
     * @return
     * @throws Exception
     */
    public Map<?, ?> selectMap(String statement, Object parameter, String mapKey) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>selectMap: statement[" + statement + "], mapKey[" + mapKey + "]");
        }
        return this.getSqlSession().selectMap(statement, parameter, mapKey);
    }

    /**
     * 查询列表
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @return
     * @throws Exception
     */
    public List<?> selectList(String statement, Object parameter) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>selectList: statement[" + statement + "]");
        }
        return this.getSqlSession().selectList(statement, parameter);
    }

    /**
     * 查询第一条记录(如果结果集不为空，则返回第一条记录)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @return
     * @throws Exception
     */
    public Object selectFirst(String statement, Object parameter) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>selectFirst: statement[" + statement + "]");
        }
        List<?> returnList = this.selectList(statement, parameter, 0, 1);
        if (returnList == null || returnList.size() == 0) {
            return null;
        } else {
            return returnList.get(0);
        }
    }

    /**
     * 支持 limit 语法查询， offset < [rownum] <= (offset+rows)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @param offset
     * @param rows
     * @return
     * @throws Exception
     */
    public List<?> selectList(String statement, Object parameter, int offset, int rows) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>selectList: statement[" + statement + "], offset[" + offset + "], rows[" + rows + "]");
        }
        return this.getSqlSession().selectList(statement, parameter, new RowBounds(offset, rows));
    }

    /**
     * 分页查询
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @param page
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> Page<T> selectList(String statement, Object parameter, Page<T> page) throws Exception {
        List<T> lists = (List<T>) this.selectList(statement, parameter, page.getOffset(), page.getPageSize());
        page.setResult(lists);
        page.setTotalRow(selectCount(statement, parameter));
        page.refresh();
        return page;
    }

    /**
     * 查询结果集条数
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @param parameter
     * @return
     * @throws Exception
     */
    public int selectCount(String statement, Object parameter) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("===>selectCount: statement[" + statement + "]");
        }
        int count = 0;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            MappedStatement mst = this.getMappedStatement(statement);
            BoundSql boundSql = mst.getBoundSql(parameter);

            String sql = " select count(*) total_count from (" + boundSql.getSql() + ") t_total";

            if (logger.isDebugEnabled()) {
                logger.debug("===>selectCount: sql[" + sql + "]");
            }
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setQueryTimeout(mst.getTimeout());
            DefaultParameterHandler dph = new DefaultParameterHandler(mst, parameter, boundSql);
            dph.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total_count");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("<===selectCount: count[" + count + "]");
            }
        } catch (Exception e) {
            count = 0;
            logger.error("===>selectCount-error: statement[" + statement + "]", e);
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return count;
    }

    /**
     * 获取数据库连接
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        Configuration config = this.getConfiguration();
        return config.getEnvironment().getDataSource().getConnection();
    }

    /**
     * 获取映射信息
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param statement
     * @return
     * @throws Exception
     */
    public MappedStatement getMappedStatement(String statement) throws Exception {
        Configuration config = this.getConfiguration();
        return config.getMappedStatement(statement);
    }

    /**
     * 获取配置信息
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public Configuration getConfiguration() throws Exception {
        return this.getSqlSession().getConfiguration();
    }

    // ---------- getter and setter ----------//
    protected SqlSession getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

}
