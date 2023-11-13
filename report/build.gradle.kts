dependencies {
    implementation("org.aspectj:aspectjweaver:1.9.20.1")
    implementation(project(":model"))
    implementation(project(":execution"))
    implementation(project(":redis:repository"))
    implementation(project(":redis:queue"))
}