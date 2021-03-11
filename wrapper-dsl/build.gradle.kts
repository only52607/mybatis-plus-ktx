import java.util.Properties
import java.util.Date

plugins {
    kotlin("jvm")
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

task("wrapper")

//task("wrapper",Wrapper::class){
//    gradleVersion = "6.6.1"
//}

dependencies {
    api("com.baomidou:mybatis-plus-boot-starter:3.4.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.21")
}

bintray {
    try{
        val properties = Properties()
        val inputStream = project.rootProject.file("local.properties").inputStream()
        properties.load(inputStream)
        user = properties.getProperty("BINTRAY_USER") //System.getenv('BINTRAY_USER')
        key = properties.getProperty("BINTRAY_KEY") //System.getenv('BINTRAY_KEY')
    }catch(e: Exception){
        e.printStackTrace()
    }
    override = true
    publish = true
    setPublications("MPKTXPublication")
    pkg.apply {
        repo = "maven"
        name = project.name
        userOrg = user
        setLicenses("AGPL-V3")
        vcsUrl = "github.com/only52607/mybatis-plus-ktx"
        issueTrackerUrl = "github.com/only52607/mybatis-plus-ktx/issues"
        version.apply {
            name = "mybatis-plus-ktx"
            desc = "Provide mybatis-plus with kotlin dsl support"
            released = Date().toString()
            vcsTag = version.toString()
        }
    }
}

publishing.publications.create("MPKTXPublication",MavenPublication::class.java){
    artifact(tasks.jar)
    artifact(tasks.kotlinSourcesJar)
    artifactId = "mybatis-plus-ktx"
}
