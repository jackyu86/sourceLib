package io.sited.queue.impl;

import io.sited.queue.Queue;

/**
 * @author chi
 */
public interface QueueProvider {
    <T> Queue<T> create(Class<T> type);
}
