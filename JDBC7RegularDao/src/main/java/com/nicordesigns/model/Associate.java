package com.nicordesigns.model;

import java.util.Date;

public class Associate {

  private int charityId;
  private int charityAssociateType;
  private String charityAssociateName;
  private String charityAssociateSurname;
  private Date charityAssociateDOB;

  public Associate(
      int charityId,
      int charityAssociateType,
      String charityAssociateName,
      String charityAssociateSurname,
      Date charityAssociateDOB) {
    this.charityId = charityId;
    this.charityAssociateType = charityAssociateType;
    this.charityAssociateName = charityAssociateName;
    this.charityAssociateSurname = charityAssociateSurname;
    this.charityAssociateDOB = charityAssociateDOB;
  }

  public int getCharityId() {
    return charityId;
  }

  public void setCharityId(int charityId) {
    this.charityId = charityId;
  }

  public int getCharityAssociateType() {
    return charityAssociateType;
  }

  public void setCharityAssociateType(int charityAssociateType) {
    this.charityAssociateType = charityAssociateType;
  }

  public String getCharityAssociateName() {
    return charityAssociateName;
  }

  public void setCharityAssociateName(String charityAssociateName) {
    this.charityAssociateName = charityAssociateName;
  }

  public String getCharityAssociateSurname() {
    return charityAssociateSurname;
  }

  public void setCharityAssociateSurname(String charityAssociateSurname) {
    this.charityAssociateSurname = charityAssociateSurname;
  }

  public Date getCharityAssociateDOB() {
    return charityAssociateDOB;
  }

  public void setCharityAssociateDOB(Date charityAssociateDOB) {
    this.charityAssociateDOB = charityAssociateDOB;
  }
}
