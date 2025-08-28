package modifiers;

public class Counter {
  static int count = 0;

  public Counter() {
    count++;
  }

  public void display() {
    System.out.println("count = " + count);
  }


  public static void main(String[] args) {
    Counter c1 = new Counter();
    c1.display();

    Counter c2 = new Counter();
    c2.display();
  }
}

