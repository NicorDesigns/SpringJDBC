package com.nicordesigns.model;

import java.util.Objects;

public class Program {

  private int programId;
  private String programType;

  public Program(int programId, String programType) {
    this.programId = programId;
    this.programType = programType;
  }

  public Program(String programType) {
    this.programType = programType;
  }

  public Program() {}

  public int getProgramId() {
    return programId;
  }

  public void setProgramId(int programId) {
    this.programId = programId;
  }

  public String getProgramType() {
    return programType;
  }

  public void setProgramType(String programType) {
    this.programType = programType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Program)) return false;
    Program program = (Program) o;
    return getProgramType().equals(program.getProgramType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getProgramType());
  }

  @Override
  public String toString() {
    return "Program{"
        + "programId="
        + programId
        + ", programDescription='"
        + programType
        + '\''
        + '}';
  }
}
