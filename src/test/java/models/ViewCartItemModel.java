package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ViewCartItemModel {
  private String cookie;
  private String id;
  @JsonProperty("prod_id")
  private int prodId;
}
