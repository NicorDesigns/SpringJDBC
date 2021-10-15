package com.nicordesigns.model;

public class Category {

  private int categoryId;
  private String categoryName;

  public Category(String categoryName) {
    this.categoryName = categoryName;
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
}
