package inheritance;

public class Person {
  String id;
  String name;
  String email;

  public Person() {
  }

  public Person(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public  void input() {
    // code to input person details
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
