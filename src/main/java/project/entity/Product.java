package project.entity;

public class Product {
    private int productId;
    private String productName;
    private Integer categoryId;
    private Integer brandId;
    private Integer modelYear;
    private Float price;
    private String description;

    public Product() {
    }

    public Product(int productId, String productName, Integer categoryId, Integer brandId, Integer modelYear, Float price, String description) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.modelYear = modelYear;
        this.price = price;
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", modelYear=" + modelYear +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
