package mk.myLabs.No_7.ex2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
        // Vasiod kod ovde
        Map<String, List<String>> listOfAnagrams = new TreeMap<>();
        Scanner sc = new Scanner(inputStream);
        String firstLine = sc.nextLine();
        listOfAnagrams.put(firstLine, new ArrayList<>());
        while (sc.hasNextLine()) {
            String inputLine = sc.nextLine();
            if (!containsLetters(inputLine, listOfAnagrams)) {
                listOfAnagrams.entrySet().stream().filter(x -> isAnagram(x.getKey(), inputLine)).forEach(x -> x.getValue().add(inputLine));
                if (listOfAnagrams.values().stream().noneMatch(x -> x.contains(inputLine))) {
                    listOfAnagrams.put(inputLine, new ArrayList<>());
                }
            } else {
                listOfAnagrams.put(inputLine, new ArrayList<>());
            }
        }
        listOfAnagrams.forEach((key, value) -> {
            System.out.print(key + " ");
            value.forEach(y -> System.out.print(y + " "));
            System.out.println();
        });
    }

    private static boolean containsLetters(String inputLine, Map<String, List<String>> listOfAnagrams) {
        return listOfAnagrams.containsKey(inputLine);
    }

    private static boolean isAnagram(String key, String inputLine) {
        char[] array = key.toCharArray();
        char[] array2 = inputLine.toCharArray();
        Arrays.sort(array);
        Arrays.sort(array2);
        return Arrays.equals(array, array2);
    }


//    private static boolean isAnagram(String key, String inputLine) {
//        boolean flag = false;
//        if (key.length() != inputLine.length())
//            return false;
//        for (int i = 0; i < inputLine.length(); i++) {
//            flag = false;
//            for (int j = 0; j < key.length(); j++) {
//                if (inputLine.charAt(i) == key.charAt(j)) {
//                    flag = true;
//                    break;
//                }
//            }
//            if (!flag)
//                return false;
//        }
//        return flag;
//    }
}

