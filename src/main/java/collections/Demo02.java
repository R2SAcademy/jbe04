package collections;

import java.util.ArrayList;

public class Demo02 {

  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();

    list.add("apple");
    list.add("banana");
    list.add("orange");

    String searchElement = "apple";
    // Find the element (found or not found)
    list.stream().filter(fruit -> searchElement.equals(fruit))
        .findFirst()
        .ifPresentOrElse(
            (fruit) -> System.out.println(searchElement + " found in the list."),
            () -> System.out.println(searchElement + " not found in the list.")
        );
  }
}
