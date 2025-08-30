package project.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reviews {
    private int reviewId;
    private int customerId;
    private int productId;
    private int rating;
    private String comment;
    private LocalDateTime reviewDate;

    public Reviews() {
    }

    public Reviews(int reviewId, int customerId, int productId, int rating, String comment, LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return String.format("--- Shipping Details ---%n" +
                        "  Review Id:       %d%n" +
                        "  Customer Id:     %d%n" +
                        "  Product Id:      %d%n" +
                        "  Rating:          %s%n" +
                        "  Comment:         %s%n" +
                        "  Review Date:     %s%n" +
                        "----------------------",
                reviewId, customerId, productId, rating, comment, reviewDate);
    }
}