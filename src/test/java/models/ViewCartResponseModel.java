package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ViewCartResponseModel {
  @JsonProperty("Items")
 private ViewCartItemModel[] items;
}
