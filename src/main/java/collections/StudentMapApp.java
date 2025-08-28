package collections;

import java.util.HashMap;
import java.util.Scanner;

public class StudentMapApp {
  public static void main(String[] args) {
    HashMap<String, Student> studentMap = new HashMap<>();

    // Create and add students
    Student s1 = new Student("SV001", "Alice", 8.5);
    Student s2 = new Student("SV002", "Bob", 7.0);
    Student s3 = new Student("SV003", "Charlie", 9.2);

    studentMap.put(s1.getStudentId(), s1);
    studentMap.put(s2.getStudentId(), s2);
    studentMap.put(s3.getStudentId(), s3);

    // Lookup
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter student ID to look up: ");
    String inputId = scanner.nextLine().toUpperCase().trim();

    if (studentMap.containsKey(inputId)) {
      System.out.println("Student Found: " + studentMap.get(inputId));
    } else {
      System.out.println("Student ID not found.");
    }
  }

}
