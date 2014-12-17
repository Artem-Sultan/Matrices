package ru.sbt.practice.matrices.Product;

import ru.sbt.practice.matrices.Containers.TripleImpl;
import ru.sbt.practice.matrices.Matrix;
import ru.sbt.practice.matrices.MatrixFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by artem on 26.11.14.
 */
public class ProductMaker {
    private final int nThreads;


    public ProductMaker() {
        nThreads = Runtime.getRuntime().availableProcessors();
    }

    public ProductMaker(int nThreads) {
        this.nThreads = nThreads;
    }

    public Matrix product (Matrix first, Matrix second, Class resultClass) {
        if (first.getNumberOfColumns()!=second.getNumberOfLines()) throw  new IllegalArgumentException("Dimensions don't match!");
        int nLines = first.getNumberOfLines();
        int nColumns = second.getNumberOfColumns();

        Matrix result = MatrixFactory.create(resultClass, nLines, nColumns);

        Productor mainProductor = new Productor(first,second,result,0,nLines-1,0,nColumns-1);
        ForkJoinPool pool = new ForkJoinPool(nThreads);
        pool.invoke(mainProductor);
        return result;
    }

    public Matrix optProduct (Matrix first, Matrix second, Class resultClass) {
        if (first.getNumberOfColumns() != second.getNumberOfLines())
            throw new IllegalArgumentException("Dimensions don't match!");
        int nLines = first.getNumberOfLines();
        int nColumns = second.getNumberOfColumns();
        Matrix result = MatrixFactory.create(resultClass, nLines, nColumns);

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        AtomicInteger flag = new AtomicInteger(0);

        CashWorker cashWorker = new CashWorker(1024,result,flag,nThreads-1);
        ArrayBlockingQueue<TripleImpl> cash = cashWorker.getCash();

        executor.execute(cashWorker);
        for (int i = 0; i < nThreads-1; i++) {
            Runnable worker = new ProductWorker(first,second,i,nThreads-1,cash,flag);
            executor.execute(worker);
        }

        executor.shutdown();

        // Wait until all threads are finish
        //executor.awaitTermination();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }




}


