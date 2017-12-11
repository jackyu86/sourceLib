package io.sited.queue;

import java.util.concurrent.ExecutorService;

/**
 * @author chi
 */
public interface QueueConfig {
    <T> Queue<T> get(Class<T> type);

    <T> Queue<T> create(Class<T> type);

    ExecutorService executor();
}
