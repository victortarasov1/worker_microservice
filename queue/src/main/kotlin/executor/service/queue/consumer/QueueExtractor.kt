package executor.service.queue.consumer

interface QueueExtractor {
     fun <T> poll(key: String, clazz: Class<T>) : T?
}