package executor.service.source.listener;


public interface SourceListener {
    /**
     * Fetches data from a remote source and enqueues it into a queue.
     */
    void fetchData();
}
