package org.example.task4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car implements Runnable{

    private Semaphore semaphore;
    private CountDownLatch countDownLatch;
    private CyclicBarrier cyclicBarrier;

    private String nameCar;
    private long totalTime = 0;

    public Car(Semaphore semaphore, String nameCar) {
        this.semaphore = semaphore;
        this.nameCar = nameCar;
    }

    @Override
    public void run() {

        int maxTime = 1_000;
        stage(maxTime*2);
        System.out.println(nameCar + " готовий!!!");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        long startRace = System.currentTimeMillis();

        stage(maxTime);
        System.out.println(nameCar + " проїхав звичайний участок дороги!");

        if(semaphore.availablePermits() == 0){
            System.out.println(nameCar + " біля тунелю!");
        }

        try {
            semaphore.acquire();
            System.out.println(nameCar + " в тунелі!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stage(maxTime);
        semaphore.release();
        System.out.println(nameCar + " проїхав через тунель!");

        stage(maxTime);

        System.out.println(nameCar + " на фініші!");
        totalTime = System.currentTimeMillis()-startRace;
        countDownLatch.countDown();
    }


    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    private void stage(int maxTime){
        try {
            Thread.sleep((int) (Math.random()*maxTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getNameCar() {
        return nameCar;
    }

    public long getTotalTime() {
        return totalTime;
    }
}