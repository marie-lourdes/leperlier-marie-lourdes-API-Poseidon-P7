## Webapp Poseidon Conseil
A Webapp with REST API secured with Spring Security .

### Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them
- Java 17
- Maven 3.9.4
- SpringBoot 6.1.3
- Spring Tools 4
- Spring Security
- Thymeleaf
- Bootstrap v.4.3.1
- Create database with name "demo" as configuration in application.properties

### Running App

For building  PROD :

- BUILD PROD : mvn clean package -Dmaven.test.skip=true

Post installation, building project, and creating  BDD from src/main/resources/data.sql, you will have to run app
 with your CLI: 'mvn spring-boot:run' or with dashboard of Spring Tools.
 
Finally, you will be ready to  use webapp and request 
The link homepage  is :http://localhost:8080/

### Testing with ENV dev

- For testing, use Maven CLI  and run: 'mvn clean package -Denvironnement=dev'

### MORE INFORMATIONS
-  For infomation global of project (dependencies, version ...) :
See folder /doc/site

-  For infomation about classes or methods used in application Spring Boot and Spring Security:
See folder /doc/Javadoc  in root project

