package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWithCustomeExecutor {

    public int findAccountNumber() {
        System.out.println(Thread.currentThread() + " findAccountNumber");
        sleep(1);
        return 10;
    }

    public int calculateBalance(int accountNumber) {
        System.out.println(Thread.currentThread() + " calculateBalance");
        sleep(1);
        return accountNumber * accountNumber;
    }

    public Integer notifyBalance(Integer balance) {
        System.out.println(Thread.currentThread() + "Sending Notification");
        sleep(1);
        return balance;
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void notify(String str) {
        sleep(1);
        System.out.println(Thread.currentThread() + "Recived vlaue " + str);
    }

    public void completableFutureWithExecutorService() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> completableFuture =
                CompletableFuture.supplyAsync(this::findAccountNumber,newFixedThreadPool)
                        .thenApply(this::calculateBalance)
                        .thenApply(this::notifyBalance);
        Integer balance = completableFuture.join();
        System.out.println(Objects.equals(Integer.valueOf(balance), Integer.valueOf(100)));
        newFixedThreadPool.shutdown();
    }

    public static void main(String [] args) {
        new CompletableFutureWithCustomeExecutor().completableFutureWithExecutorService();
    }
}
