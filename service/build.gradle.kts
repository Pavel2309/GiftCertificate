plugins {
    `java-library`
    id("GiftCertificate.java-common-conventions")
    id("org.sonarqube") version "3.3"
}

dependencies {
    api(project(":persistence"))
}