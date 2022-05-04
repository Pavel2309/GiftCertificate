## A web service for the Gift Certificates system.

--------------------------------------------------

### The project is separated into different parts where each part adds functionality.

---------------------------------------------

## Part 1 (done)

#### Stack
Java EE, Spring Framework, Spring IoC, Gradle, Apache Tomcat, MySQL, JUnit5, Mockito

#### Business requirements
The system should expose REST APIs to perform the following operations:
1. CRUD operations for Gift Certificates.
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
3. Add "make an order" functionality (a user can buy a certificate).
4. Get information about user's orders.
5. Get information about a single user's order (cost and timestamp of purchase).
6. Get the most frequently used tag (tags) of a user (users) who spent them most money in the system.
7. Search certificates by multiple tags ("and" condition).
8. Implement pagination for all GET endpoints.
9. Support HATEOAS on REST endpoints.