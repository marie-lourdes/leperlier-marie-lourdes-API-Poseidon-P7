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
-Thymeleaf
- Bootstrap v.4.3.1
- Create database with name "demo" as configuration in application.properties

### Running App

Post installation of Java, Maven and Spring Tools 4, and BDD you will have to run app
 with your CLI or with dashboard of Spring Tools or  run in you CLI: mvn spring-boot:run ..

For build  PROD  or DEV you can run:
->BUILD DEV : mvn clean package -Denvironnement=dev
->BUILD PROD : mvn clean package -Dmaven.test.skip=true

Post installation and building project, and create  BDD from src/main/resources/Data.sql, you will have to run app
 with your CLI: 'mvn spring-boot:run' or with dashboard of Spring Tools.
 Then move the JAR  generated at root of project , Spring will  run with properties in folder /config for ENV "prod" and finally  run in you CLI: 
 for app PROD 


Finally, you will be ready to  use webapp and request 
The link homepage  is :http://localhost:8080/

### MORE INFORMATIONS

 For testing application use:
`run as` , then, 'Maven test' for unit test and maven verify for integration test

 For testing request:
See the file of logging in folder log of project or use Postman after running application

see folder /doc 

### Debugging with ENV dev
Use  Maven CLI 'mvn clean package -Denvironnement=dev' and run again 
 For debbuging
Use  Maven CLI 'mvn test or mvn verify  

