package executor.service.queue.listener

interface QueueExtractor {
     fun <T> poll(key: String, clazz: Class<T>) : T?
}