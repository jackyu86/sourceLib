package io.sited.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.common.collect.Maps;
import io.sited.Module;
import io.sited.ModuleInfo;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author chi
 */
@ModuleInfo(name = "log")
public class LogModule extends Module {
    static {
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    @Override
    protected void configure() throws Exception {
        LogOptions options = options(LogOptions.class);
        if (options.level != null) {
            Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.valueOf(options.level));

            Map<String, String> loggers = Maps.newHashMap();
            loggers.put("org.mongodb", "WARN");
            loggers.put("org.xnio", "WARN");
            loggers.put("com.zaxxer.hikari", "WARN");

            if (options.loggers != null) {
                loggers.putAll(options.loggers);
            }

            loggers.forEach((key, value) -> {
                Logger logger = (Logger) LoggerFactory.getLogger(key);
                logger.setLevel(Level.valueOf(value));
            });
        }
    }
}
