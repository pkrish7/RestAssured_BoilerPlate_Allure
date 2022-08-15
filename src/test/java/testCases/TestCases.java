package testCases;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TestCases {

    @Test(priority = 1)
    public void testGetRequest_HappyCase(){
        RestAssured.baseURI = "https://reqres.in/";
        String responseBody = given()
        .when().get("/api/users")
        .then().statusCode(200).log().body().extract().response().asString();
        assertFalse(responseBody.isEmpty());
    }

    @Test(priority = 2)
    public void testGetRequest_UnhappyCase(){
        RestAssured.baseURI = "https://reqres.in/";
        String responseBody =
                given()
                .when()
                        .get("/api/unknown/23")
                .then()
                        .statusCode(404)
                        .log().body().extract().response().asString();
        assertTrue(responseBody.contentEquals("{}"));
    }

    @Test(priority = 3)
    public void testPostRequest_HappyCase(){
        RestAssured.baseURI = "https://reqres.in/";
        HashMap requestBody = new HashMap();
        requestBody.put("name", "RestAssuredTest");
        requestBody.put("job", "Tester");
        Response response = given()
                 .contentType("application/json")
                 .body(requestBody)
         .when()
                 .post("/api/users")
         .then()
                 .statusCode(201)
                 .log().body().extract().response();
        //Validate the number of nodes returned in the response
        JsonPath resposeBodyNodes = response.jsonPath();
        System.out.println("TEST3:::::::::"+resposeBodyNodes.get("name"));
    }

    @Test(priority = 4)
    public void testPostRequest_UnhappyCase(){
        RestAssured.baseURI = "https://reqres.in/";
        HashMap requestBody = new HashMap();
        requestBody.put("email", "abc@test.com");
        String responseBody = given()
                .contentType("application/json")
                .body(requestBody)
        .when().post("/api/register")
                .then().statusCode(400)
                .log().body().extract().response().asString();
        assertTrue(responseBody.contains("{\"error\":\"Missing password\"}"));
    }

    @Test(priority = 5)
    public void testPutRequest_HappyCase(){
        RestAssured.baseURI = "https://reqres.in/";
        HashMap requestBody = new HashMap();
        requestBody.put("name", "RestAssuredTest");
        requestBody.put("job", "Automation Tester");
        String responseBody = given()
                .contentType("application/json")
                .body(requestBody)
                .when().put("/api/users/2")
                .then().statusCode(200)
                .log().body().extract().response().asString();
        //Validate the number of nodes returned in the response
    }

    @Test(priority = 6)
    public void testDeleteRequest_HappyCase(){
        RestAssured.baseURI = "https://reqres.in/";
        String responseBody = given()
                .when().delete("/api/users/2")
                .then().statusCode(204)
                .log().body().extract().response().asString();
        assertTrue(responseBody.isEmpty());
    }
}
