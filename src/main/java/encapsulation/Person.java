package encapsulation;

public class Person {
  private String name;
  private int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // setter
  public void setAge(int age) {
    if (age < 0) {
      System.out.println("Age cannot be negative.");
    } else {
      this.age = age;
    }
  }

  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return name + " is " + age + " years old.";
  }
}
