package com.example.demo.mapper;

import com.example.demo.entity.Employee;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;

public class EmployeeMapper {

  public static EmployeeResponse toResponse(Employee employee) {
    EmployeeResponse dto = new EmployeeResponse(
        employee.getId(), employee.getLastname(), employee.getFirstname(),
        employee.getBirthdate(), employee.getSupervisor()
    );

    return dto;
  }

  public static Employee toEntity(EmployeeRequest dto) {
    Employee employee = new Employee();

    employee.setLastname(dto.getLastname());
    employee.setFirstname(dto.getFirstname());
    employee.setBirthdate(dto.getBirthdate());
    employee.setSupervisor(dto.getSupervisor());

    return employee;
  }
}
