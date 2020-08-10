## Introduction
* Built with [Java 8](https://java.com/en/download/) with [spring boot 2.3.2](https://spring.io/projects/spring-boot) framework
* Uses [Mongodb](https://www.mongodb.com/) as datastore
* Uses JWT based authentication. To keep implementation simple, auth handled from code as opposed to spring security which is the way for production grade apps
* Test cases only cover datastore operations for now
* The application is dockerized. Can run without additional set up if JAR is available
* Ports exposed. application - 8888. mongodb - 27019
* Passwords saved to datastore in plain text format


## Requirements:
* Docker, docker-compose
* Java 8, Mongodb and Maven
    * If no jar file in available to compile the code
    * To run the test cases
* mongodb without authentication

## Installation
#### With Docker. JAR file required on the local machine in target folder
```
# cd to project root
cd /to/the/project/root

# build images
docker-compose build

# run the build
docker-compose up -d

# stop the application
docker-compose down
```

#### Compile the code and run test cases
```
# cd to project root
cd /to/the/project/root

# change the mongo db credentials on path. currently configured for docker
vi src/main/resources/application.yaml

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
curl -X POST http://localhost:8888/mock/

# Login - admin
username : admin_user
password: password

# Login - user
username: user_user
password: password
```


## API Documentation for application -openapi (swagger)
Make sure the application is running before viewing this in browser.
[http://localhost:8888/swagger-ui.html](http://localhost:8888/swagger-ui.html)
