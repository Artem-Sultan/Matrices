package ru.sbt.practice.matrices;

import java.io.IOException;
import java.util.*;

/**
 * Created by artem on 05.11.14.
 */
public class SparseVector extends AbstractVector {
    private boolean isTransported = false;
    private int length;
    private TreeMap<Integer,Double> sparseData;
    private int maxIndex=0;

    public SparseVector(double[] data){
        super (data);
    }

    public SparseVector(int l) {
        super(l);
    }

    @Override
    public Iterator<IndexValue> iteratorAll() {
        return new ItAll();
    }

    @Override
    public Iterator<IndexValue> iteratorNotZero() {
        return new ItNoneZero();
    }

    private class ItNoneZero implements Iterator<IndexValue>{
        private Collection entries = sparseData.entrySet();
        private Iterator entryIt = entries.iterator();
        private Map.Entry<Integer, Double> entry;
        @Override
        public boolean hasNext() {
            return entryIt.hasNext();
        }

        @Override
        public IndexValue next() {
            entry = (Map.Entry<Integer, Double>)entryIt.next();
            return new IndexValue(entry.getKey(), entry.getValue());
        }
    }

    private class ItAll implements Iterator<IndexValue>{

        private Collection entries = sparseData.entrySet();
        private Iterator entryIt = entries.iterator();
        private Map.Entry<Integer, Double> entry;
        int i =0;
        int nextIndexNotZero = -1;

        @Override
        public boolean hasNext() {
            return i<length;
        }

        @Override
        public IndexValue next() {
            if (nextIndexNotZero < i) {
                if (entryIt.hasNext()) {
                    entry = (Map.Entry<Integer, Double>) entryIt.next();
                    nextIndexNotZero = entry.getKey();
                }
                else nextIndexNotZero = length;
            }
            if (i == nextIndexNotZero) {
                i++;
                return new IndexValue(entry.getKey(), entry.getValue());
            }
            i++;
            return new IndexValue(i, 0);
        }
    }


    @Override
    public int getLength() {
        return length;
    }

    @Override
    public double getElement(int i) {
        if (i>=length) throw new ArrayIndexOutOfBoundsException(i);
        if (sparseData.get(i) == null) return 0;
        return sparseData.get(i);
    }

    @Override
    public void transportate() {
        isTransported =!isTransported;
    }

    @Override
    public void setElement(int i, double element) {
            if (i >= 0 && i < length)
            {
                sparseData.put(i,element);
            } else
            {
                throw new ArrayIndexOutOfBoundsException(i);
            }
    }

    @Override
    public String toString(){
        String result = "";
        Iterator it = new ItAll();
        IndexValue tmp = new IndexValue(1,1);
        while (it.hasNext()) {
            tmp = (IndexValue)it.next();
            result = result + tmp.getValue() +" ";
        }
        return result;
    }
}
