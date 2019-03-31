plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.21"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.21"

    id("org.springframework.boot") version "2.1.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
}

group = "pl.dopierala.bio"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.shell:spring-shell-starter:2.0.0.RELEASE")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.wrapper {
    gradleVersion = "5.3.1"
    distributionType = Wrapper.DistributionType.ALL
}
