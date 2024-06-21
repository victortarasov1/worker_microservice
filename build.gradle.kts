plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    id("com.google.cloud.tools.jib") version "3.4.2"
    id("io.freefair.lombok") version "8.4"
}

group = "executor.service"
version = "0.0.1-SNAPSHOT"
extra["springCloudVersion"] = "2023.0.2"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
    implementation("org.aspectj:aspectjweaver:1.9.20.1")
    implementation("org.seleniumhq.selenium:selenium-java:4.13.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
jib.to.image = "victortarasov/executor-worker-service:v2"


