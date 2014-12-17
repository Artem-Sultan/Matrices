package ru.sbt.practice.matrices.Product;

import ru.sbt.practice.matrices.Containers.TripleImpl;
import ru.sbt.practice.matrices.Matrix;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by artem on 26.11.14.
 */
public class ProductWorker implements Runnable {
    private Matrix first;
    private Matrix second;
    private int index;
    private int nIndexes;


    private int lineIndexStart;
    private int lineIndexEnd; // not included
    private int columnIndexStart;
    private int columnIndexEnd;
    private AtomicInteger flag;

    private ArrayBlockingQueue<TripleImpl> cash;

    public ProductWorker(Matrix first, Matrix second, int index, int nIndexes, ArrayBlockingQueue<TripleImpl> queue, AtomicInteger flag) {
        this.flag = flag;
        this.first = first;
        this.second = second;
        this.index = index;
        this.nIndexes = nIndexes;
        int linePace = first.getNumberOfLines()/nIndexes;
        int columnPace = second.getNumberOfColumns()/nIndexes;
        cash = queue;

        lineIndexStart = (linePace)*index;
        if (index == nIndexes-1) lineIndexEnd = first.getNumberOfLines();
        else lineIndexEnd = lineIndexStart + linePace;
        columnIndexStart = (columnPace)*index;
        if (index == nIndexes-1) columnIndexEnd = second.getNumberOfColumns();
        else columnIndexEnd = columnIndexStart + columnPace;
    }

    @Override
    public void run() {
        //System.out.println(index + " started");
        for (int i = lineIndexStart; i < lineIndexEnd; i++) {
            for (int j = columnIndexStart; j < columnIndexEnd; j++) {
                try {
                    cash.put(new TripleImpl(i, j, first.getLine(i).scalarProductWith(second.getColumn(j))));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
       flag.incrementAndGet();
    }

}


//java concurrency in practice

