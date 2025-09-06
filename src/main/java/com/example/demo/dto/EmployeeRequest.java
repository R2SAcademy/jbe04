package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public class EmployeeRequest {
  @NotBlank(message = "Last name is required")
  private String lastname;

  @NotBlank(message = "First name is required")
  private String firstname;

  @Past(message = "Birthdate must be in the past")
  private LocalDate birthdate;

  private Integer supervisor; // can be null if no boss

  @NotBlank
  private String email;

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public Integer getSupervisor() {
    return supervisor;
  }

  public void setSupervisor(Integer supervisor) {
    this.supervisor = supervisor;
  }
}
