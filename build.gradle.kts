import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.0.1"
  id("io.spring.dependency-management") version "1.1.0"
  kotlin("jvm") version "1.7.22"
  kotlin("plugin.spring") version "1.7.22"
  kotlin("plugin.jpa") version "1.7.22"
}

group = "com.waterfogsw"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}


dependencies {
  // spring web
  implementation("org.springframework.boot:spring-boot-starter-web")

  // redis
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.redisson:redisson-spring-boot-starter:3.19.3")

  // jpa
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  // kotlin
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  // mysql
  runtimeOnly("com.mysql:mysql-connector-j")

  // test
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
