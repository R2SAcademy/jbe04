public class Student {

  // Properties
  String name;
  float mark1;
  float mark2;
  float mark3;

  float total() {
    return  mark1 + mark2 + mark3;
  }

  float average() {
    return total() / 3;
  }

}
