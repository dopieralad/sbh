plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.21"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.21"

    id("org.springframework.boot") version "2.1.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
}

val projectGroup: String by project
val projectVersion: String by project
group = projectGroup
version = projectVersion

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

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
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
    gradleVersion = "5.6.2"
    distributionType = Wrapper.DistributionType.ALL
}
