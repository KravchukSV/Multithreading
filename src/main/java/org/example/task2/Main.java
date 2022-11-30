package org.example.task2;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name number(0-2): ");
        int index = scanner.nextInt();

        Callable<String> callable = new PrinterName(index);
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread threadPrinter = new Thread(futureTask);
        threadPrinter.start();

        Thread demon = new Thread(()->{
            while (true){
                System.out.print(".");
            }
        });

        demon.setDaemon(true);
        demon.start();

        try {
            System.out.println('\n' + futureTask.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
