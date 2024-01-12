package executor.service.queue.consumer

interface QueueConsumer<T> {
    fun poll(): T?
}