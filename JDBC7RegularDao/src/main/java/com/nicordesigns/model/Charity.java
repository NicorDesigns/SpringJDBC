package com.nicordesigns.model;

public class Charity {

  private int charityId;
  private String charityTaxId;
  private String charityName;
  private String charityMission;
  private String charityWebAddress;
  private String charityFacebookAddress;
  private String charityTwitterAddress;

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

  @Override
  public String toString() {
    return "Charity{"
        + "charityId="
        + charityId
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
        + '}';
  }
}
