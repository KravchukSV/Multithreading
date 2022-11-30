package org.example.task2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class PrinterName implements Callable<String> {
    private List<String> listName = Arrays.asList("Jon", "Sam", "Adam");
    private int indexName;

    public PrinterName(int indexName) {
        this.indexName = indexName;
    }

    @Override
    public String call() throws Exception {
        String name = listName.get(indexName);
        Thread.sleep(5_000);
        return name;
    }
}
