package ru.sbt.practice.matrices.Product;

import ru.sbt.practice.matrices.Containers.TripleImpl;
import ru.sbt.practice.matrices.Matrix;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by artem on 26.11.14.
 */
public class CashWorker implements Runnable {
    ArrayBlockingQueue<TripleImpl> cash;
    Matrix result;
    AtomicInteger flag;
    int nProducers;

    public CashWorker(int capacity, Matrix result, AtomicInteger flag, int nProducers) {
        cash = new ArrayBlockingQueue<TripleImpl>(capacity);
        this.result = result;
        this.nProducers = nProducers;
        this.flag = flag;
    }

    public ArrayBlockingQueue<TripleImpl> getCash() {
        return cash;
    }

    @Override
    public void run() {
       TripleImpl tmp = null;
        int flagg = flag.get();
       // System.out.println("producers:" + nProducers);
        while ((flagg=flag.get()) < nProducers) {
            if(cash.isEmpty()){
                LockSupport.parkNanos(10);
                continue;
            }
           // System.out.println("zzz" + flagg);
                try {
                    tmp = cash.take();
                    result.setElement(tmp.getX(), tmp.getY(), tmp.getElement());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           // System.out.println("out------");

        }
       // System.out.println("out of loop");
       // System.out.flush();
        while (!cash.isEmpty()) {
            try {
                tmp = cash.take();
                result.setElement(tmp.getX(), tmp.getY(), tmp.getElement());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("cash out");
    }
}



