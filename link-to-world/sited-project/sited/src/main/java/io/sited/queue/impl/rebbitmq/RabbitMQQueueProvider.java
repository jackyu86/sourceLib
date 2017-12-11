package io.sited.queue.impl.rebbitmq;

import io.sited.StandardException;
import io.sited.queue.Queue;
import io.sited.queue.impl.QueueProvider;

/**
 * @author chi
 */
public class RabbitMQQueueProvider implements QueueProvider {
    @Override
    public <T> Queue<T> create(Class<T> type) {
        throw new StandardException("Not support");
    }
}
