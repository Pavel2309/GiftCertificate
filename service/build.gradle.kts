plugins {
    `java-library`
    id("GiftCertificate.java-common-conventions")
    id("org.sonarqube") version "3.3"
    id ("jacoco")
}

dependencies {
    api(project(":persistence"))
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