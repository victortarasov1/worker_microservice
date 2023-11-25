package executor.service.queue.listener

interface QueueListener<T> {
    fun poll(): T?
}