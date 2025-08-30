package project.form;

import project.entity.Product;
import project.util.ScannerUtil;
import project.util.ValidationUtil;

public class ProductForm {
    public static int inputProductId(String action) {
        return ScannerUtil.readInt("Enter product ID to " + action + ": ");
    }

    public static Product inputNewProduct() {
        Product product = new Product();
        String name = null;

        while (!ValidationUtil.isValidString(name)) {
            name = ScannerUtil.readNonEmptyString("Enter product name: ");
            if (ValidationUtil.isValidString(name)) {
                product.setProductName(name);
            } else {
                System.out.println("Product name must not be empty.");
            }
        }

        product.setCategoryId(ScannerUtil.readInt("Enter category ID: "));
        product.setBrandId(ScannerUtil.readIntAllowNull("Enter brand ID: "));

        Integer modelYear;
        do {
            modelYear = ScannerUtil.readIntAllowNull("Enter product model year: ");
            if (ValidationUtil.isValidYear(modelYear)) {
                product.setModelYear(modelYear);
            } else {
                System.out.println("Model year must >= 1990 and <=2100.");
            }
        } while (!ValidationUtil.isValidYear(modelYear));

        Float price;
        do {
            price = ScannerUtil.readFloatAllowNull("Enter new price: ");
            if (ValidationUtil.isValidPrice(price)) {
                product.setPrice(price);
            } else {
                System.out.println("Price must be >0.");
            }
        } while (!ValidationUtil.isValidPrice(price));

        product.setDescription(ScannerUtil.readAllowEmptyString("Enter description: "));

        return product;
    }

    public static Product inputUpdateProduct() {
        Product product = new Product();
        int id;
        do {
            id = ScannerUtil.readInt("Enter product ID to update: ");
            if (!ValidationUtil.isValidId(id))
                System.out.println("ID can't not empty");

        } while (!ValidationUtil.isValidId(id));
        product.setProductId(id);

        product.setProductName(ScannerUtil.readAllowEmptyString("Enter new product name: "));
        product.setCategoryId(ScannerUtil.readIntAllowNull("Enter new category Id: "));
        product.setBrandId(ScannerUtil.readIntAllowNull("Enter new brand Id: "));

        Integer modelYear;
        do {
            modelYear = ScannerUtil.readIntAllowNull("Enter new model year: ");
            if (ValidationUtil.isValidYear(modelYear)) {
                product.setModelYear(modelYear);
            } else {
                System.out.println("Model year must >= 1990 and <=2100.");
            }
        } while (!ValidationUtil.isValidYear(modelYear));

        Float price;
        do {
            price = ScannerUtil.readFloatAllowNull("Enter new price: ");
            if (ValidationUtil.isValidPrice(price)) {
                product.setPrice(price);
            } else {
                System.out.println("Price must be >0.");
            }
        } while (!ValidationUtil.isValidPrice(price));

        product.setDescription(ScannerUtil.readAllowEmptyString("Enter new description: "));

        return product;
    }

    public static String inputNameKeyword() {
        return ScannerUtil.readAllowEmptyString("Enter name keyword: ");
    }

    public static Integer inputCategoryId() {
        return ScannerUtil.readIntAllowNull("Enter Category Id: ");
    }

    public static Integer inputBrandId() {
        return ScannerUtil.readIntAllowNull("Enter brand Id: ");
    }

    public static Integer inputModelYear() {
        Integer modelYear;
        do {
            modelYear = ScannerUtil.readIntAllowNull("Enter model year: ");
            if (ValidationUtil.isValidYear(modelYear)) {
                return modelYear;
            } else {
                System.out.println("Model year must >= 1990 and <=2100.");
            }
        } while (!ValidationUtil.isValidYear(modelYear));
        return null;
    }

    public static Float inputMinPrice() {
        Float minPrice;
        do {
            minPrice = ScannerUtil.readFloatAllowNull("Enter min price: ");
            if (ValidationUtil.isValidPrice(minPrice)) {
                return minPrice;
            } else {
                System.out.println("Price must be >0.");
            }
        } while (!ValidationUtil.isValidPrice(minPrice));
        return null;
    }

    public static Float inputMaxPrice() {
        Float maxPrice;
        do {
            maxPrice = ScannerUtil.readFloatAllowNull("Enter max price: ");
            if (ValidationUtil.isValidPrice(maxPrice)) {
                return maxPrice;
            } else {
                System.out.println("Price must be >0.");
            }
        } while (!ValidationUtil.isValidPrice(maxPrice));
        return null;
    }
}
