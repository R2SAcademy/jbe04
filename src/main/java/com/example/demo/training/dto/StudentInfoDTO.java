package com.example.demo.training.dto;

public record StudentInfoDTO (
    Integer id,
    String studentName,
    String className,
    String email,
    String phoneNumber) {}