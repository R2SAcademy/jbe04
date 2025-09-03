package project.entity;

public enum OrderStatus {
    PENDING(1),
    CONFIRMED(2),
    SHIPPED(3),
    CANCELLED(4);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus fromCode(int code) {
        return switch (code) {
            case 2 -> CONFIRMED;
            case 3 -> SHIPPED;
            case 4 -> CANCELLED;
            default -> PENDING;
        };
    }
}