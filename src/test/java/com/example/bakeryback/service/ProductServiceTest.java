package com.example.bakeryback.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bakeryback.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @InjectMocks
  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  @Test
  public void reserveOneItemShouldReturnHttpOk() {
    ResponseEntity expectedResponse = new ResponseEntity<>("Executed", HttpStatus.OK);
    when(productRepository.reduceAmountByOne(1)).thenReturn(expectedResponse);
    when(productRepository.productExists(1)).thenReturn(true);
    when(productRepository.amountBiggerThanZero(1)).thenReturn(true);
    ResponseEntity response = productService.reserveOneItem(1);
    assertThat(response).isEqualTo(expectedResponse);
    verify(productRepository, times(1)).reduceAmountByOne(1);
  }
  @Test
  public void reserveOneItemShouldReturnOutOfStock() {
    ResponseEntity expectedResponse = new ResponseEntity<>("Product 1 out of stock", HttpStatus.NOT_FOUND);
    when(productRepository.productExists(1)).thenReturn(true);
    when(productRepository.amountBiggerThanZero(1)).thenReturn(false);
    ResponseEntity response = productService.reserveOneItem(1);
    assertThat(response).isEqualTo(expectedResponse);
  }

}
