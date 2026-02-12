package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaginationResponseModel {

  @JsonProperty("Items")
  private EntryResponseModel[] items;

  @JsonProperty("LastEvaluatedKey")
  private LastEvaluatedKeyModel lastEvaluatedKey;

  @JsonProperty("ScannedCount")
  private int scannedCount;

}
