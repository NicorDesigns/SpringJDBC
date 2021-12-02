package com.nicordesigns.dao;

import com.nicordesigns.model.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class CharityCategorySupportDao {

  public static Category findCategoryById(JdbcTemplate jdbcTemplate, Integer categoryId) {
    Category category;

    String sqlQuery = "SELECT * FROM category WHERE CATEGORY_ID = ?";
    BeanPropertyRowMapper<Category> categoryBeanPropertyRowMapper =
        BeanPropertyRowMapper.newInstance(Category.class);

    category = jdbcTemplate.queryForObject(sqlQuery, categoryBeanPropertyRowMapper, categoryId);

    return category;
  }

  public static Integer findCategoryIdInCharityRelationshipRow(
      JdbcTemplate jdbcTemplate, int charityId) {

    System.out.println(
        "SELECT CATEGORY_ID FROM charity_category WHERE CHARITY_ID =   " + charityId);
    String sqlQuery = "SELECT CATEGORY_ID FROM charity_category WHERE CHARITY_ID = ?";
    return jdbcTemplate.query(
        sqlQuery,
        new Object[] {charityId},
        resultSet -> resultSet.next() ? resultSet.getInt("CATEGORY_ID") : null);
  }
}
