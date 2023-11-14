dependencies {
    implementation(project(":redis:configuration"))
    implementation(project(":model"))
    implementation(project(":aop:logger"))
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.3")
}