package org.example.task1;

public class Main {
    public static void main(String[] args) {
        test();
    }
    public static void test(){
        Thread threadA = new Thread(new Printer('A'));
        Thread threadB = new Thread(new Printer('B'));
        Thread threadC = new Thread(new Printer('C'));

        Printer.setStartSymbol('A');

        threadA.start();
        threadB.start();
        threadC.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
