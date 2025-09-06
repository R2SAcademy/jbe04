package com.example.demo.dto;

import java.time.LocalDate;

public class EmployeeResponse {
  private int id;
  private String lastname;
  private String firstname;
  private LocalDate birthdate;
  private Integer supervisor; // Extensible: show supervisor name instead of id

  public EmployeeResponse(int id, String lastname, String firstname, LocalDate birthdate, Integer supervisor) {
    this.id = id;
    this.lastname = lastname;
    this.firstname = firstname;
    this.birthdate = birthdate;
    this.supervisor = supervisor;
  }

  public int getId() {
    return id;
  }

  public String getLastname() {
    return lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public Integer getSupervisor() {
    return supervisor;
  }
}
