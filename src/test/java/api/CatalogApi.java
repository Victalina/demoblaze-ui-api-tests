package api;

import io.qameta.allure.Step;
import models.*;

import static io.restassured.RestAssured.given;
import static spec.Spec.requestSpec;
import static spec.Spec.responseSpecStatusCode;

public class CatalogApi{

  @Step("Send request to get product catalog")
  public EntriesResponseModel getCatalogEntries() {

    return
            given(requestSpec)
                    .when()
                    .get("/entries")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(EntriesResponseModel.class);
  }

  @Step("Send request to get product from category")
  public CategoryResponseModel getEntriesByCategory(String category){

    CategoryRequestModel cat = new CategoryRequestModel(category);
    return
            given(requestSpec)
                    .body(cat)
                    .when()
                    .post("/bycat")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(CategoryResponseModel.class);

  }

  @Step("Send request to get existing product card by id")
  public ProductCardResponseModel getExistingProductCardById(String id){

    ProductCardRequestModel entryId = new ProductCardRequestModel(id);
    return
            given(requestSpec)
                    .body(entryId)
                    .when()
                    .post("/view")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ProductCardResponseModel.class);
  }

  @Step("Send request to get non-existent product card by id")
  public ErrorMessageModel getNonExistentProductCardById(String id){

    ProductCardRequestModel entryId = new ProductCardRequestModel(id);
    return
            given(requestSpec)
                    .body(entryId)
                    .when()
                    .post("/view")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(ErrorMessageModel.class);
  }

  @Step("Get catalog entities after id")
  public PaginationResponseModel getPaginatingCatalogEntriesAfterId(String id){
    PaginationRequestModel afterId = new PaginationRequestModel(id);
    return
            given(requestSpec)
                    .when()
                    .body(afterId)
                    .post("/pagination")
                    .then()
                    .spec(responseSpecStatusCode(200))
                    .extract().as(PaginationResponseModel.class);
  }
}
