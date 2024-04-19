//package mk.kolokvium.updatedEX.ex20;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * I Partial exam 2016
// */
//public class TaskSchedulerTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        Task[] timeTasks = new Task[n];
//        for (int i = 0; i < n; ++i) {
//            int x = scanner.nextInt();
//            timeTasks[i] = new TimedTask(x);
//        }
//        n = scanner.nextInt();
//        Task[] priorityTasks = new Task[n];
//        for (int i = 0; i < n; ++i) {
//            int x = scanner.nextInt();
//            priorityTasks[i] = new PriorityTask(x);
//        }
//        Arrays.stream(priorityTasks).forEach(System.out::println);
//        TaskRunner<Task> runner = new TaskRunner<>();
//        System.out.println("=== Ordered tasks ===");
//        System.out.println("Timed tasks");
//        runner.run(Schedulers.getOrdered(), timeTasks);
//        System.out.println("Priority tasks");
//        runner.run(Schedulers.getOrdered(), priorityTasks);
//        int filter = scanner.nextInt();
////        System.out.printf("=== Filtered time tasks with order less then %d ===\n", filter);
////        runner.run(Schedulers.getFiltered(filter), timeTasks);
////        System.out.printf("=== Filtered priority tasks with order less then %d ===\n", filter);
////        runner.run(Schedulers.getFiltered(filter), priorityTasks);
//        scanner.close();
//    }
//}
//
//class TaskRunner<Task> {
//    public void run(TaskScheduler<Task> scheduler, Task[] tasks) {
//        List<Task> order = scheduler.schedule(tasks);
//        order.forEach(System.out::println);
//    }
//}
//
//interface TaskScheduler<Task> {
//    List<Task> schedule(Task[] tasks);
//    //TODO for sorting
//}
//
//interface Task {
//    //dopolnete ovde
//    int getOrder();
//}
//
//class PriorityTask implements Task {
//    private final int priority;
//
//    public PriorityTask(int priority) {
//        this.priority = priority;
//    }
//
//
//    @Override
//    public String toString() {
//        return String.format("PT -> %d", getOrder());
//    }
//
//    @Override
//    public int getOrder() {
//        return priority;
//    }
//}
//
//class TimedTask implements Task {
//    private final int time;
//
//    public TimedTask(int time) {
//        this.time = time;
//    }
//
//
//    @Override
//    public String toString() {
//        return String.format("TT -> %d", getOrder());
//    }
//
//    @Override
//    public int getOrder() {
//        return time;
//    }
//}
//
//class Schedulers {
//    public static List<Task> taskList = new ArrayList<>();
//
//    static <Task extends Comparable<Task>> TaskScheduler<Task> getOrdered() {
//
//        // vashiot kod ovde (annonimous class)
//
//        TaskScheduler<Task> tast = new TaskScheduler<Task>() {
//            @Override
//            public List<Task> schedule(Task[] tasks) {
//                List<Task> tasks1 = new ArrayList<>(Arrays.asList(tasks));
//                Collections.sort(tasks1);
//                return tasks1;
//            }
//
//        };
//        return tast;
//
//    }
//
//    public static List<Task> taskList2 = new ArrayList<>();
//
////    static <T extends Comparable<T>> TaskScheduler<T> getFiltered(int order) {
////
////        // vashiot kod ovde (lambda expression)
////
////
////    }
//}