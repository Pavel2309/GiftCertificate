### The multi-modal project represents a web service for Gift Certificates system.

### The project is going to be separated into several sections where each section adds a specific functionality to the project.

### Stack
Java EE, Spring Framework, Spring IoC, Spring Boot, Spring Security, Gradle, Apache Tomcat, MySQL, JUnit5, Mockito

## Section 1: REST API Basics (Done)

### Requirements
The system should expose REST APIs to perform the following operations:
1. CRUD operations for gift certificates.
2. CRD operations for Tags.
3. The ability to get certificates based on different parameters, including:
   1. by the tag name
   2. search by the certificate's title or description
   3. sort by the update date or by the title (can be applied both at the same time)

------

## Section 2: REST API Advanced (Done)

### Requirements

#### Part 1:
The application from the Section 1 should be migrated to the Spring Boot application.

#### Part 2:
The system should be extended and expose the following REST APis:
1. Change a single field of a certificate.
2. Add an entity of a User (implement only get operations).
3. Make an order (a user can buy a certificate).
4. Get information about users' orders.
5. Get information about user's orders (cost and timestamp of purchase).
6. Get the most widely used tag of a user with the highest cost of all orders.
7. Search certificates by multiple tags ("and" condition).
8. Implement pagination for all GET endpoints.
9. Support HATEOAS on REST endpoints.

#### Part 3:
1. Hibernate should be used as a JPA implementation for data access.
2. Spring Transaction should be used in all necessary areas of the application.
3. Audit data should be populated using JPA features.


------
## Section 3: Authentication & Spring Security (Done)

### Requirements

1. Spring Security should be used as a security framework.
2. Application should support only stateless user authentication and verify integrity of JWT token.
3. Users should be stored in a database with some basic information and a password.

------
User Permissions:
- Guest:
   * Read operations for main entity.
   * Signup.
   * Login.
- User:
   * Make an order on main entity.
   * All read operations.
- Administrator (can be added only via database call):
   * All operations, including addition and modification of entities.

------