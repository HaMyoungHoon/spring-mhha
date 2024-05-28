import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    war
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24"
}

group = "mhha"
version = "1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springdoc:springdoc-openapi-ui:1.8.0")

    implementation("dev.akkinoc.util:yaml-resource-bundle:2.12.3")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.1")

    implementation("com.google.code.gson:gson:2.11.0")

    implementation("org.json:json:20240303")

    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}
