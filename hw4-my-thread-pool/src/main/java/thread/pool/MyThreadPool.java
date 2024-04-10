package thread.pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyThreadPool {
    private final int capacity;
    private final List<MyThread> threads;
    private final LinkedList<Runnable> tasks;
    private boolean isShutdown;
    private final Object lock = new Object();

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
        synchronized (lock) {
            if (isShutdown) {
                throw new IllegalStateException();
            }
            tasks.add(task);
            lock.notify();
        }
    }

    void shutdown() {
        synchronized (lock) {
            isShutdown = true;
            lock.notifyAll();
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (lock) {
                    while (tasks.isEmpty() && !isShutdown) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (isShutdown && tasks.isEmpty()) {
                        break;
                    }
                    task = tasks.poll();
                }
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


