package mk.kolokvium2.ex13;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Word vectors test
 */

class WordVectors {
    public Map<String, List<Integer>> vectorsMapping;
    public List<String> listOfWords;

    public WordVectors(String[] word, List<List<Integer>> vectors) {
        this.vectorsMapping = new HashMap<>();
        IntStream.range(0, word.length).forEach(i -> vectorsMapping.put(word[i], vectors.get(i)));
    }

    public void readWords(List<String> wordsList) {
        this.listOfWords = wordsList;
    }

    public List<Integer> slidingWindow(int n) {
        List<List<Integer>> listOfIntegers = new ArrayList<>();
        listOfWords.forEach(word -> {
            if (vectorsMapping.containsKey(word)) {
                listOfIntegers.add(vectorsMapping.get(word));
            } else {
                listOfIntegers.add(List.of(5, 5, 5, 5, 5));
            }
        });
        List<Integer> maxList = new ArrayList<>();
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < listOfIntegers.size()-n; i++) {
            List<Integer> windowSum = new ArrayList<>(Collections.nCopies(5, 0));
            for (int j = i; j < i + n; j++) {
                List<Integer> current = listOfIntegers.get(j);
                for (int k = 0; k < 5; k++) {
                    windowSum.set(k, windowSum.get(k) + current.get(k));
                }
            }
            if (maxList.stream().mapToInt(Integer::intValue).sum() > maxSum) {
                maxSum = maxList.stream().mapToInt(Integer::intValue).sum();
                maxList = new ArrayList<>(windowSum);
            }
        }

//        for (int i = 0; i < listOfIntegers.size()-2; i++) {
//            List<Integer> list1=listOfIntegers.get(i);
//            List<Integer> list2=listOfIntegers.get(i);
//            List<Integer> list3=listOfIntegers.get(i);
//        }
        return maxList;
    }


}

public class WordVectorsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] words = new String[n];
        List<List<Integer>> vectors = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");
            words[i] = parts[0];
            List<Integer> vector = Arrays.stream(parts[1].split(":"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            vectors.add(vector);
        }
        n = scanner.nextInt();
        scanner.nextLine();
        List<String> wordsList = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            wordsList.add(scanner.nextLine());
        }
        WordVectors wordVectors = new WordVectors(words, vectors);
        wordVectors.readWords(wordsList);
        n = scanner.nextInt();
        List<Integer> result = wordVectors.slidingWindow(n);
        System.out.println(result.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        scanner.close();
    }
}



