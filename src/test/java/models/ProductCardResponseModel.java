package models;

import lombok.Data;

@Data
public class ProductCardResponseModel {
  private String cat;
  private String desc;
  private int id;
  private String img;
  private double price;
  private String title;
}
