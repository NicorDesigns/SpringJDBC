package com.nicordesigns.model;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Program)) return false;
    Program program = (Program) o;
    return getProgramDescription().equals(program.getProgramDescription());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getProgramDescription());
  }
}
