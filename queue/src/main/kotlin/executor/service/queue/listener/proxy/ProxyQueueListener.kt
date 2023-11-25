package executor.service.queue.listener.proxy

import executor.service.model.ProxyConfigHolder
import executor.service.queue.listener.QueueListener

interface ProxyQueueListener: QueueListener<ProxyConfigHolder>