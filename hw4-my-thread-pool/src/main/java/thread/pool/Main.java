package thread.pool;

public class Main {
    public static void main(String[] args) {
        MyThreadPool myThreadPool=new MyThreadPool(10);
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            myThreadPool.execute(() -> System.out.println("Task "+ finalI +
                    " is completed by "+Thread.currentThread().getName()));
        }

        myThreadPool.shutdown();
    }
}