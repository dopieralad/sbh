plugins {
    val kotlinVersion = "1.3.50"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    val springShellVersion: String by project
    val jUnitVersion: String by project

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.shell:spring-shell-starter:$springShellVersion")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:$jUnitVersion")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.wrapper {
    gradleVersion = "5.6.2"
    distributionType = Wrapper.DistributionType.ALL
}
