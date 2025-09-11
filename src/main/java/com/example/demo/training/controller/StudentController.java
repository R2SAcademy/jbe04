package com.example.demo.training.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.training.dto.StudentDetailResponse;
import com.example.demo.training.dto.StudentInfoDTO;
import com.example.demo.training.repository.StudentRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {
//  private final StudentRepository studentRepository;
//
//  public StudentController(StudentRepository studentRepository) {
//    this.studentRepository = studentRepository;
//  }
//
//  // GET /api/students/{id}/detail
//  @GetMapping("/{id}/detail")
//  public ResponseEntity<StudentDetailResponse> getDetail(@PathVariable Integer id) {
//    Student s = studentRepository.findByIdWithAll(id)
//        .orElseThrow(() -> new ResourceNotFoundException("Student not found with id = " + id));
//
//    // profile có thể null
//    String email = (s.getProfile() != null) ? s.getProfile().getEmail() : null;
//    String phone = (s.getProfile() != null) ? s.getProfile().getPhoneNumber() : null;
//
//    // courses có thể rỗng; sort cho đẹp
//    List<String> courseNames = s.getCourses().stream()
//        .map(Course::getName)
//        .sorted(Comparator.naturalOrder())
//        .toList();
//
//    StudentDetailResponse body = new StudentDetailResponse(
//        s.getId(),
//        s.getName(),
//        s.getClassRoom().getName(),
//        email,
//        phone,
//        courseNames
//    );
//
//    return ResponseEntity.ok(body);
//  }
//
//  // Endpoint mới: GET /api/students/info
//  @GetMapping("/info")
//  public ResponseEntity<List<StudentInfoDTO>> getStudentsInfo() {
//    List<StudentInfoDTO> result = studentRepository.findStudentWithClassAndProfile();
//    return ResponseEntity.ok(result);
//  }
}
