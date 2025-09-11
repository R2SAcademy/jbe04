package com.example.demo.training.dto;

import java.util.List;

public record StudentDetailResponse(
    Integer id,
    String name,
    String className,
    String email,         // from Profile (nullable)
    String phoneNumber,   // from Profile (nullable)
    List<String> courses  // course names
) { }
