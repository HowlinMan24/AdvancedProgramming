package mk.kolokvium.updatedEX.ex15;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class AmountNotAllowedException extends Exception {
    public int sum;

    public AmountNotAllowedException(int sum) {
        this.sum = sum;
    }

    public String getMessage() {
        return "Receipt with amount " + sum + " is not allowed to be scanned";
    }
}

class Account {
    public int id;
    public List<Item> itemList;

    public static double DDV = 0.15f;

    public Account(int id, List<Item> itemList) {
        this.id = id;
        this.itemList = itemList;
    }

    public static Account create(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        int id = Integer.parseInt(parts[0]);
        List<Item> itemList2 = new ArrayList<>();

        for (int i = 1; i < parts.length; i++) {
            if (Objects.equals(parts[i], "A") || Objects.equals(parts[i], "B") || Objects.equals(parts[i], "V")) {
                itemList2.add(new Item(Integer.parseInt(parts[i - 1]), parts[i]));
            }
        }
        int sum = itemList2.stream().mapToInt(i -> i.price).sum();
        if (sum > 30000) {
            throw new AmountNotAllowedException(sum);
        } else {
            return new Account(id, itemList2);
        }
    }

    @Override
    public String toString() {
        return String.format("%d %d %.2f", id, sumAmounts(), taxReturn());
    }

    public int sumAmounts() {
        return itemList.stream().mapToInt(i -> i.price).sum();
    }

    public double taxReturn() {
        return itemList.stream().mapToDouble(i -> i.taxes * i.price * DDV).sum();
    }

}

enum Type {A, B, V}

class Item {
    public int price;
    public double taxes;
    public Type type;

    public Item(int price, String typee) {
        this.price = price;
        if (typee.equals("A")) {
            this.taxes = 0.18f;
            type = Type.A;
        } else if (typee.equals("B")) {
            type = Type.B;
            this.taxes = 0.05f;
        } else if (typee.equals("V")) {
            type = Type.V;
            this.taxes = 0.0f;
        }
    }

}

class MojDDV {
    public List<Account> accountList;


    public MojDDV() {
        this.accountList = new ArrayList<>();
    }

    void readRecords(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            try {
//                Account.create(line);
                accountList.add(Account.create(line));
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        });
        br.close();
    }

    void printTaxReturns(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        accountList.forEach(x -> pw.println(x.toString()));
        pw.flush();
        pw.close();
    }

}


public class MojDDVTest {

    public static void main(String[] args) throws IOException {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}