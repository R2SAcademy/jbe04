package collections;

import java.util.ArrayList;

public class Demo {

  public static void main(String[] args) {
    ArrayList<Integer> list = new ArrayList<>();
    list.add(10);
    list.add(20);
    list.add(30);

    for(int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }

    // for-each loop
    // for (Type item : collection) { ... }
    for (int number : list) {
      System.out.println(number);
    }
  }
}
