package com.jd.scrt.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * 抽象ServletContextListener
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public abstract class AbstractServletContextListener extends HttpServlet implements ServletContextListener {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("contextInitialized...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("contextDestroyed...");
    }

}
