package com.example.demo.controller;

import com.example.demo.repository.EmployeeRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import com.example.demo.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

  @Autowired
  private EmployeeRepository employeeRepository;

//  @GetMapping
//  public ResponseEntity<List<Employee>> list() {
//    return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
//  }

  @GetMapping
  public ResponseEntity<List<EmployeeResponse>> list() {
    List<EmployeeResponse> employees = employeeRepository.findAll().stream()
        .map(EmployeeMapper::toResponse)
        .toList();

    return new ResponseEntity<>(employees, HttpStatus.OK);
  }

//  @PostMapping
//  public ResponseEntity<Employee> create(@RequestBody Employee employee) {
//      return new ResponseEntity<>(employeeRepository.save(employee), HttpStatus.CREATED);
//  }
@PostMapping
public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest employee) {
    Employee savedEmployee = employeeRepository.save(EmployeeMapper.toEntity(employee));
  return new ResponseEntity<>(EmployeeMapper.toResponse(savedEmployee), HttpStatus.CREATED);
}

  @PutMapping("/{id}")
  public ResponseEntity<Employee> update(@PathVariable int id, @RequestBody Employee employee) {
    Employee updatedEmployee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

    updatedEmployee.setLastname(employee.getLastname());
    updatedEmployee.setFirstname(employee.getFirstname());
    updatedEmployee.setBirthdate(employee.getBirthdate());
    updatedEmployee.setSupervisor(employee.getSupervisor());

    return new ResponseEntity<>(employeeRepository.save(updatedEmployee), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable int id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

    employeeRepository.delete(employee);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/supervisor/{supervisorId}")
  public ResponseEntity<List<Employee>> getBySupervisor(@PathVariable Integer supervisorId) {
    List<Employee> employees = employeeRepository.findBySupervisor(supervisorId);
    return ResponseEntity.ok(employees);
  }

  @GetMapping("/search")
  public ResponseEntity<List<Employee>> searchByName(@RequestParam String keyword) {
    List<Employee> employees = employeeRepository.searchByName(keyword);
    return ResponseEntity.ok(employees);
  }

}
