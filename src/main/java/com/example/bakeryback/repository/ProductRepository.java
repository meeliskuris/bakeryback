package com.example.bakeryback.repository;

import com.example.bakeryback.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

  @Autowired
  NamedParameterJdbcTemplate template;

  static final String NOT_FOUND = "Product not found";
  static final String EXECUTED = "Executed";

  public List<Product> getAllProducts() {
    String sql = "SELECT * FROM product";
    return template.query(sql, new MapSqlParameterSource(), new BeanPropertyRowMapper<>(Product.class));
  }

  public Boolean productExists(int id) {
    String sql = "SELECT count(*) FROM product WHERE id= :id";
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
    if (template.queryForObject(sql, parameters, Integer.class) > 0) {
      return Boolean.TRUE;
    } else {
      return Boolean.FALSE;
    }
  }

  public Boolean amountBiggerThanZero(int id) {
    String sql = "SELECT available_amount FROM product WHERE id= :id";
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
    if (template.queryForObject(sql, parameters, Integer.class) > 0) {
      return Boolean.TRUE;
    } else {
      return Boolean.FALSE;
    }
  }

  public ResponseEntity<String> reduceAmountByOne(int id) {
    String sql = "UPDATE product SET available_amount=available_amount-1 WHERE id= :id";
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
    if (template.update(sql, parameters) == 1) {
      return new ResponseEntity<>(EXECUTED, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.NOT_FOUND);
    }
  }

  public String increaseAmount(int id, int amount) {
    String sql = "UPDATE product SET available_amount=available_amount+ :amount WHERE id= :id";
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id).addValue("amount", amount);
    if (template.update(sql, parameters) == 1) {
      return EXECUTED;
    } else {
      return NOT_FOUND;
    }
  }

  public Boolean isAmountChangeable(int id) {
    String sql = "SELECT amount_changeable FROM product WHERE id= :id";
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
    return template.queryForObject(sql, parameters, Boolean.class);
  }

  public String setAvailableAmount(int id, int availableAmount) {
    String sql = "UPDATE product SET available_amount= :available_amount WHERE id= :id";
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("available_amount", availableAmount)
        .addValue("id", id);
    if (template.update(sql, parameters) == 1) {
      return EXECUTED;
    } else {
      return NOT_FOUND;
    }
  }


}
