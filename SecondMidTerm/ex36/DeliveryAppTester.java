package mk.kolokvium2.ex36;

import java.util.*;
import java.util.stream.Collectors;

/*
YOUR CODE HERE
DO NOT MODIFY THE interfaces and classes below!!!
*/

class DeliveryApp {
   public String appName;

   public List<Person> listOfPeople;
   public List<Restaurant> listOfRestaurants;

   public DeliveryApp(String appName) {
       this.appName = appName;
       this.listOfPeople = new ArrayList<>();
       this.listOfRestaurants = new ArrayList<>();
   }

   public void registerDeliveryPerson(String id, String name, Location currentLocation) {
       listOfPeople.add(new DeliveryPerson(id, name, currentLocation));
   }

   public void addRestaurant(String id, String name, Location location) {
       listOfRestaurants.add(new Restaurant(id, name, location));
   }

   public void addUser(String id, String name) {
       listOfPeople.add(new User(id, name));
   }

   public void addAddress(String id, String addressName, Location location) {
       for (Person listOfPerson : listOfPeople) {
           if (listOfPerson.id.equalsIgnoreCase(id)) {
               listOfPerson.setLocation(addressName, location);
           }
       }
   }

   public void orderFood(String userId, String userAddressName
           , String restaurantId, float cost) {
       Restaurant restaurant = findRestaurant(restaurantId);
       Deliver(restaurant);
   }

   public void printUsers() {

   }

   public void Deliver(Restaurant restaurant) {
       List<DeliveryPerson> deliveryPersonList = listOfPeople.stream()
               .filter(x -> x.getTypeofPerson() == 'D')
               .map(x -> (DeliveryPerson) x)
               .collect(Collectors.toList());
       DeliveryPerson finalMaxPerson = deliveryPersonList.get(0);
       for (int i = 1; i < deliveryPersonList.size(); i++) {
           if (isCloserToTheRestaraunt(restaurant, deliveryPersonList, i)) {
               if (finalMaxPerson.numDeliveries > deliveryPersonList.get(i).numDeliveries) {
                   finalMaxPerson = deliveryPersonList.get(i);
               }
           } else if (isCloserToTheRestaraunt(restaurant, deliveryPersonList, i, finalMaxPerson)) {
               finalMaxPerson = deliveryPersonList.get(i);
           }
       }
       //It needs to be made "final" i.e it shouldn't be modified after initialisation
       DeliveryPerson finalMaxPerson2 = finalMaxPerson;
       deliveryPersonList.stream().filter(x -> x.name.equalsIgnoreCase(finalMaxPerson2.name)).forEach(x -> {
           x.setLocation("Kiro", restaurant.currentLocation);
           // Kiro is a "dummy" XD
           x.setTotalEarnings(90 + calculateDistance(x.currentLocation, restaurant.currentLocation) * 10);
       });

   }

   private int calculateDistance(Location one, Location two) {
       return Math.abs(one.distance(two));
   }

   private static boolean isCloserToTheRestaraunt(Restaurant restaurant, List<DeliveryPerson> deliveryPersonList, int i) {
       return deliveryPersonList.get(i).currentLocation.getX() == restaurant.currentLocation.getX()
               && deliveryPersonList.get(i).currentLocation.getY() == restaurant.currentLocation.getY();
   }

   private static boolean isCloserToTheRestaraunt(Restaurant restaurant, List<DeliveryPerson> deliveryPersonList, int i, DeliveryPerson maxPerson) {
       return Math.abs(deliveryPersonList.get(i).currentLocation.getX() - restaurant.currentLocation.getX()) < Math.abs(maxPerson.currentLocation.getX() - restaurant.currentLocation.getX())
               && Math.abs(maxPerson.currentLocation.getY() - restaurant.currentLocation.getY()) > Math.abs(deliveryPersonList.get(i).currentLocation.getY() - restaurant.currentLocation.getY());
   }

   public Restaurant findRestaurant(String id) {
       return listOfRestaurants.stream().filter(x -> x.id.equalsIgnoreCase(id)).findFirst().orElse(null);
   }
}

