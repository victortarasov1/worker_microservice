package executor.service.queue.consumer.proxy

import executor.service.model.ProxyConfigHolder
import executor.service.queue.consumer.QueueConsumer

interface ProxyConsumer: QueueConsumer<ProxyConfigHolder>