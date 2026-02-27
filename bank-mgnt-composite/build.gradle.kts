plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("info.solidsoft.pitest") version "1.19.0-rc.1"
}

dependencies {
    // Módulos internos
    implementation(project(":common-lib"))
    
    // Spring Boot Starters
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
    
    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

pitest {
    targetClasses.set(listOf("com.nttdata.composite.*"))
    junit5PluginVersion.set("1.2.1")
    outputFormats.set(listOf("HTML"))
    timestampedReports.set(false)
}
