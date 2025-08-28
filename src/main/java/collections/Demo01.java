package collections;

import java.util.ArrayList;

public class Demo01 {

  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<>();

    list.add("apple");
    list.add("banana");
    list.add("orange");

    String searchElement = "orange";
    // Find the element (found or not found)
    for (String fruit : list) {
      // Code here
      if (searchElement.equals(fruit)) {
        System.out.println(searchElement + " found in the list.");
        break;
      }
    }
  }
}
