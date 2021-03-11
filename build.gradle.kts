import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.21")
    implementation("org.springframework.boot:spring-boot-dependencies:2.4.1")
}

allprojects {
    repositories {
        maven("http://maven.aliyun.com/nexus/content/groups/public/")
        maven("http://maven.aliyun.com/nexus/content/groups/spring/")
        maven("http://maven.aliyun.com/nexus/content/repositories/jcenter")
        maven("http://maven.aliyun.com/nexus/content/repositories/google")
        maven("http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    group = "com.ooooonly"
    version = "1.0"
}

tasks.test {
    useJUnit()
}