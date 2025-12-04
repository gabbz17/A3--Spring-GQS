package com.example.demo.restAssured;

import com.example.demo.entity.People;
import com.example.demo.web.dto.RequestNameUpdate;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PeopleServiceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/people";
    }

    People people = new People(24L, "Carlos", "carlos@gmail.com", "11111111111", BigDecimal.valueOf(1000));

    @Test
    void createPeopleTest() {

        given()
                .contentType(ContentType.JSON)
                .body(people)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo("Carlos Drummond"));
    }

    @Test
    void findByIdTest() {

        given()
                .pathParam("id", people.getNumberAccount())
                .when()
                .get("/id/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Joseph"));

    }

    @Test
    void findAllTest() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].name", notNullValue());
    }

    @Test
    void updateNameTest() {

        RequestNameUpdate updateDto = new RequestNameUpdate("Joseph");

        given()
                .contentType(ContentType.JSON)
                .body(updateDto)
                .pathParam("id", people.getNumberAccount())
                .when()
                .patch("/id/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Joseph"));
    }

    @Test
    void deleteByIdTest() {

        given()
                .pathParam("id", people.getNumberAccount())
                .when()
                .delete("/delete/{id}")
                .then()
                .statusCode(204); // No Content
    }

}