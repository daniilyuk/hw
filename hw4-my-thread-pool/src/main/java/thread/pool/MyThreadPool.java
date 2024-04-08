package thread.pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyThreadPool {
    private int capacity;
    private List<MyThread> threads;
    private LinkedList<Runnable> tasks;
    private boolean isShutdown;

    public MyThreadPool(int capacity) {
        this.capacity = capacity;
        this.threads = new ArrayList<>(capacity);
        this.tasks = new LinkedList<>();
        this.isShutdown = false;

        for (int i = 0; i < this.capacity; i++) {
            MyThread thread = new MyThread();
            thread.start();
            threads.add(thread);
        }
    }

    void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException();
        }
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    void shutdown() {
        isShutdown = true;
        for (MyThread thread : threads) {
            thread.interrupt();
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown) {
                Runnable task;
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = tasks.poll();
                }
                task.run();
            }
        }
    }
}
