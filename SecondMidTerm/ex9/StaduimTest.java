package mk.kolokvium2.ex9;

import java.util.*;


class SeatTakenException extends Exception {
    public SeatTakenException() {
    }
}

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException() {
    }
}

class Stadium {
    public String nameStadium;
    public Map<String, Sector> mapOfSectors;

    public Stadium(String nameStadium) {
        this.nameStadium = nameStadium;
        this.mapOfSectors = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sectorSizes) {
        for (int i = 0; i < sectorNames.length; i++) {
//            mapOfSectors.computeIfAbsent(sectorNames[i],x -> new Sector(sectorNames[i], sectorSizes[i]));
            mapOfSectors.put(sectorNames[i], new Sector(sectorNames[i], sectorSizes[i]));
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException {
        Sector sector = mapOfSectors.get(sectorName);
        sector.setSeat(sectorName, type, seat);
    }

    public void showSectors() {
        mapOfSectors.values()
                .stream()
                .sorted(Comparator.comparing(Sector::getNumberOfFreeSeats).thenComparing(Sector::getNameOfSector))
                .forEach(sector -> {
                    System.out.printf("%s\t%d/%d\t%.1f%%%n", sector.nameOfSector, sector.numberOfFreeSeats, sector.totalNumberOfSeats, (float) (sector.totalNumberOfSeats - sector.numberOfFreeSeats));
                });
    }
}

class Sector {
    public String nameOfSector;
    public int totalNumberOfSeats;
    public int numberOfFreeSeats;
    public Map<Integer, Integer> seatsOnSector;
    // key -> ( 0 - neutral , 1 - home, 2 - away )

    public Sector(String nameOfSector, int numberOfSeats) {
        this.nameOfSector = nameOfSector;
        this.totalNumberOfSeats = numberOfSeats;
        this.numberOfFreeSeats = numberOfSeats;
        this.seatsOnSector = new HashMap<>();
    }

    public void setSeat(String sectorName, int type, int seat) throws SeatTakenException, SeatNotAllowedException {
        if (seatsOnSector.containsValue(seat)) {
            throw new SeatTakenException();
        }

        int existingType = seatsOnSector.getOrDefault(seat, -1);

        if (existingType != -1) {
            throw new SeatTakenException();
        }

        if (type == 0) {
            if ((seatsOnSector.containsKey(1) || seatsOnSector.containsKey(2))) {
                throw new SeatNotAllowedException();
            }
        } else if (type == 1) {
            if (seatsOnSector.containsKey(2)) {
                throw new SeatNotAllowedException();
            }
        } else if (type == 2) {
            if (seatsOnSector.containsKey(1)) {
                throw new SeatNotAllowedException();
            }
        } else {
            throw new IllegalArgumentException("Invalid seat type: " + type);
        }

        seatsOnSector.put(seat, type);
        numberOfFreeSeats--;
    }


    public String getNameOfSector() {
        return nameOfSector;
    }

    public int getTotalNumberOfSeats() {
        return totalNumberOfSeats;
    }

    public int getNumberOfFreeSeats() {
        return numberOfFreeSeats;
    }
}

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}

