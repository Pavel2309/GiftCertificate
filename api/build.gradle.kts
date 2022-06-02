plugins {
    war
    id("GiftCertificate.java-common-conventions")
    id("org.sonarqube") version "3.3"
    id ("jacoco")
}

dependencies {

    implementation(project(":service"))

    providedCompile("javax.servlet:javax.servlet-api:4.0.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    implementation ("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")
    testImplementation("org.mockito:mockito-junit-jupiter:4.5.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
}

repositories {
    mavenCentral()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}
tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.named("sonarqube").configure {
    dependsOn(tasks.test)
}