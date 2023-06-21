package pl.sii.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityTest {

    @Value("${auth.username}")
    String username;

    @Value("${auth.password}")
    String password;

    @LocalServerPort
    int localPort;

    @BeforeEach
    void init() {
        RestAssured.port = localPort;
    }

    @Nested
    class HappyPaths {

        @ParameterizedTest
        @DisplayName("Should receive HTTP 200 OK when accessing index without authentication")
        @ValueSource(strings = {"/", "/index.html"})
        void shouldAccessIndexWithoutAuthentication(String url) {

            given()
                    .when()
                    .get(url)

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.OK.value());
        }

        @ParameterizedTest
        @DisplayName("Should receive HTTP 200 OK when authorized")
        @ValueSource(strings = {"/api/v1/students", "/api/v1/students/1"})
        void shouldReceive200WhenAuthenticated(String url) {

            given()
                    .auth()
                    .basic(username, password)

                    .when()
                    .get(url)

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        @DisplayName("Should successfully PUT when authorized")
        void shouldAddStudentWhenAuthenticated() {

            given()
                    .contentType(ContentType.JSON)
                    .body("""
                            {"name": "Alice"}
                            """)
                    .auth()
                    .basic(username, password)

                    .when()
                    .put("/api/v1/students")

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        @DisplayName("Should successfully DELETE when authorized")
        void shouldDeleteStudentsWhenAuthenticated() {

            given()
                    .auth()
                    .basic(username, password)

                    .when()
                    .delete("/api/v1/students")

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Nested
    class Rejections {

        @ParameterizedTest
        @DisplayName("Should receive HTTP 401 Unauthorized when not authorized")
        @ValueSource(strings = {"/api/v1/students", "/api/v1/students/1"})
        void shouldReceive401WhenNotAuthenticated(String url) {

            given()

                    .when()
                    .get(url)

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        @DisplayName("Should NOT PUT when unauthorized")
        void shouldNotAddStudentWhenUnauthenticated() {

            given()
                    .contentType(ContentType.JSON)
                    .body("""
                            {"name": "Alice"}
                            """)

                    .when()
                    .put("/api/v1/students")

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        void shouldReceive401WhenTryingToDeleteUnauthenticated() {
            given()

                    .when()
                    .delete("/api/v1/students")

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
