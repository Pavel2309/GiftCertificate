plugins {
    id("GiftCertificate.java-common-conventions")
}

dependencies {
    implementation("org.springframework:spring-jdbc:5.3.17")
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.h2database:h2:2.1.210")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}

repositories {
    mavenCentral()
}
