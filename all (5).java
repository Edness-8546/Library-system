import java.util.ArrayList;
import java.util.List;
class NegativePriceException extends Exception {
    public NegativePriceException(String message) {
        super(message);
    }
}
class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
class Product {
    String name;
    double price;
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return name + " (" + price + " руб.)";
    }
}
class ShoppingCart {
    List<Product> products;
    public ShoppingCart() {
        this.products = new ArrayList<>();
    }
    public void addProduct(Product product) throws NegativePriceException {
        if (product.getPrice() < 0) {
            throw new NegativePriceException("Цена продукта " + product.getName() + " не может быть отрицательной");
        }
        products.add(product);
        System.out.println("добавлен продукт: " + product);
    }
    public void removeProduct(String productName) throws ProductNotFoundException {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(productName)) {
                Product removed = products.remove(i);
                System.out.println("Удалён продукт " + removed);
                return;
            }
        }
        throw new ProductNotFoundException("Продукт " + productName + " не найден в корзине");
    }
    public void showCart() {
        System.out.println("\nСодержимое корзины:");
        if (products.isEmpty()) {
            System.out.println("Вы нашли фигу");
        } else {
            for (Product p : products) {
                System.out.println(" - " + p);
            }
        }
        System.out.println();
    }
}
public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        System.out.println("---Добавляем 3 продукта---");
        try {
            cart.addProduct(new Product("полавинка Хлеба", 1050));
            cart.addProduct(new Product("Молоко", 1500));
            cart.addProduct(new Product("Сыр", 300));
        } catch (NegativePriceException e) {
            System.out.println("Ошибка " + e.getMessage());
        }
        cart.showCart();
        System.out.println("---Пытаемся добавить продукт с ценой -100---");
        try {
            cart.addProduct(new Product("чипсы", -100));
        } catch (NegativePriceException e) {
            System.out.println("поймано исключение NegativePriceException: " + e.getMessage());
        }
        System.out.println("\n---Пытаемся удалить несуществующий продукт---");
        try {
            cart.removeProduct("Пицца");
        } catch (ProductNotFoundException e) {
            System.out.println("Поймано исключение ProductNotFoundException: " + e.getMessage());
        }
        System.out.println("\n=== А теперь удалим существующий продукт ===");
        try {
            cart.removeProduct("Молоко");
        } catch (ProductNotFoundException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        cart.showCart();
    }
}
