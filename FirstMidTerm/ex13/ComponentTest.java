//package mk.kolokvium.updatedEX.ex13;
//
//import java.util.*;
//
//class Component implements Comparable<Component> {
//    public String color;
//    public int weight;
//    public List<Component> listComponents;
//    public String dashes;
//
//    public Component(String color, int weight) {
//        this.color = color;
//        this.weight = weight;
//        this.listComponents = new ArrayList<>();
//        this.dashes = "---";
//    }
//
//    void addComponent(Component component) {
//        listComponents.add(component);
//        Collections.sort(listComponents);
//    }
//
//    @Override
//    public int compareTo(Component o) {
//        return Integer.compare(this.weight, o.weight);
//    }
//
//    public String getString(String extra) {
//        return String.format("%s%d:%s", dashes + extra, weight, color);
//    }
//
//}
//
//class Window {
//    public String name;
//    public List<Component> components;
//
//    public Window(String name) {
//        this.name = name;
//        this.components = new ArrayList<>();
//    }
//
//    public void addComponent(int position, Component component) {
//
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder st = new StringBuilder();
//        st.append("WINDOW " + name);
//        for (int i = 0; i < components.size(); i++) {
//            st.append(i + 1).append(components.get(i).getString("---")).append("\n");
//        }
//        return st.toString();
//    }
//}
//
//public class ComponentTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String name = scanner.nextLine();
//        Window window = new Window(name);
//        Component prev = null;
//        while (true) {
//            try {
//                int what = scanner.nextInt();
//                scanner.nextLine();
//                if (what == 0) {
//                    int position = scanner.nextInt();
//                    window.addComponent(position, prev);
//                } else if (what == 1) {
//                    String color = scanner.nextLine();
//                    int weight = scanner.nextInt();
//                    Component component = new Component(color, weight);
//                    prev = component;
//                } else if (what == 2) {
//                    String color = scanner.nextLine();
//                    int weight = scanner.nextInt();
//                    Component component = new Component(color, weight);
//                    prev.addComponent(component);
//                    prev = component;
//                } else if (what == 3) {
//                    String color = scanner.nextLine();
//                    int weight = scanner.nextInt();
//                    Component component = new Component(color, weight);
//                    prev.addComponent(component);
//                } else if (what == 4) {
//                    break;
//                }
//
//            } catch (InvalidPositionException e) {
//                System.out.println(e.getMessage());
//            }
//            scanner.nextLine();
//        }
//
//        System.out.println("=== ORIGINAL WINDOW ===");
//        System.out.println(window);
//        int weight = scanner.nextInt();
//        scanner.nextLine();
//        String color = scanner.nextLine();
//        window.changeColor(weight, color);
//        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
//        System.out.println(window);
//        int pos1 = scanner.nextInt();
//        int pos2 = scanner.nextInt();
//        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
//        window.swichComponents(pos1, pos2);
//        System.out.println(window);
//    }
//}
//
//// вашиот код овде
