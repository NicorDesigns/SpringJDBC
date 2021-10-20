package com.nicordesigns.model;

public class Program {

  private int programId;
  private String programDescription;

  public Program(int programId, String programDescription) {
    this.programId = programId;
    this.programDescription = programDescription;
  }

  public int getProgramId() {
    return programId;
  }

  public void setProgramId(int programId) {
    this.programId = programId;
  }

  public String getProgramDescription() {
    return programDescription;
  }

  public void setProgramDescription(String programDescription) {
    this.programDescription = programDescription;
  }

  @Override
  public String toString() {
    return "Program{"
        + "programId="
        + programId
        + ", programDescription='"
        + programDescription
        + '\''
        + '}';
  }
}
