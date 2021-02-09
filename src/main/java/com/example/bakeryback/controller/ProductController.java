package com.example.bakeryback.controller;

import com.example.bakeryback.model.Product;
import com.example.bakeryback.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/product")
  public List<Product> getProducts() {
    return productService.getProducts();
  }

  @PostMapping("/reserve")
  public ResponseEntity<String> reserveOneItem(@RequestParam("productId") int id) {
    return productService.reserveOneItem(id);
  }

  @DeleteMapping("/reserve")
  public ResponseEntity<String> cancelReservation(@RequestParam("productId") int id,
      @RequestParam("amount") int amount) {
    return productService.cancelReservation(id, amount);
  }

  @PostMapping("/product/UpdateAvailableAmount")
  public ResponseEntity<String> updateAvailableAmount(@RequestParam("id") int id,
      @RequestParam("availableAmount") int availableAmount) {
    return productService.updateAvailableAmount(id, availableAmount);
  }
}
