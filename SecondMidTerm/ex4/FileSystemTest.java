package mk.kolokvium2.ex4;

import java.nio.file.LinkOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */

class FileSystem {

    public Map<Character, List<File>> foldersMap;
    public List<File> folderList;

    public FileSystem() {
        this.foldersMap = new TreeMap<>();
//        this.folderList = new ArrayList<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
        foldersMap.computeIfAbsent(folder, x -> new ArrayList<>()).add(new File(name, size, createdAt));
//        folderList.add(new File(folder, name, size, createdAt));
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        return foldersMap.entrySet().stream()
                .flatMap(x -> x.getValue().stream())
                .filter(x -> x.name.charAt(0) == '.')
                .filter(x -> x.size < size)
                .collect(Collectors.toList());
//        return folderList.stream()
//                .filter(x -> x.name.charAt(0) == '.')
//                .filter(x -> x.size < size)
//                .sorted()
//                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {
        return folders.stream()
                .filter(x -> foldersMap.containsKey(x))
                .mapToInt(y -> foldersMap.get(y).stream().mapToInt(z -> z.size).sum())
                .sum();
    }

    public Map<Integer, Set<File>> byYear() {
        return foldersMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        x -> x.dateAndTimeCreated.getYear(),
                        Collectors.toSet()
                ));
    }

    public Map<String, Long> sizeByMonthAndDay() {
        Map<String, Long> stringLongMap = new HashMap<>();
        foldersMap.values().stream()
                .flatMap(Collection::stream)
                .forEach(x -> {
                    stringLongMap.put(((x.dateAndTimeCreated.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase() + "-" + x.dateAndTimeCreated.getDayOfMonth()).toUpperCase()), (long) x.size);
                });
        return stringLongMap;
    }
}

class File implements Comparable<File> {
    public String name;
    public int size;
    //    public char folder;
    public LocalDateTime dateAndTimeCreated;

    public File(String name, int size, LocalDateTime dateAndTimeCreated) {
        this.name = name;
//        this.folder = folder;
        this.size = size;
        this.dateAndTimeCreated = dateAndTimeCreated;
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB ", name, size) + dateAndTimeCreated;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public LocalDateTime getDateAndTimeCreated() {
        return dateAndTimeCreated;
    }

    @Override
    public int compareTo(File o) {
        int dateComparison = this.dateAndTimeCreated.compareTo(o.dateAndTimeCreated);
        if (dateComparison != 0) {
            return dateComparison;
        }

        int nameComparison = this.name.compareTo(o.name);
        if (nameComparison != 0) {
            return nameComparison;
        }

        return Integer.compare(this.size, o.size);
    }
}

public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here


