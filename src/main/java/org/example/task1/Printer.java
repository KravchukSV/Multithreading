package org.example.task1;

import java.util.ArrayList;
import java.util.List;

public class Printer implements Runnable{

    private static Object monitor = new Object();
    private static char currentSymbol;
    private static List<Character> characters = new ArrayList<>();
    private char symbol;
    private static int nextSymbol;

    public Printer(char symbol) {
        this.symbol = symbol;
        characters.add(symbol);
    }

    public static void setStartSymbol(char startSymbol) {
        Printer.currentSymbol = startSymbol;
        nextSymbol = characters.indexOf(startSymbol);
    }

    @Override
    public void run() {

            for(int i = 0; i < 5; i++){
                synchronized (monitor){
                    try {
                        while (currentSymbol != symbol){
                            monitor.wait();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.print(symbol);
                    currentSymbol = getNextSymbol();
                    monitor.notifyAll();
                }
            }
    }

    private char getNextSymbol(){
        if(++nextSymbol >= characters.size()){
            nextSymbol = 0;
        }
        return characters.get(nextSymbol);
    }
}