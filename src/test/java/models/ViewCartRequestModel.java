package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewCartRequestModel {
  private String cookie;
  private boolean flag;
}
