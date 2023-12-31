# Spring Security tutorial

## Build

```shell
./mvnw clean verify
```

## Run

```shell
java -jar target/spring-sec-0.0.1-SNAPSHOT.jar
```

## Using API

### Get students

```shell
curl -s -u "user:password" http://localhost:8080/api/v1/students | jq
```

### Get student '1'

```shell
curl -s -u "user:password" http://localhost:8080/api/v1/students/1 | jq
```

### Add student

```shell
curl -s -u "user:password" -X PUT -H "Content-Type: application/json" -d '{"name": "Abc"}'  http://localhost:8080/api/v1/students
```

### Delete students

```shell
curl -s -u "user:password" -X DELETE http://localhost:8080/api/v1/students
```

## References

1. https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html
