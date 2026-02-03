plugins {
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.spring") version "2.3.0"
    id("groovy")
    id("com.github.ben-manes.versions") version "0.53.0"
}

group = "no.novari"

val apiVersion: String by project

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.named<Jar>("jar") {
    enabled = false
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.fintlabs.no/releases")
    }
    mavenLocal()
}

dependencyManagement {
    dependencies {
        // Downgrade MongoDB driver for compatibility with MongoDB 3.6: https://www.mongodb.com/docs/drivers/java/sync/current/compatibility/
        dependency("org.mongodb:mongodb-driver-core:5.1.4")
        dependency("org.mongodb:mongodb-driver-sync:5.1.4")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("com.google.guava:guava:33.5.0-jre")
    implementation("org.apache.commons:commons-lang3:3.20.0")
    implementation("commons-beanutils:commons-beanutils:1.11.0")
    implementation("org.apache.jena:jena-arq:5.6.0")
    implementation("org.jooq:jool-java-8:0.9.15")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("no.fint:fint-model-resource:0.4.1")
    implementation("no.fint:fint-event-model:3.3.1")
    implementation("no.fint:fint-relation-model:1.1.6")
    implementation("no.fintlabs:fint-spring-secrets:1.0.0-rc-4")
    implementation("no.fintlabs:fint-sse:3.1.0-rc-2")

    implementation("no.novari:fint-administrasjon-resource-model-java:$apiVersion")
    implementation("no.novari:fint-utdanning-resource-model-java:$apiVersion")
    implementation("no.novari:fint-okonomi-model-java:$apiVersion")
    implementation("no.novari:fint-okonomi-resource-model-java:$apiVersion")
    implementation("no.novari:fint-arkiv-model-java:$apiVersion")
    implementation("no.novari:fint-arkiv-resource-model-java:$apiVersion")

    implementation("org.springframework.hateoas:spring-hateoas")
    implementation("org.springframework.plugin:spring-plugin-core")
    implementation("org.springframework.boot:spring-boot-actuator")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("cglib:cglib-nodep:3.3.0")
    testImplementation("org.spockframework:spock-spring:2.4-groovy-5.0")
    testImplementation("org.spockframework:spock-core:2.4-groovy-5.0")
    testImplementation("org.apache.groovy:groovy:5.0.4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    systemProperties = System.getProperties().entries.associate { it.key.toString() to it.value }
}

tasks.test {
    testLogging {
        events("passed", "skipped", "failed")
    }
    useJUnitPlatform()
}
