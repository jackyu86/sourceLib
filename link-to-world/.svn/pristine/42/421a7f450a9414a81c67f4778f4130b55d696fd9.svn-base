package io.sited.queue;

import com.google.common.collect.Maps;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.Provider;
import io.sited.StandardException;
import io.sited.queue.impl.QueueProvider;
import io.sited.queue.impl.jdk.JDKQueueProvider;
import io.sited.queue.impl.rebbitmq.RabbitMQQueueProvider;
import io.sited.util.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chi
 */
@ModuleInfo(name = "queue")
public class QueueModule extends Module {
    private final Logger logger = LoggerFactory.getLogger(QueueModule.class);

    private final Map<Class<?>, Queue<?>> queues = Maps.newHashMap();
    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private QueueProvider provider;

    @Override
    protected void configure() throws Exception {
        QueueModuleOptions options = options(QueueModuleOptions.class);

        if (options.rabbitMQ != null) {
            provider = new RabbitMQQueueProvider();
        } else {
            provider = new JDKQueueProvider(executor);
        }

        bind(QueueConfig.class, queueConfig());
        export(QueueConfig.class);
    }

    private Provider<QueueConfig, Module> queueConfig() {
        return module -> new QueueConfig() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> Queue<T> get(Class<T> type) {
                if (!queues.containsKey(type)) {
                    throw new StandardException("missing queue, type={}", type);
                }
                return (Queue<T>) queues.get(type);
            }

            @Override
            public <T> Queue<T> create(Class<T> type) {
                logger.info("create queue, type={}", type.getCanonicalName());
                Queue<T> queue = provider.create(type);
                Type queueClass = Types.generic(Queue.class, type);
                module.bind(queueClass, queue);
                module.export(queueClass);
                queues.put(type, queue);
                return queue;
            }

            @Override
            public ExecutorService executor() {
                return executor;
            }
        };
    }
}
