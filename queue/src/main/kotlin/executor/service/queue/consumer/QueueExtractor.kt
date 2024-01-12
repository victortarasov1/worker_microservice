package executor.service.queue.consumer

internal interface QueueExtractor {
     fun <T> poll(key: String, clazz: Class<T>) : T?
}