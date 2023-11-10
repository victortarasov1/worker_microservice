rootProject.name = "worker_microservice"
include("execution")
include("logback")
include("logger")
include("model")
include("webdriver")
include("collection")
include("source")
include("main")
include("report")
include("facade")
include("redis")
include("redis:configuration")
findProject(":redis:configuration")?.name = "configuration"
include("redis:queue")
findProject(":redis:queue")?.name = "queue"
