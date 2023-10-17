dependencies {
    implementation("org.seleniumhq.selenium:selenium-java:4.13.0")
    implementation(project(":logger"))
    implementation(project(":model"))
    implementation(project(":webdriver"))
    implementation(project(":collection"))
    implementation(project(":source"))
    implementation(project(":logback"))
}

tasks.bootJar {
    enabled = false
}

