// CartForm.java
package project.form;

import java.util.Scanner;

public class CartForm {
    private final Scanner sc = new Scanner(System.in);

    public int inputUserId() {
        System.out.print("Nhập userId: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int inputProductId() {
        System.out.print("Nhập productId: ");
        return Integer.parseInt(sc.nextLine());
    }

    public int inputQuantity() {
        System.out.print("Nhập số lượng: ");
        return Integer.parseInt(sc.nextLine());
    }
}
