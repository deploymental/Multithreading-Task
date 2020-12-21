package com.motorsport.ru;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

class ServiceClassification {

    void classification(ConcurrentHashMap<Set, LinkedBlockingQueue<String>> map, String line)
            throws InterruptedException {
        Set<String> key = Collections.unmodifiableSet(new HashSet<>(
                Arrays.stream(line.replaceAll("[^0-9\\p{L}]+", " ").trim()
                        .replaceAll(" +", " ")
                        .split(" ")).collect(Collectors.toSet())));
        putLineToQueue(map, line, key);
    }

    private synchronized void putLineToQueue(
            ConcurrentHashMap<Set, LinkedBlockingQueue<String>> map,
            String line,
            Set key
    ) throws InterruptedException {
        if (key.contains("")) {
            return;
        }
        LinkedBlockingQueue<String> blockingQueue = map.get(key);
        if (blockingQueue == null) {
            LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
            queue.put(line);
            map.put(key, queue);
        } else {
            blockingQueue.put(line);
        }
    }

    public static ServiceClassification getInstance() {
        return ServiceClassificationHolder.instance;
    }

    private static class ServiceClassificationHolder {
        private final static ServiceClassification instance = new ServiceClassification();
    }
}