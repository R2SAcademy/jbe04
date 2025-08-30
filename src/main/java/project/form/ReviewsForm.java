package project.form;

import project.entity.Reviews;
import project.util.ScannerUtil;


import java.util.Scanner;

public class ReviewsForm {
    private static final Scanner scanner = new Scanner(System.in);

    public static Reviews inputReview() {
        Reviews review = new Reviews();

        //input user id
        review.setCustomerId(ScannerUtil.readInt("Enter customer id: "));

        //input product id
        review.setProductId(ScannerUtil.readInt("Enter product id: "));

        //input rating 1 -> 5
        int rating;
        do {
            rating = ScannerUtil.readInt("Enter rating (1 -5): ");
            if (rating < 1 || rating > 5) {
                System.out.println("Invalid rating. Please enter again (1-5): ");
            }
        } while (rating < 1 || rating > 5);
        review.setRating(rating);

        //input comment
        review.setComment(ScannerUtil.readNonEmptyString("Enter your comment: "));

        return review;
    }

    public static Reviews inputUpdate() {
        Reviews reviews = new Reviews();

        System.out.print("Enter review_id to update: ");
        reviews.setReviewId(Integer.parseInt(scanner.nextLine().trim()));

        System.out.print("Enter your customer_id (permission check): ");
        reviews.setCustomerId(Integer.parseInt(scanner.nextLine().trim()));

        int rating;
        do {
            System.out.print("Enter new rating (1-5): ");
            rating = Integer.parseInt(scanner.nextLine().trim());
            if (rating < 1 || rating > 5) System.out.println("Invalid. Try again.");
        } while (rating < 1 || rating > 5);
        reviews.setRating(rating);

        System.out.print("Enter new comment: ");
        reviews.setComment(scanner.nextLine().trim());

        return reviews;
    }
}
