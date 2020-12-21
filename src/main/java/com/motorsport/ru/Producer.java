package com.motorsport.ru;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer implements Runnable {
    private ServiceClassification serviceClassification;
    private ConcurrentHashMap<Set, LinkedBlockingQueue<String>> map;
    private String line;

    public Producer(String line, ConcurrentHashMap<Set, LinkedBlockingQueue<String>> map) {
        this.line = line;
        this.map = map;
        this.serviceClassification = ServiceClassification.getInstance();
    }

    @Override
    public void run() {
        try {
            String s = line;
            System.out.println(s);
            serviceClassification.classification(map, s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}