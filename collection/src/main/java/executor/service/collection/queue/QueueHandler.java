package executor.service.collection.queue;

import java.util.List;
import java.util.Optional;

/**
 * A basic interface for a queue data structure that defines methods for adding and removing elements in a queue.
 *
 * @param <T> the type of elements stored in the queue
 */
public interface QueueHandler<T> {
    void add(T element);

    void addAll(List<T> elements);

    Optional<T> poll();

    List<T> removeAll();

    int getSize();
}
