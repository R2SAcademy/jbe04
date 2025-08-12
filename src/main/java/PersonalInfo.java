import java.util.Scanner;

public class PersonalInfo {

  public static void main(String[] args) {
    String name;
    int age;

    // input
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter your name: ");
    name = scanner.nextLine();

    System.out.print("Enter your age: ");
    age = scanner.nextInt();

    // output
    System.out.println("Hello " + name + ", you are " + age + " years old.");

  }

}
