import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

}

plugins {
    kotlin("jvm") version "1.4.21"
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
}

group = "com.ooooonly"
version = "1.0"

repositories {
    maven("http://maven.aliyun.com/nexus/content/groups/public/")
    maven("http://maven.aliyun.com/nexus/content/groups/spring/")
    maven("http://maven.aliyun.com/nexus/content/repositories/jcenter")
    maven("http://maven.aliyun.com/nexus/content/repositories/google")
    maven("http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("com.baomidou:mybatis-plus-boot-starter:3.4.2")
    testImplementation(kotlin("test-junit"))
    testImplementation("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}