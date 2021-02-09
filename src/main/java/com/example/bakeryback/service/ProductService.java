package com.example.bakeryback.service;

import com.example.bakeryback.model.Product;
import com.example.bakeryback.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  static final String DOES_NOT_EXIST = "Product does not exist, productId: ";
  static final String OUT_OF_STOCK = "Product out of stock, productId: ";
  static final String CANNOT_CHANGE_AMOUNT = "Cannot change available amount of product, it's an edible item, productId: ";

  public List<Product> getProducts() {
    return productRepository.getAllProducts();
  }

  public ResponseEntity<String> updateAvailableAmount(int id, int amount) {
    if (!productRepository.productExists(id)) {
      return new ResponseEntity<>(DOES_NOT_EXIST + id, HttpStatus.NOT_FOUND);
    }
    if (productRepository.isAmountChangeable(id)) {
      return new ResponseEntity<>(productRepository.setAvailableAmount(id, amount), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(CANNOT_CHANGE_AMOUNT + id, HttpStatus.FORBIDDEN);
    }
  }

  public ResponseEntity<String> reserveOneItem(int id) {
    if (!productRepository.productExists(id)) {
      return new ResponseEntity<>(DOES_NOT_EXIST + id, HttpStatus.NOT_FOUND);
    }
    if (productRepository.amountBiggerThanZero(id)) {
      return productRepository.reduceAmountByOne(id);
    } else {
      return new ResponseEntity<>(OUT_OF_STOCK + id, HttpStatus.NOT_FOUND);
    }
  }

  public ResponseEntity<String> cancelReservation(int id, int amount) {
    if (!productRepository.productExists(id)) {
      return new ResponseEntity<>(DOES_NOT_EXIST + id, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(productRepository.increaseAmount(id, amount), HttpStatus.OK);
  }
}
