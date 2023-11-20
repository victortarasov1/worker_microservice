package executor.service.redis.queue.listener

interface QueueExtractor {
     fun <T> poll(key: String, clazz: Class<T>) : T?
}