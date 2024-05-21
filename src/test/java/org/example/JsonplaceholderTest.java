package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class JsonplaceholderTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Ativa o logging apenas em caso de falha
        RestAssured.filters(new io.restassured.filter.log.RequestLoggingFilter(LogDetail.ALL), // Log das requisições
                new io.restassured.filter.log.ResponseLoggingFilter(LogDetail.ALL)); // Log das respostas
    }

    @Test
    public void testBuscandoPeloName() {
        given()
                .queryParam("name", "alias odio sit")
                .when()
                .get("/comments")
                .then()
                .statusCode(200)
                .body("[0].email", equalTo("Lew@alysha.tv"));
    }

    @Test
    public void testCriarUsuario() {
        String requestBody = "{\n" +
                "  \"name\": \"Tiago Amaro\",\n" +
                "  \"username\": \"tiago1a\",\n" +
                "  \"email\": \"tiagoamaro012gmail.com\"\n" +
                "}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void testAtualizarUsuario() {
        String requestBody = "{\n" +
                "  \"email\": \"novo.email@teste.com\",\n" +
                "  \"address\": {\n" +
                "    \"geo\": {\n" +
                "      \"lat\": \"48.8566\",\n" +
                "      \"lng\": \"2.3522\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/users/5")
                .then()
                .statusCode(200)
                .body("email", equalTo("novo.email@teste.com"))
                .body("address.geo.lat", equalTo("48.8566"))
                .body("address.geo.lng", equalTo("2.3522"));
    }
}
