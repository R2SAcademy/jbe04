package exception;

public class Demo02 {
  public static void main(String[] args) {
    int array[] = {1, 2, 3, 4, 5}; // length = 5 (indices 0 to 4)

    try {
      for(int i = 0; i <= array.length; i++) {
        System.out.println("Element at index " + i + ": " + array[i]);
      }
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.out.println("Array index out of bounds: " + ex.getMessage());
    }

    System.out.println("End of program");
  }
}
