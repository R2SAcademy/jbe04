package collections;

import java.util.HashSet;
import java.util.Scanner;

public class UserRegistration {
  public static void main(String[] args) {
    HashSet<User> users = new HashSet<>();
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("Enter name (or 'exit'): ");
      String name = scanner.nextLine();

      if (name.equalsIgnoreCase("exit"))
        break;

      System.out.print("Enter email: ");
      String email = scanner.nextLine();

      User newUser = new User(name, email);

      if (users.contains(newUser)) {
        System.out.println("Email already registered!");
      } else {
        users.add(newUser);
        System.out.println("User registered successfully.");
      }
    }
  }
}
