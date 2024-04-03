package stream.api;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Task> tasks = List.of(
                new Task(1, "Task1", Status.OPEN),
                new Task(2, "Task2", Status.OPEN),
                new Task(3, "Task3", Status.CLOSED),
                new Task(4, "Task4", Status.AT_WORK),
                new Task(5, "Task5", Status.CLOSED),
                new Task(6, "Task6", Status.OPEN));

        System.out.println(tasksByStatus(tasks, Status.OPEN));
        System.out.println(taskIsPresent(tasks, 4));
        System.out.println(tasksSortedByStatus(tasks));
        System.out.println(tasksCountByStatus(tasks, Status.AT_WORK));
    }

    private static List<Task> tasksByStatus(List<Task> tasks, Status status) {
        return tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .toList();
    }

    private static boolean taskIsPresent(List<Task> tasks, int id) {
        return tasks.stream()
                .anyMatch(task -> task.getId() == id);
    }

    private static List<Task> tasksSortedByStatus(List<Task> tasks) {
        return tasks.stream()
                .sorted((task1, task2)->task2.getStatus().compareTo(task1.getStatus()))
                .toList();
    }

    private static long tasksCountByStatus(List<Task> tasks, Status status) {
        return tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .count();
    }
}
