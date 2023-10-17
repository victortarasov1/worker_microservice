dependencies {
    implementation("com.h2database:h2")
    implementation("org.postgresql:postgresql:42.6.0")
}

tasks.bootJar {
    enabled = false
}
