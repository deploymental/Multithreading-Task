package com.motorsport.ru;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ReadFileService readFileService = new ReadFileService();
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        readFileService.readFileAndPutInQueue(queue);
        ConcurrentHashMap<Set, LinkedBlockingQueue<String>> map = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        System.out.println(queue.size());
        for (String s : queue) {
            executorService.execute(new Producer(s, map));
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println(map);
    }
}