package executor.service.redis.queue.listener.proxy

import executor.service.model.ProxyConfigHolder
import executor.service.redis.queue.listener.QueueListener

interface ProxyQueueListener: QueueListener<ProxyConfigHolder?>