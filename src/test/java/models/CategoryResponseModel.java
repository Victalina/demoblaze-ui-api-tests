package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryResponseModel {

  @JsonProperty("Items")
  private EntryResponseModel[] items;

}
