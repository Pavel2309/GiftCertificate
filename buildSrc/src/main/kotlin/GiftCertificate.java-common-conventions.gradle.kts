plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("org.springframework.boot:spring-boot-starter-web:2.6.7")
    implementation("org.springframework.boot:spring-boot-starter-hateoas:2.6.7")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0")
    testImplementation("org.springframework.security:spring-security-test:5.7.1")

    testImplementation("junit:junit-dep:4.11")
    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation("org.hamcrest:hamcrest-library:2.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")


    implementation ("javax.validation:validation-api:2.0.1.Final")
    implementation ("org.hibernate:hibernate-validator:6.0.2.Final")

    implementation("org.springframework.boot:spring-boot-starter-security:2.6.7")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:2.6.7")


    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks.test {
    useJUnitPlatform()
}
