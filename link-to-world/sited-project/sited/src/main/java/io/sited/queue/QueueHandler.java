package io.sited.queue;

/**
 * @author chi
 */
public interface QueueHandler<T> {
    void handle(T event) throws Throwable;
}
