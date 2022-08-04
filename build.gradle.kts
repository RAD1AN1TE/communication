import org.jetbrains.kotlin.gradle.tasks.KotlinCompile




plugins {
    kotlin("jvm") version "1.7.10"
    id ("com.google.devtools.ksp") version "1.6.10-1.0.2"
    id("org.liquibase.gradle") version "2.0.3"
    id("jacoco")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {



    implementation("io.ktor:ktor-server-core:1.6.8")
    implementation("io.ktor:ktor-server-netty:1.6.8")
    implementation ("ch.qos.logback:logback-classic:1.2.11")

    implementation("io.ktor:ktor-client-core:1.6.8")
    implementation("io.ktor:ktor-client-core:1.6.8")
    implementation("io.ktor:ktor-client-serialization:1.6.8")
    implementation("io.ktor:ktor-client-logging:1.6.8")


    implementation ("io.ktor:ktor-jackson:1.6.8")

    implementation ("io.insert-koin:koin-core:3.0.1")
    implementation ("io.insert-koin:koin-ktor:3.0.1")

    implementation("org.jetbrains.exposed:exposed-core:0.38.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.2")

    implementation("org.postgresql:postgresql:42.3.6")


    implementation("org.liquibase:liquibase-core:4.4.3")

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("io.github.microutils:kotlin-logging:1.6.26")



    implementation("com.zaxxer:HikariCP:2.7.8")
    implementation("junit:junit:4.13.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.8")


    testImplementation(kotlin("test"))
    testImplementation("com.h2database:h2:1.3.148")

    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
}

jacoco {
    toolVersion = "0.8.7"
    reportsDirectory.set(layout.buildDirectory.dir("jacoco-dir"))
}
tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) //Generate reports after tests
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}