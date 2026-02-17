package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddToCartRequestModel {

  private String id;
  private String cookie;
  @JsonProperty("prod_id")
  private int prodId;
  private boolean flag;
}
