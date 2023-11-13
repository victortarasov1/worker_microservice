package executor.service.redis.queue.producer

import executor.service.model.Scenario

interface QueueProducer {
    fun add(scenarios: List<Scenario>)
}