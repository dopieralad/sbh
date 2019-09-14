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

sourceSets {
    create("report") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

configurations {
    this["reportImplementation"].extendsFrom(configurations.testImplementation.get())
    this["reportRuntime"].extendsFrom(configurations.testRuntime.get())
}

dependencies {
    val kotlinCoroutinesVersion: String by project
    val jUnitVersion: String by project
    val springShellVersion: String by project

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
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
    jvmArgs = listOf("-Xmx10g", "-XX:MaxMetaspaceSize=1g", "-XX:+HeapDumpOnOutOfMemoryError", "-Dfile.encoding=UTF-8")
}

val report = task<Test>("report") {
    description = "Generates reports"
    group = "verification"

    testClassesDirs = sourceSets["report"].output.classesDirs
    classpath = sourceSets["report"].runtimeClasspath

    shouldRunAfter("test")
    outputs.upToDateWhen { false }

    useJUnitPlatform()
    jvmArgs = listOf("-Xmx10g", "-XX:MaxMetaspaceSize=1g", "-XX:+HeapDumpOnOutOfMemoryError", "-Dfile.encoding=UTF-8")
}

tasks.check { dependsOn(report) }

tasks.wrapper {
    gradleVersion = "5.6.2"
    distributionType = Wrapper.DistributionType.ALL
}
