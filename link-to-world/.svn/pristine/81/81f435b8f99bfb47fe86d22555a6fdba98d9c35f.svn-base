package io.sited.queue;

/**
 * @author chi
 */
public interface Queue<T> {
    void publish(T value);

    Queue<T> addHandler(QueueHandler<T> handler);
}
