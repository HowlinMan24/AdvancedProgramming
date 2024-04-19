package mk.myLabs.No_7.ex4;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class TermFrequency {
    public Map<String, Integer> wordsWithFrequency;
    public int totalWords;
    public Set<String> uniqueWords;

    public TermFrequency(InputStream in, String[] words) {
        this.wordsWithFrequency = new TreeMap<>();
        this.uniqueWords = new HashSet<>();
        this.totalWords = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        br.lines().forEach(line -> {
            String[] parts = line.split("\\s+");
            List<String> filteredWords = Arrays.stream(parts)
                    .map(x -> x.replace(",", "").replace("\n", "").replace(".", "").toLowerCase())
                    .filter(x -> !Arrays.asList(words).contains(x))
                    .collect(Collectors.toList());
            totalWords += filteredWords.size();
            filteredWords.forEach(word -> {
                if (wordsWithFrequency.containsKey(word)) {
                    int frequency = wordsWithFrequency.get(word);
                    frequency++;
                    wordsWithFrequency.put(word, frequency);
                } else {
                    wordsWithFrequency.put(word, 1);
                }
            });
            uniqueWords.addAll(filteredWords);
        });

    }

//    public boolean isContained(String word, String[] words) {
//        return Arrays.stream(words).anyMatch(x -> x.equalsIgnoreCase(word));
//    }

    public int countTotal() {
        return totalWords;
    }

    public int countDistinct() {
        return uniqueWords.size();
    }

    public List<String> mostOften(int k) {
        return wordsWithFrequency.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[]{"во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја"};
        TermFrequency tf = new TermFrequency(System.in, stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
