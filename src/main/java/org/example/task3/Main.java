package org.example.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) {
        //List<Integer> list = new CopyOnWriteArrayList<>();
        List<Integer> list = new ArrayList<>();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        FillingList filling1 = new FillingList(list, readWriteLock);
        FillingList filling2 = new FillingList(list, readWriteLock);

        Thread t1 = new Thread(filling1);
        Thread t2 = new Thread(filling2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list.size());
    }
}
