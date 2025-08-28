package encapsulation;

public class PersonTest {

  public static void main(String[] args) {
    Person person = new Person("Nguyen Van An", 22);
    person.setAge(18);
    System.out.println(person.getAge());
//    System.out.println(person); // Output: Nguyen Van An is 22 years old.
  }
}
