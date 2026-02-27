plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("info.solidsoft.pitest") version "1.19.0-rc.1"
}

dependencies {
    // Módulos internos
    implementation(project(":common-lib"))
    implementation(project(":shared-repositories"))
    
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
    
    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
    
    // Database
    runtimeOnly("org.postgresql:postgresql:42.7.1")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:testcontainers:1.19.3")
    testImplementation("org.testcontainers:postgresql:1.19.3")
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

pitest {
    targetClasses.set(listOf("com.nttdata.account.*"))
    junit5PluginVersion.set("1.2.1")
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
}
