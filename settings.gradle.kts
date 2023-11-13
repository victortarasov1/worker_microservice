rootProject.name = "worker_microservice"
include("execution")
include("logback")
include("model")
include("webdriver")
include("facade")
include("redis")
include("redis:configuration")
findProject(":redis:configuration")?.name = "configuration"
include("redis:queue")
findProject(":redis:queue")?.name = "queue"
include("redis:repository")
findProject(":redis:repository")?.name = "repository"
include("aop")
findProject(":aop:logger")?.name = "logger"
include("aop:logger")
findProject(":aop:report")?.name = "report"
include("aop:report")