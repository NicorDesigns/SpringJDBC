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

  public Charity(
      int charityId,
      String charity_tax_id,
      String charity_name,
      String charity_mission,
      String charity_web_address,
      String charity_facebook_address,
      String charity_twitter_address) {

    this.charityId = charityId;
    this.charityTaxId = charity_tax_id;
    this.charityName = charity_name;
    this.charityMission = charity_mission;
    this.charityWebAddress = charity_web_address;
    this.charityFacebookAddress = charity_facebook_address;
    this.charityTwitterAddress = charity_twitter_address;
  }

  public Charity(
      String charity_tax_id,
      String charity_name,
      String charity_mission,
      String charity_web_address,
      String charity_facebook_address,
      String charity_twitter_address,
      Category category) {

    this.charityTaxId = charity_tax_id;
    this.charityName = charity_name;
    this.charityMission = charity_mission;
    this.charityWebAddress = charity_web_address;
    this.charityFacebookAddress = charity_facebook_address;
    this.charityTwitterAddress = charity_twitter_address;
    this.charityCategory = category;
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
  public String toString() {
    return "Charity{"
        + '\n'
        + "charityId="
        + charityId
        + '\n'
        + ", charityTaxId='"
        + charityTaxId
        + '\n'
        + ", charityName='"
        + charityName
        + '\n'
        + ", charityMission='"
        + charityMission
        + '\n'
        + ", charityWebAddress='"
        + charityWebAddress
        + '\n'
        + ", charityFacebookAddress='"
        + charityFacebookAddress
        + '\n'
        + ", charityTwitterAddress='"
        + charityTwitterAddress
        + '\n'
        + ", charityCategory="
        + charityCategory
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Charity)) return false;
    Charity charity = (Charity) o;
    return getCharityTaxId().equals(charity.getCharityTaxId())
        && getCharityName().equals(charity.getCharityName())
        && getCharityMission().equals(charity.getCharityMission())
        && getCharityWebAddress().equals(charity.getCharityWebAddress())
        && getCharityFacebookAddress().equals(charity.getCharityFacebookAddress())
        && getCharityTwitterAddress().equals(charity.getCharityTwitterAddress())
        && getCharityCategory().equals(charity.getCharityCategory());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getCharityTaxId(),
        getCharityName(),
        getCharityMission(),
        getCharityWebAddress(),
        getCharityFacebookAddress(),
        getCharityTwitterAddress(),
        getCharityCategory());
  }
}
