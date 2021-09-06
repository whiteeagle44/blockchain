package blockchain;

import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        Blockchain blockchain = new Blockchain(0);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Callable<Block>> tasks = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            tasks.add(blockchain::add);
        }

        try {
            executor.invokeAny(tasks);
            executor.shutdown();
            boolean terminated = executor.awaitTermination(8, TimeUnit.SECONDS);
            if (terminated) {
                System.out.println("Blockchain gneration finished in the expected time.");
            } else {
                System.out.println("Blockchain generation takes longer than expected.");
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
        if (blockchain.verify()) {
            System.out.println("Blockchain is valid.");
        } else {
            System.out.println("Blockchain is not valid.");
        }
    }
}
