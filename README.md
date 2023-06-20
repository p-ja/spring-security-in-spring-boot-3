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
curl -s  http://localhost:8080/api/v1/students | jq
```

### Get student '1'

```shell
curl -s  http://localhost:8080/api/v1/students/1 | jq
```

### Add student

```shell
curl -s -X PUT -H "Content-Type: application/json" -d '{"name": "Abc"}'  http://localhost:8080/api/v1/students
```

### Delete students

```shell
curl -s -X DELETE http://localhost:8080/api/v1/students
```
