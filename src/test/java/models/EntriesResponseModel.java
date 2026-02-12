package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EntriesResponseModel {

  @JsonProperty("Items")
  private EntryResponseModel[] items;

  @JsonProperty("LastEvaluatedKey")
  private LastEvaluatedKeyModel lastEvaluatedKey;

}
