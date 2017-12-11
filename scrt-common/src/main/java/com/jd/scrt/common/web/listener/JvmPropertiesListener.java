package com.jd.scrt.common.web.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * JvmPropertiesListener(web启动将properties加载至jvm properties)
 * <p/>
 * 在web.xml里，最上面加上如下配置：
 * <context-param>
 * <param-name>jvmPropertiesLocation</param-name>
 * <param-value>classpath:jvm.properties</param-value>
 * </context-param>
 * <context-param>
 * <param-name>jvmPropertiesForceSetting</param-name>
 * <param-value>false</param-value>
 * </context-param>
 * <p/>
 * <listener>
 * <listener-class>com.jd.scrt.common.web.listener.JvmPropertiesListener</listener-class>
 * </listener>
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public class JvmPropertiesListener extends AbstractServletContextListener {

    private static final long serialVersionUID = 1L;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * jvm.properties文件位置
     */
    private String jvmPropertiesLocation = "jvm.properties";
    /**
     * 是否强制赋值
     */
    private boolean jvmPropertiesForceSetting = false;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.initWebServletContext(sce);
    }

    /**
     * 初始化WebServletContext
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param sce
     */
    protected void initWebServletContext(ServletContextEvent sce) {
        logger.info("initWebServletContext begin...");
        try {
            ServletContext ctx = sce.getServletContext();
            String location = ctx.getInitParameter("jvmPropertiesLocation");
            if (location != null && location.trim().length() > 0) {
                this.jvmPropertiesLocation = location.trim();
            }
            String force = ctx.getInitParameter("jvmPropertiesForceSetting");
            if (force != null && force.trim().length() > 0) {
                this.jvmPropertiesForceSetting = Boolean.parseBoolean(force.trim());
            }
            this.initJvmProperties();
        } catch (Exception e) {
            logger.error("initWebServletContext-error!", e);
        }
    }

    /**
     * 初始化JvmProperties
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    protected void initJvmProperties() throws Exception {
        logger.info("initJvmProperties: jvmPropertiesLocation[" + jvmPropertiesLocation + "],jvmPropertiesForceSetting[" + jvmPropertiesForceSetting
                + "] begin...");
        Properties props = this.loadProperties(this.jvmPropertiesLocation);
        if (props == null) {
            logger.warn("initJvmProperties: properties is null, return!");
            return;
        }
        for (Object key : props.keySet()) {
            if (key == null) {
                continue;
            }
            String k = String.valueOf(key);
            String v = String.valueOf(props.get(key));
            if (System.getProperty(k) == null || this.jvmPropertiesForceSetting) {
                System.setProperty(k, v);
                logger.info("initJvmProperties: key[" + k + "],value[" + v + "] set succeed! ");
            } else {
                logger.info("initJvmProperties: key[" + k + "] not set! ");
            }
        }
    }

    /**
     * 加载Properties文件
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param location
     * @return
     * @throws Exception
     */
    protected Properties loadProperties(String location) throws Exception {
        Properties props = new Properties();
        InputStream is = null;
        try {
            Resource resource = resourceLoader.getResource(location);
            is = resource.getInputStream();
            props.load(is);
        } catch (IOException ex) {
            logger.error("Could not load properties from path:" + location, ex);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return props;
    }

}
