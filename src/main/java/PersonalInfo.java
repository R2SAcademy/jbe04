import java.util.Scanner;

public class PersonalInfo {

  public static void main(String[] args) {
    String name = null; // default value
    int age ;

    // input
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter your age: ");
//    age = scanner.nextLine(); // convert input to int
    age = Integer.parseInt(scanner.nextLine());

    System.out.print("Enter your name: ");
    name = scanner.nextLine();

    // output
    System.out.println("Hello " + name + ", you are " + age + " years old.");

  }

}
