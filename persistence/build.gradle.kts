plugins {
    id("GiftCertificate.java-common-conventions")
    id("org.sonarqube") version "3.3"
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.6.7")
//    implementation("org.hibernate:hibernate-core:5.6.8.Final")
//    implementation("org.springframework:spring-orm:5.3.19")

    implementation("mysql:mysql-connector-java:8.0.29")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.h2database:h2:2.1.210")
    implementation("org.apache.commons:commons-lang3:3.12.0")

}

repositories {
    mavenCentral()
}
