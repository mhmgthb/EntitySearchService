package api;

import com.takamol.qiwa.microservice.search.model.SearchRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
import net.minidev.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

//import  static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.http.ContentType;
import util.TestConfig;

import java.util.List;

@Slf4j
public class EntitySearchTest {

    @BeforeClass
    public static  void intialize(){
        RestAssured.baseURI = TestConfig.BASE_URI;
    }
    //@Test
    public void testSetRequest() {
        // Set the base URI, using JSONPlaceholder as an example

        // Send a GET request and save the response
        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();
        // Print the JSON content of the response
        System.out.println("Response JSON: " + response.asString()); // Verify that the status code is 200.
        // Validate that the status code is 200
        response.then().statusCode(200); // validate that the response has a status code of 200.
        // Validate a specific field value in the response
        response.then().body("userId", equalTo(1));
        response.then().body("id", equalTo(1));
        response.then().body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
        response.then().body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }
   // @Test
    public void test_json_request_is_valid() {

        // use org.json JSONObject to define your json

            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getSearchRequest())   // use jsonObj toString method
                    .when()
                    .post("/search/employee")
                    .then()
                    .assertThat().statusCode(200);

    }
    @Test
    public void test_when_entity_name_is_not_given() {

        // use org.json JSONObject to define your json

            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getSearchRequest())   // use jsonObj toString method
                    .when()
                    .post("/search/")
                    .then()
                    .assertThat().statusCode(400);


    }
    @Test
    public void test_when_request_is_not_valid_json() {

        // use org.json JSONObject to define your json
        try {
            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.convertToJsonString(TestConfig.getSearchRequest()).concat("--"))   // use jsonObj toString method
                    .when()
                    .post("/search/employee")
                    .then()
                    .assertThat().statusCode(400);
        } catch (Exception ex) {
            log.error("test_when_enity_name_is_not_given could not be executed ", ex);
        }
    }
        @Test
        public void test_when_request_does_not_conforms_to_schema() {

            // use org.json JSONObject to define your json

                given()
                        .contentType("application/json")  //another way to specify content type
                        .body(TestConfig.getInvalidSchemaRequest())   // use jsonObj toString method
                        .when()
                        .post("/search/employee")
                        .then()
                        .assertThat().statusCode(400);

        }
    @Test
    public void test_when_some_filter_has_empty_feiled() {

        // use org.json JSONObject to define your json

            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getRequestWithEmptyFilterField())   // use jsonObj toString method
                    .when()
                    .post("/search/employee")
                    .then()
                    .assertThat().statusCode(400);

    }
    @Test
    public void test_when_page_size_is_invalid() {

        // use org.json JSONObject to define your json

            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getRequestWithInvalidPageSize())   // use jsonObj toString method
                    .when()
                    .post("/search/employee")
                    .then()
                    .assertThat().statusCode(400);

    }
    @Test
    public void test_when_offset_size_is_invalid() {


            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getRequestWithInvalidOffsetSize())   // use jsonObj toString method
                    .when()
                    .post("/search/employee")
                    .then()
                    .assertThat().statusCode(400);

    }
    @Test
    public void test_when_order_field_is_null() {

        // use org.json JSONObject to define your json

            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getRequestWithOrderFieldNulle())   // use jsonObj toString method
                    .when()
                    .post("/search/employee")
                    .then()
                    .assertThat().statusCode(400);

    }
    @Test
    public void test_when_order_field_is_invalid() {


            given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getRequestWithOrderFieldInvalid())   // use jsonObj toString method
                    .when()
                    .post("/search/employee")
                    .then()
                    .assertThat().statusCode(400);

    }
    @Test
    public void test_response_list_size_as_per_filters() {


            List<JSONObject> employeesList = given()
                    .contentType("application/json")  //another way to specify content type
                    .body(TestConfig.getSearchRequest())   // use jsonObj toString method
                    .when()
                    .post("/search/employee").as(new TypeRef<List<JSONObject>>() {});
                   // .then()
                   // .assertThat().statusCode(201);
            assertThat(employeesList.size(), equalTo(10));

    }
    @Test
    public void test_response_list_size_as_per_page_size() {
        SearchRequest searchRequest = TestConfig.getSearchRequest();
        List<JSONObject> employeesList = given()
                .contentType("application/json")  //another way to specify content type
                .body(searchRequest)   // use jsonObj toString method
                .when()
                .post("/search/employee").as(new TypeRef<List<JSONObject>>() {});
        // .then()
        // .assertThat().statusCode(201);
        assertThat(employeesList.size(), equalTo(searchRequest.getSize()));

    }
}
