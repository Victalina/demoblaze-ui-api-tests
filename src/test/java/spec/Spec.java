package spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;

public class Spec {
  public static RequestSpecification requestSpec = with()
          .contentType("application/json")
          .log().all();


  public static ResponseSpecification responseSpecStatusCode(int statusCode) {
    ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(statusCode)
            .log(ALL)
            .build();
    return responseSpec;
  }
}

