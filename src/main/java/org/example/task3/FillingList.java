package org.example.task3;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;

public class FillingList implements Runnable{
    private List<Integer> list;
    private Random random = new Random();
    private ReadWriteLock readWriteLock;

    public FillingList(List<Integer> list, ReadWriteLock readWriteLock) {
        this.list = list;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        for(int i = 0; i < 100; i++){
            readWriteLock.writeLock().lock();
            list.add(random.nextInt(200));
            readWriteLock.writeLock().unlock();
        }
    }
}
