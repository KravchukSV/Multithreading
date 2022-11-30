package org.example.task4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
    public static String[] colors = {"\u001B[0m", "\u001B[30m", "\u001B[31m", "\u001B[32m",
    "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[37m", "\u001B[1;30m"};

    public static void main(String[] args) {

        int numCars = 10;
        Semaphore tunnel = new Semaphore(3);
        CountDownLatch finish = new CountDownLatch(numCars);
        CyclicBarrier readeRace = new CyclicBarrier(numCars);

        List<Car> cars = creatCars(numCars, tunnel, finish, readeRace);

        startRace(cars);

        try {
            finish.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printInfoRace(cars);

    }

    static List<Car> creatCars(int size, Semaphore semaphore, CountDownLatch cdl, CyclicBarrier cb)
    {
        List<Car> cars = new ArrayList<>();

        for(int i = 0; i < size; i++){
            Car car = new Car(semaphore, colors[i] + "Car" + (i+1));
            car.setCountDownLatch(cdl);
            car.setCyclicBarrier(cb);
            cars.add(car);
        }

        return cars;
    }

    static void startRace(List<Car> cars){
        for(Car car:cars){
            new Thread(car).start();
        }
    }

    static void printInfoRace(List<Car> cars){
        Collections.sort(cars, Comparator.comparing(Car::getTotalTime));
        for(Car car:cars){
            System.out.println(car.getNameCar() + ": " + car.getTotalTime());
        }
    }
}