package mk.myLabs.No_3.ex1;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

// TODO add toString methods in the Item classes


class InvalidExtraTypeException extends Exception {

    public InvalidExtraTypeException() {
    }
}

class InvalidPizzaTypeException extends Exception {

    public InvalidPizzaTypeException() {
    }
}

class ItemOutOfStockException extends Exception {

    public String message;

    public ItemOutOfStockException(Item item) {
        message = "The item doesn't exist: " + item;
    }

}

class ArrayIndexOutOfBоundsException extends Exception {
    public ArrayIndexOutOfBоundsException(int idx) {
    }
}

class EmptyOrder extends Exception {

}

class OrderLockedException extends Exception {

}

interface Item {
    int getPrice();
}

class ExtraItem implements Item {

    public String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if (type.equals("Coke") || type.equals("Ketchup"))
            this.type = type;
        else
            throw new InvalidExtraTypeException();
    }

    @Override
    public int getPrice() {
        if (type.equals("Coke"))
            return 5;
        else
            return 3;
    }

    @Override
    public String toString() {
        if (type.equals("Coke"))
            return "Coke";
        else
            return "Ketchup";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ExtraItem extraItem = (ExtraItem) o;
        return Objects.equals(type, extraItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

class PizzaItem implements Item {

    public String type;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if (type.equals("Standard") || type.equals("Pepperoni") || type.equals("Vegetarian"))
            this.type = type;
        else
            throw new InvalidPizzaTypeException();
    }

    @Override
    public int getPrice() {
        if (type.equals("Standard"))
            return 10;
        else if (type.equals("Pepperoni"))
            return 12;
        else
            return 8;
    }

    @Override
    public String toString() {
        if (type.equals("Standard"))
            return "Standard";
        else if (type.equals("Pepperoni"))
            return "Pepperoni";
        else
            return "Vegetarian";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PizzaItem pizzaItem = (PizzaItem) o;
        return Objects.equals(type, pizzaItem.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

class Order implements Item {

    public static int MAX_STOCK = 10;

    public Item[] listItems;

    public int[] numItems;

    public boolean lockOrder;

    public Order() {
        this.listItems = new Item[0];
        this.numItems = new int[0];
        this.lockOrder = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if (lockOrder)
            throw new OrderLockedException();
        if (count > MAX_STOCK)
            throw new ItemOutOfStockException(item);
        // TODO might need to change the code if there need to be removed an old item and replaced with the one in the arguments
        int index = -1;
        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i].equals(item)) {
                index = i;
            }
        }
        if (index != -1) {
            listItems[index] = item;
            numItems[index] = count;
        } else {
            Item[] order = new Item[listItems.length + 1];
            int[] numOrder = new int[numItems.length + 1];
            for (int i = 0; i < listItems.length; i++) {
                order[i] = listItems[i];
            }
            for (int i = 0; i < numItems.length; i++) {
                numOrder[i] = numItems[i];
            }
            order[listItems.length] = item;
            numOrder[numItems.length] = count;
            numItems = numOrder;
            listItems = order;
        }

    }

    @Override
    public int getPrice() {
        int sum = 0;
        for (int i = 0; i < listItems.length; i++)
            sum += listItems[i].getPrice() * numItems[i];
        return sum;
    }

    public void displayOrder() {
        int total = 0;
//        StringBuilder stAdd = new StringBuilder();
        String st, st2 = "Total:";
        for (int i = 0; i < listItems.length; i++) { // - to the left + to the right
            st = String.format("%3d.%-14s x%2d%5d$", i + 1, listItems[i].toString(), numItems[i], listItems[i].getPrice() * numItems[i]);
            System.out.println(st);
//            stAdd.append(st).append("\n");
            total += listItems[i].getPrice() * numItems[i];
        }
        System.out.printf("%-20s%7d$%n", st2, total);
//        return stAdd.toString();
    }

    public void removeItem(int idx) throws ArrayIndexOutOfBоundsException, OrderLockedException {
        if (lockOrder)
            throw new OrderLockedException();
        if (idx < 0 || idx > listItems.length)
            throw new ArrayIndexOutOfBоundsException(idx);
        int counter = 0;
        Item[] removed = new Item[listItems.length - 1];
        int[] removedNumItems = new int[numItems.length - 1];
        for (int i = 0; i < listItems.length; i++) {
            if (i == idx) {
                continue;
            }
            removed[counter++] = listItems[i];
        }
        counter = 0;
        for (int i = 0; i < numItems.length; i++) {
            if (i == idx) {
                continue;
            }
            removedNumItems[counter++] = numItems[i];
        }
        listItems = removed;
        numItems = removedNumItems;
    }

    public void lock() throws EmptyOrder {
        if (listItems.length > 0)
            this.lockOrder = true;
        else
            throw new EmptyOrder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return lockOrder == order.lockOrder && Arrays.equals(listItems, order.listItems) && Arrays.equals(numItems, order.numItems);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lockOrder);
        result = 31 * result + Arrays.hashCode(listItems);
        result = 31 * result + Arrays.hashCode(numItems);
        return result;
    }
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}
