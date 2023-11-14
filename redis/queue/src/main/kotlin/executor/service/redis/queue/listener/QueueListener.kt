package executor.service.redis.queue.listener

interface QueueListener<T> {
    fun poll(): T?
}