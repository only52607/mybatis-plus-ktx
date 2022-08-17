import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/spring-plugin")
        maven("https://jitpack.io")
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.6.21")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.6.21")
    }
}

plugins {
    kotlin("jvm") version "1.6.21"
}

allprojects {
    group = "com.github.only52607.mybatis.plus.ktx"
    version = "1.0"
    repositories {
        maven ("https://maven.aliyun.com/repository/public")
        maven ("https://maven.aliyun.com/repository/google")
        maven ("https://jitpack.io")
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")
    apply(plugin = "kotlin-spring")
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.21")
        implementation(kotlin("reflect"))
    }

    tasks.test {
        useJUnitPlatform()
    }
}