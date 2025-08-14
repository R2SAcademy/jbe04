import java.util.Scanner;

public class Employee {
  private String code;
  String name;
  int bYear;
  String address;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Employee() {
  }

  public Employee(String code, String name, int bYear, String address) {
    this.code = code;
    this.name = name;
    this.bYear = bYear;
    this.address = address;
  }

  void input() {
    // input
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter employee code: ");
    code = scanner.nextLine();

    System.out.print("Enter employee name: ");
    name = scanner.nextLine();

    System.out.print("Enter employee birth year: ");
    bYear = Integer.parseInt(scanner.nextLine());

    System.out.print("Enter employee address: ");
    address = scanner.nextLine();
  }


  String toString1() {
    return code + "," + name + "," + bYear + "," + address;
  }

}
