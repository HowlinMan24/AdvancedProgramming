package mk.kolokvium.updatedEX.ex16;


import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class AmountNotAllowedException extends Exception {
    public int sum;

    public AmountNotAllowedException(int sum) {
        this.sum = sum;
    }

    public String getMessage() {
        return "Receipt with amount " + sum + " is not allowed to be scanned";
    }
}

class MojDDV {

    public List<Account> accountList;

    public MojDDV() {
        this.accountList = new ArrayList<>();
    }

    void readRecords(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            String id = parts[0];
            List<Item> items = new ArrayList<>();
            for (int i = 1; i < parts.length; i++) {
                if (parts[i].equals("A") || parts[i].equals("B") || parts[i].equals("V")) {
                    items.add(new Item(Integer.parseInt(parts[i - 1]), parts[i]));
                }
            }
            try {
                accountList.add(new Account(id, items));
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }
//            System.out.println(accountList);
        });
    }

    void printTaxReturns(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        accountList.forEach(x -> pw.println(x.toString()));
        pw.flush();
//        pw.close();
    }

    void printStatistics(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        StringBuilder st = new StringBuilder();
        st.append("min:\t")
                .append(String.format("%.3f", accountList.stream().mapToDouble(Account::taxReturnSum).min().orElse(0.0f)))
                .append("\nmax:\t")
                .append(String.format("%.3f", accountList.stream().mapToDouble(Account::taxReturnSum).max().orElse(0.0f)))
                .append("\nsum:\t")
                .append(String.format("%.3f", accountList.stream().mapToDouble(Account::taxReturnSum).sum()))
                .append("\ncount:\t").append(accountList.size())
                .append("\navg:\t")
                .append(String.format("%.3f", accountList.stream().mapToDouble(Account::taxReturnSum).sum() / accountList.size()));
        pw.println(st);
        pw.flush();
        pw.close();
    }

}

class Account {
    public String id;
    public List<Item> itemList;
    public static float DDV = 0.15f;

    public Account(String id, List<Item> itemList) throws AmountNotAllowedException {
        this.id = id;
        this.itemList = itemList;
        if (sumItemPrices() > 30000)
            throw new AmountNotAllowedException(sumItemPrices());
    }

    public int sumItemPrices() {
        return itemList.stream().mapToInt(x -> x.price).sum();
    }

    public float taxReturnSum() {
        return (float) itemList.stream().mapToDouble(x -> x.price * x.taxes * DDV).sum();
    }

//    public float minTaxReturn() {
//        return (float) itemList.stream().mapToDouble(x -> x.).min().orElse(0);
//    }
//
//    public float maxTaxReturn() {
//        return (float) itemList.stream().mapToDouble(x -> x.price * x.taxes * DDV).max().orElse(0);
//    }

    @Override
    public String toString() {
        return String.format("    %6s\t     %5s\t %2.5f", id, String.valueOf(sumItemPrices()), taxReturnSum());
    }
}

class Item {
    public int price;
    public Type type;
    public float taxes;

    public Item(int price, String type) {
        this.price = price;
        if (type.equals("A")) {
            taxes = 0.18f;
            this.type = Type.A;
        } else if (type.equals("B")) {
            taxes = 0.05f;
            this.type = Type.B;
        } else {
            taxes = 0.0f;
            this.type = Type.V;
        }
    }
}

enum Type {A, B, V}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}
