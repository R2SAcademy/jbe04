import java.util.Scanner;

public class StudentManagment {

  public static void main(String[] args) {
//    Student student1 = new Student();
//
//    student1.name = "john";
//    student1.mark1 = 80;
//    student1.mark2 = 60;
//    student1.mark3 = 70;
//
//    System.out.println("Name: " + student1.name);
//    System.out.println("Mark 1: " + student1.mark1);
//    System.out.println("Mark 2: " + student1.mark2);
//    System.out.println("Mark 3: " + student1.mark3);
//    System.out.println("Total: " + student1.total());
//    System.out.println("Average: " + student1.average());
//
//    Student student2 = new Student();
//
//    student2.name = "anu";
//    student2.mark1 = 90;
//    student2.mark2 = 60;
//    student2.mark3 = 80;
//
//    System.out.println("Name: " + student2.name);
//    System.out.println("Mark 1: " + student2.mark1);
//    System.out.println("Mark 2: " + student2.mark2);
//    System.out.println("Mark 3: " + student2.mark3);
//    System.out.println("Total: " + student2.total());
//    System.out.println("Average: " + student2.average());

    // Using array of Student objects
    Student[] students = new Student[100];
    int count = 0;
    Scanner scanner = new Scanner(System.in);
    String choice;

    final String ADD = "1";
    final String SHOW = "2";
    final String EXIT = "3";

    do {
      System.out.println("1. Add student");
      System.out.println("2. Show all students");
      System.out.println("3. Exit");

      System.out.println("Enter your choice: ");
      choice = scanner.nextLine();

      switch (choice) {
        case  ADD: // add student
          if (count >= students.length) {
            System.out.println("Student list is full.");
            break;
          }

          Student student = new Student();

          System.out.print("Enter student name: ");
          student.name = scanner.nextLine();

          System.out.print("Enter mark 1: ");
          student.mark1 = Float.parseFloat(scanner.nextLine());

          System.out.print("Enter mark 2: ");
          student.mark2 = Float.parseFloat(scanner.nextLine());

          System.out.print("Enter mark 3: ");
          student.mark3 = Float.parseFloat(scanner.nextLine());

          students[count++] = student;
          break;
        case SHOW: // show all students
          System.out.println("List of students:");
          for (int i = 0; i < count; i++) {
            System.out.println("Student " + (i + 1) + ":");
            System.out.println("Name: " + students[i].name);
            System.out.println("Mark 1: " + students[i].mark1);
            System.out.println("Mark 2: " + students[i].mark2);
            System.out.println("Mark 3: " + students[i].mark3);
            System.out.println("Total: " + students[i].total());
            System.out.println("Average: " + students[i].average());
          }
          break;
      }

    } while (!choice.equals(EXIT));

  }
}
