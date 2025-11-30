package com.example.demo.restAssured;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.Matchers.*;
import static sun.security.util.KnownOIDs.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void shouldCreatePersonSuccessfully() {
        String jsonBody = """
        {
            "name": "Gabriel",
            "email": "gabriel@gmail.com",
            "cpf": "11111111111",
            "balance": 1000.0
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/people")
                .then()
                .statusCode(201)
                .body("name", equalTo("Gabriel"))
                .body("email", equalTo("gabriel@gmail.com"))
                .body("cpf", equalTo("11111111111"));
    }
}
