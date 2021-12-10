package com.nicordesigns.jdbc12.model;

import java.util.Objects;

public class Category {

  private int categoryId;
  private String categoryName;

  public Category() {}

  public Category(String categoryName) {
    this.categoryName = categoryName;
  }

  public Category(int category_id, String category_name) {
    this.categoryId = category_id;
    this.categoryName = category_name;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public String toString() {
    return "Category{"
        + "categoryId="
        + categoryId
        + ", categoryName='"
        + categoryName
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Category)) return false;
    Category category = (Category) o;
    return getCategoryName().equals(category.getCategoryName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCategoryName());
  }
}
