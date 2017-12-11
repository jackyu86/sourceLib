package io.sited.queue.impl.jdk;

import com.google.common.collect.Lists;
import io.sited.queue.Queue;
import io.sited.queue.QueueHandler;
import io.sited.queue.impl.QueueProvider;
import io.sited.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author chi
 */
public class JDKQueueProvider implements QueueProvider {
    private final ExecutorService executor;

    public JDKQueueProvider(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public <T> Queue<T> create(Class<T> type) {
        return new DefaultQueueImpl<>(executor, type);
    }

    private static class DefaultQueueImpl<T> implements Queue<T> {
        private final Logger logger = LoggerFactory.getLogger(DefaultQueueImpl.class);

        private final ExecutorService pool;
        private final List<QueueHandler<T>> handlers = Lists.newArrayList();
        private final Class<T> type;

        DefaultQueueImpl(ExecutorService pool, Class<T> type) {
            this.pool = pool;
            this.type = type;
        }

        @Override
        public void publish(T event) {
            pool.execute(() -> handlers.forEach(handler -> handle(handler, event)));
        }

        void handle(QueueHandler<T> handler, T event) {
            try {
                handler.handle(event);
            } catch (Throwable e) {
                logger.info("failed to process event, type={}, handler={}, event={}", type, handler, JSON.toJSON(event), e);
            }
        }

        @Override
        public Queue<T> addHandler(QueueHandler<T> handler) {
            handlers.add(handler);
            return this;
        }
    }

}
