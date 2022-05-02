### The multi-modal project represents a web service for Gift Certificates system.

### The project is going to be separated into several parts where each part will add functionality.

## Part 1 (done)

#### Stack
Java EE, Spring Framework, Spring IoC, Gradle, Apache Tomcat, MySQL, JUnit5, Mockito

#### Business requirements
The system should expose REST APIs to perform the following operations:
1. CRUD operations for gift certificates.
2. CRD operations for Tags.
3. The ability to get certificates based on different parameters, including:
   1. by the tag name
   2. search by the certificate's title or description
   3. sort by the update date or by the title (can be applied both at the same time)

------

## Part 2

The application from the Part 1 is migrated to the Spring Boot application.

#### Business requirements
The system should be extended and expose the following REST APis:
1. Change a single field of a certificate.
2. Add an entity of a User (implement only get operations).
3. Make an order (a user can buy a certificate).
4. Get information about orders of a user.
5. Get information about a single order of a user (cost and timestamp of purchase).
6. Get the most widely used tag of a user with the highest cost of all orders.
7. Search certificates by multiple tags ("and" condition).
8. Implement pagination for all GET endpoints.
9. Support HATEOAS on REST endpoints.