abstract class Person {
   public String id;
   public String name;

   public Person(String id, String name) {
       this.id = id;
       this.name = name;
   }

   abstract char getTypeofPerson();

   abstract void setLocation(String name, Location location);

}

class DeliveryPerson extends Person {
   public Location currentLocation;
   public int totalEarnings;
   public int numDeliveries;

   public DeliveryPerson(String id, String name, Location location) {
       super(id, name);
       this.currentLocation = location;
       this.totalEarnings = 0;
       this.numDeliveries = 0;
   }

   @Override
   char getTypeofPerson() {
       return 'D';
   }

   void setLocation(String name, Location location) {
       this.currentLocation = location;
   } // LoL not work XD

   public void setTotalEarnings(int money) {
       this.totalEarnings += money;
   }
}

class User extends Person {

   public Map<String, List<Location>> userAddress;
   public int totalSpending;

   public User(String id, String name) {
       super(id, name);
       this.userAddress = new HashMap<>();
       this.totalSpending = 0;
   }

   @Override
   char getTypeofPerson() {
       return 'U';
   }

   void setLocation(String name, Location location) {
       userAddress.computeIfAbsent(name, x -> new ArrayList<>()).add(location);
       if (userAddress.containsKey(name))
           userAddress.get(name).add(location);
   }

   public void setTotalSpending(int totalSpending) {
       this.totalSpending += totalSpending;
   }
}

class Restaurant {
   public String id;
   public String name;
   Location currentLocation;

   public Restaurant(String id, String name, Location currentLocation) {
       this.id = id;
       this.name = name;
       this.currentLocation = currentLocation;
   }

   public Restaurant getRestaurant(String name) {
       return new Restaurant(id, name, currentLocation);
   }
}

interface Location {
   int getX();

   int getY();

   default int distance(Location other) {
       int xDiff = Math.abs(getX() - other.getX());
       int yDiff = Math.abs(getY() - other.getY());
       return xDiff + yDiff;
   }
}

class LocationCreator {
   public static Location create(int x, int y) {

       return new Location() {
           @Override
           public int getX() {
               return x;
           }

           @Override
           public int getY() {
               return y;
           }
       };
   }
}

public class DeliveryAppTester {
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       String appName = sc.nextLine();
       DeliveryApp app = new DeliveryApp(appName);
       while (sc.hasNextLine()) {
           String line = sc.nextLine();
           String[] parts = line.split(" ");

           if (parts[0].equals("addUser")) {
               String id = parts[1];
               String name = parts[2];
               app.addUser(id, name);
           } else if (parts[0].equals("registerDeliveryPerson")) {
               String id = parts[1];
               String name = parts[2];
               int x = Integer.parseInt(parts[3]);
               int y = Integer.parseInt(parts[4]);
               app.registerDeliveryPerson(id, name, LocationCreator.create(x, y));
           } else if (parts[0].equals("addRestaurant")) {
               String id = parts[1];
               String name = parts[2];
               int x = Integer.parseInt(parts[3]);
               int y = Integer.parseInt(parts[4]);
               app.addRestaurant(id, name, LocationCreator.create(x, y));
           } else if (parts[0].equals("addAddress")) {
               String id = parts[1];
               String name = parts[2];
               int x = Integer.parseInt(parts[3]);
               int y = Integer.parseInt(parts[4]);
               app.addAddress(id, name, LocationCreator.create(x, y));
           } else if (parts[0].equals("orderFood")) {
               String userId = parts[1];
               String userAddressName = parts[2];
               String restaurantId = parts[3];
               float cost = Float.parseFloat(parts[4]);
               app.orderFood(userId, userAddressName, restaurantId, cost);
           } else if (parts[0].equals("printUsers")) {
               app.printUsers();
           } else if (parts[0].equals("printRestaurants")) {
               app.printRestaurants();
           } else {
               app.printDeliveryPeople();
           }

       }
   }
}

