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

### Installing

## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config

### Running App
Post installation of Java, Maven and Spring Tools 4, and BDD you will have to run app
 with your CLI or with dashboard of Spring Tools or  run in you CLI: mvn spring-boot:run ..

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

