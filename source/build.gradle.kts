dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation(project(":collection"))
    implementation(project(":model"))
}
tasks.bootJar {
    enabled = false
}