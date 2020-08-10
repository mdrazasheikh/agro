## Introduction
* Built with [Java 8](https://java.com/en/download/) with [sprint boot 2.3.2](https://spring.io/projects/spring-boot) framework
* Uses [Mongodb](https://www.mongodb.com/) as datastore
* Uses JWT based authentication. To keep implementation simple, auth is handled from code as opposed to spring security which is the way for production grade apps
* Test cases only cover datastore operations for now
* The application is dockerized. Can run without additional set up if JAR is available
* ports exposed. application - 8888. mongodb - 27019


## Requirements:
* Docker, docker-compose
* Java 8, Mongodb and Maven
    * If no jar file in available to compile the code
    * To run the test cases
* mongodb without authentication

## Installation
#### With Docker. JAR file is required on the local machine in target folder
```
# cd to project root
cd /to/the/project/root
# build images
docker-compose build
# run the build
docker-compose up -d
```

#### Compile the code and run test cases
```
# cd to project root
cd /to/the/project/root
# bundle the JAR. Run test case as well 
mvn clean install
# bundle the JAR. skip test case while bundling JAR
mvn clean install -DskipTests
# run test cases only
mvn test
# run the application. 
java -Dspring.server.port=8888 -jar ./target/agro.jar
```


#### Set up mock data
```
# Run as post
curl -X POST http://{host}:{port}/mock/
```

## API Documentation for application -openapi (swagger)
[http://localhost:8888/swagger-ui.html](http://localhost:8888/swagger-ui.html)
