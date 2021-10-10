package com.nicordesigns.model;

import java.net.URL;

public class Charity {

  private int charityId;
  private String charityTaxId;
  private String charityName;
  private String charityMission;
  private URL charityWebAddress;
  private URL charityFacebookAddress;
  private URL charityTwitterAddress;

  public Charity(
      int charityId,
      String charityTaxId,
      String charityName,
      String charityMission,
      URL charityWebAddress,
      URL charityFacebookAddress,
      URL charityTwitterAddress) {
    this.charityId = charityId;
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

  public URL getCharityWebAddress() {
    return charityWebAddress;
  }

  public void setCharityWebAddress(URL charityWebAddress) {
    this.charityWebAddress = charityWebAddress;
  }

  public URL getCharityFacebookAddress() {
    return charityFacebookAddress;
  }

  public void setCharityFacebookAddress(URL charityFacebookAddress) {
    this.charityFacebookAddress = charityFacebookAddress;
  }

  public URL getCharityTwitterAddress() {
    return charityTwitterAddress;
  }

  public void setCharityTwitterAddress(URL charityTwitterAddress) {
    this.charityTwitterAddress = charityTwitterAddress;
  }
}
