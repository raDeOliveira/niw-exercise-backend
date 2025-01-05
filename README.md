# Spring Boot

### About the application
Backend API

### Technologies
* Java 17
* Spring Boot 3.4.1

### Dependencies
* Spring Data JPA
* Hibernate
* Spring Web
* Spring Validation
* PostgreSQL
* Junit 5
* Mockito
* Lombok
* Slf4j
* Mapstruct

## Run Application
### Docker PostgreSQL
Run docker-compose.yml inside project folder
```
docker compose up
```

### Run Application
To compile and run the application in development mode
```
./mvnw spring-boot:run
```
### Access the application
- http://localhost:8080/api/

### Endpoints
- http://localhost:8080/api/payments/calculate
- http://localhost:8080/api/users

### Spring tests
https://spring.io/guides/gs/testing-web/

