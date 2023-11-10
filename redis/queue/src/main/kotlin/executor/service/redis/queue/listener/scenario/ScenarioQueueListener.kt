package executor.service.redis.queue.listener.scenario

import executor.service.model.Scenario
import executor.service.redis.queue.listener.QueueListener

interface ScenarioQueueListener: QueueListener<List<Scenario>>