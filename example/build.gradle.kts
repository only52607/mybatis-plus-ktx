
plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

dependencies {
    implementation(project(":wrapper-dsl"))
    implementation("mysql:mysql-connector-java")
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.baomidou:mybatis-plus-boot-starter:3.4.2")
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test-junit"))
}