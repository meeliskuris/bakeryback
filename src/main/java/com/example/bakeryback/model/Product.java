package com.example.bakeryback.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Product {
  private int id;
  private String name;
  private BigDecimal price;
  private byte[] image;
  private int availableAmount;
  private boolean amountChangeable;
}
