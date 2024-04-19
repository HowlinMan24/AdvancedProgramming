package mk.kolokvium2.ex3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

// Vashiot kod ovde

class Discounts {
    public List<Store> storeList;

    public Discounts() {
        this.storeList = new ArrayList<>();
    }

    public int readStores(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            String storeName = parts[0];
            storeList.add(new Store(storeName, Arrays.stream(parts).skip(1).collect(Collectors.toList())));
        });
//        System.out.println(storeList);
        return storeList.size();
    }

    public List<Store> byAverageDiscount() {
        return storeList.stream()
                .sorted(Comparator.comparing(Store::averageDiscount).thenComparing(Store::getStoreName).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount() {
        return storeList.stream()
                .sorted(Comparator.comparing(Store::totalDiscount).thenComparing(Store::averageDiscount))
                .limit(3)
                .collect(Collectors.toList());
    }
}

class Store {
    public String storeName;
    public Map<Double, Double> pricesMap = new HashMap<>();

    public Store(String storeName, List<String> prices) {
        this.storeName = storeName;
        prices.forEach(line -> {
            String[] parts = line.split(":");
            pricesMap.put(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
        });
    }

    public String getStoreName() {
        return storeName;
    }

    public double averageDiscount() {
        return pricesMap
                .entrySet()
                .stream()
                .mapToDouble(x -> Math.floor(((x.getValue() - x.getKey()) / x.getValue()) * 100))
                .sum() / pricesMap.size();
    }

    public double totalDiscount() {
        return pricesMap.entrySet()
                .stream()
                .mapToDouble(x -> Math.abs(x.getValue() - x.getKey()))
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#");
        st.append(storeName).append("\n");
        st.append(String.format("Average discount: %.1f%%\n", averageDiscount()));
        st.append(String.format("Total discount: %s\n", decimalFormat.format(totalDiscount())));
        pricesMap.entrySet()
                .stream()
                .sorted((x, y) ->
                        Double.compare(
                                Math.floor(((y.getValue() - y.getKey()) / y.getValue()) * 100),
                                Math.floor(((x.getValue() - x.getKey()) / x.getValue()) * 100)
                        ))
                .forEach(entry -> st.append(String.format("%s%% %s/%s", decimalFormat.format(Math.floor(((entry.getValue() - entry.getKey()) / entry.getValue()) * 100)),
                        decimalFormat.format(entry.getKey()), decimalFormat.format(entry.getValue()))));

//        pricesMap.entrySet()
//                .stream()
//                .sorted(Comparator.comparing(x -> Math.ceil(((x.getValue() - x.getKey()) / x.getValue()) * 100)))
//                .forEach(entry -> st.append(String.format("%s%% %s/%s", decimalFormat.format(Math.ceil(((entry.getValue() - entry.getKey()) / entry.getValue()) * 100)), decimalFormat.format(entry.getKey()), decimalFormat.format(entry.getValue()))).append("\n"));
        return st.toString();
    }

}