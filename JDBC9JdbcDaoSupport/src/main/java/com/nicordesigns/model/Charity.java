package com.nicordesigns.model;

import java.util.Objects;

public class Charity {

  private int charityId;
  private String charityTaxId;
  private String charityName;
  private String charityMission;
  private String charityWebAddress;
  private String charityFacebookAddress;
  private String charityTwitterAddress;
  private Category charityCategory;

  public Charity() {}
  // private List<Program> charityPrograms;

  public Charity(
      String charityTaxId,
      String charityName,
      String charityMission,
      String charityWebAddress,
      String charityFacebookAddress,
      String charityTwitterAddress) {

    this.charityTaxId = charityTaxId;
    this.charityName = charityName;
    this.charityMission = charityMission;
    this.charityWebAddress = charityWebAddress;
    this.charityFacebookAddress = charityFacebookAddress;
    this.charityTwitterAddress = charityTwitterAddress;
  }

  public Charity(
      int charityId,
      String charityTaxId,
      String charityName,
      String charityMission,
      String charityWebAddress,
      String charityFacebookAddress,
      String charityTwitterAddress,
      Category charityCategory) {

    this.charityId = charityId;
    this.charityTaxId = charityTaxId;
    this.charityName = charityName;
    this.charityMission = charityMission;
    this.charityWebAddress = charityWebAddress;
    this.charityFacebookAddress = charityFacebookAddress;
    this.charityTwitterAddress = charityTwitterAddress;
    this.charityCategory = charityCategory;
  }

  public int getCharityId() {
    return charityId;
  }

  public void setCharityId(int charityId) {
    this.charityId = charityId;
  }

  public String getCharityTaxId() {
    return charityTaxId;
  }

  public void setCharityTaxId(String charityTaxId) {
    this.charityTaxId = charityTaxId;
  }

  public String getCharityName() {
    return charityName;
  }

  public void setCharityName(String charityName) {
    this.charityName = charityName;
  }

  public String getCharityMission() {
    return charityMission;
  }

  public void setCharityMission(String charityMission) {
    this.charityMission = charityMission;
  }

  public String getCharityWebAddress() {
    return charityWebAddress;
  }

  public void setCharityWebAddress(String charityWebAddress) {
    this.charityWebAddress = charityWebAddress;
  }

  public String getCharityFacebookAddress() {
    return charityFacebookAddress;
  }

  public void setCharityFacebookAddress(String charityFacebookAddress) {
    this.charityFacebookAddress = charityFacebookAddress;
  }

  public String getCharityTwitterAddress() {
    return charityTwitterAddress;
  }

  public void setCharityTwitterAddress(String charityTwitterAddress) {
    this.charityTwitterAddress = charityTwitterAddress;
  }

  public Category getCharityCategory() {
    return charityCategory;
  }

  public void setCharityCategory(Category charityCategory) {
    this.charityCategory = charityCategory;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Charity)) return false;
    Charity charity = (Charity) o;
    return getCharityTaxId().equals(charity.getCharityTaxId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCharityTaxId());
  }

  @Override
  public String toString() {
    return "Charity{"
        + "charityId='"
        + charityId
        + '\''
        + ", charityTaxId='"
        + charityTaxId
        + '\''
        + ", charityName='"
        + charityName
        + '\''
        + ", charityMission='"
        + charityMission
        + '\''
        + ", charityWebAddress='"
        + charityWebAddress
        + '\''
        + ", charityFacebookAddress='"
        + charityFacebookAddress
        + '\''
        + ", charityTwitterAddress='"
        + charityTwitterAddress
        + '\''
        + ", charityCategory="
        + charityCategory
        + '}';
  }
}
