package mk.kolokvium.updatedEX.ex8;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class NonExistingItemException extends Exception {
    String message;

    public NonExistingItemException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

abstract class Archive {
    public int id;
    public LocalDate dateArchived;

    public Archive(int id) {
        this.id = id;
//        this.dateArchived = dateArchived;
    }

    abstract char getType();

}

class ArchiveStore {

    public List<Archive> archiveList;
    public List<String> archiveDates;

    public ArchiveStore() {
        this.archiveList = new ArrayList<>();
        this.archiveDates = new ArrayList<>();
    }

    void archiveItem(Archive item, LocalDate date) {
        item.dateArchived = date;
        archiveDates.add("Item " + item.id + " archived at " + date);
        archiveList.add(item);
    }

    void openItem(int id, LocalDate date) throws NonExistingItemException {
        boolean flag = true;
        for (Archive archive : archiveList) {
            if (archive.id == id) {
                if (archive.getType() == 'L') {
                    LockedArchive modified = (LockedArchive) archive;
                    if (date.isBefore(modified.dateToOpen)) {
                        archiveDates.add("Item " + id + " cannot be opened before " + modified.dateToOpen);
                        flag = false;
                        break;
                    } else {
                        archiveDates.add("Item " + id + " opened at " + date);
                        flag = false;
                        break;
                    }
                } else {
                    SpecialArchive modified = (SpecialArchive) archive;
                    if (modified.timesOpened + 1 > modified.maxOpen) {
                        archiveDates.add("Item " + id + " cannot be opened more than " + modified.maxOpen + " times");
                        flag = false;
                        break;
                    } else {
                        archiveDates.add("Item " + id + " opened at " + date);
                        modified.timesOpened++;
                        flag = false;
                        break;
                    }
                }
            }
        }
        if (flag) {
            throw new NonExistingItemException(String.format("Item with id %d doesn't exist", id));
        }
    }

    public String getLog() {
        StringBuilder st = new StringBuilder();
        for (String archiveDate : archiveDates) {
            st.append(archiveDate).append("\n");
        }
        return st.toString();
    }
}

class LockedArchive extends Archive {
    public int id;
    public LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.id = id;
        this.dateToOpen = dateToOpen;
    }

    @Override
    char getType() {
        return 'L';
    }
}

class SpecialArchive extends Archive {
    public int id;
    public int maxOpen;
    public int timesOpened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.id = id;
        this.maxOpen = maxOpen;
        this.timesOpened = 0;
    }

    @Override
    char getType() {
        return 'S';
    }
}


public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}