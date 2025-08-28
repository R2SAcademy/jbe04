package collections;

public class Student {
  private String studentId;
  private String name;
  private double grade;

  public Student(String studentId, String name, double grade) {
    this.studentId = studentId.toUpperCase().trim();
    this.name = name;
    this.grade = grade;
  }

  // getters
  public String getStudentId() {
    return studentId;
  }

  @Override
  public String toString() {
    return studentId + " - " + name + " - Grade: " + grade;
  }
}
