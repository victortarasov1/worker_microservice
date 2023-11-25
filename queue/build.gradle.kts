plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

allprojects {
    apply(plugin= "kotlin")
    apply(plugin= "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("redis.clients:jedis:4.4.6")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation(project(":model"))
        implementation(project(":aop:logger"))
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.3")
    }

}