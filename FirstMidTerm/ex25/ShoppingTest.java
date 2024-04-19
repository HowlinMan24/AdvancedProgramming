package mk.kolokvium.updatedEX.ex25;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class InvalidOperationException extends Exception {
    public String id;
    public InvalidOperationException(String id) {
        this.id = id;
    }

    public String getMessage() {
        return String.format("The quantity of the product with id " + id + " can not be 0.");
    }
}

class ShoppingCart {
    public List<Cart> cartList;
    public ShoppingCart() {
        this.cartList = new ArrayList<>();
    }

    void addItem(String itemData) throws InvalidOperationException {
        String[] parts = itemData.split(";");
        String id = parts[1];
        String name = parts[2];
        int price = Integer.parseInt(parts[3]);
        float quantity = Float.parseFloat(parts[4]);
        if(quantity<=0)
            throw new InvalidOperationException(id);
        if (parts[0].equals("WS")) {
            cartList.add(new WholeCart(name, id, price, quantity));
        } else {
            cartList.add(new PerGramCart(name, id, price, quantity));
        }
    }

    void printShoppingCart(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        cartList.sort(Comparator.reverseOrder());
        cartList.forEach(System.out::println);
        pw.flush();
//        pw.close();
    }

    public void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
        if (discountItems.isEmpty()) {
            throw new InvalidOperationException("There are no products with discount.");
        } else {

            PrintWriter pw = new PrintWriter(os);

            for (Cart cart : cartList) {
                for (Integer discountItem : discountItems) {
                    if (Integer.parseInt(cart.id) == discountItem) {
                        cart.price *= 0.1f;
                    }
                }
            }

//            cartList.sort(Comparator.naturalOrder());
            for (Cart cart : cartList) {
                for (Integer discountItem : discountItems) {
                    if (Integer.parseInt(cart.id) == discountItem) {
                        pw.println(cart);
                    }
                }
            }

            pw.flush();
            pw.close();
        }
    }
}

abstract class Cart implements Comparable<Cart> {

    public String name;
    public float price;
    public float quantity;

    public String id;

    public Cart(String name, String id, float price, float quantity) throws InvalidOperationException {
//        if (quantity <= 0)
//            throw new InvalidOperationException(id);
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    abstract public float calculatePrice();

    abstract public String getType();
}

class WholeCart extends Cart {

    public WholeCart(String name, String id, float price, float quantity) throws InvalidOperationException {
        super(name, id, price, quantity);
    }

    @Override
    public float calculatePrice() {
        return price * quantity;
    }

    @Override
    public String getType() {
        return "WS";
    }

    @Override
    public int compareTo(Cart o) {
        return Float.compare(this.calculatePrice(), o.calculatePrice());
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f", id, calculatePrice());
    }
}

class PerGramCart extends Cart {

    public PerGramCart(String name, String id, float price, float quantity) throws InvalidOperationException {
        super(name, id, price, quantity / 1000);
    }

    @Override
    public float calculatePrice() {
        return price * quantity;
    }

    @Override
    public String getType() {
        return "PS";
    }

    @Override
    public int compareTo(Cart o) {
        return Float.compare(this.calculatePrice(), o.calculatePrice());
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f", id, calculatePrice());
    }

}


public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}
