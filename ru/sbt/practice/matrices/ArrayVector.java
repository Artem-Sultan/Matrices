
package ru.sbt.practice.matrices;

import ru.sbt.practice.matrices.AbstractVector;
import ru.sbt.practice.matrices.Matrix;

import java.io.IOException;
import java.util.Iterator;


/**
 * Created by artem on 28.10.14.
 */


public class ArrayVector extends AbstractVector {
    private double[] vector;

    public ArrayVector(double[] vector) {
        super(vector);
    }

    public ArrayVector(int length) {
        super(length);
        vector = new double[length];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 0;
        }
    }

    @Override
    public Iterator<IndexValue> iteratorAll() {
        return new itAll();
    }
    @Override
    public Iterator<IndexValue> iteratorNotZero() {
        return new itNotNull();
    }

    @Override
    public double getElement(int i) {
        return vector[i];
    }


    private class itAll implements Iterator {
        int i = 0;

        @Override
        public boolean hasNext() {
            return (i < length);
        }

        @Override
        public IndexValue next() {
            i++;
            return new IndexValue(i - 1, vector[i]);
        }
    }

    private class itNotNull implements Iterator {
        int i = 0;
        int tempNonZero = 0;

        @Override
        public boolean hasNext() {
            for (int j = tempNonZero; j < length; j++) {
                if (vector[j] != 0) {
                    i=j;
                    return true;
                }
            }
            return false;
        }

        @Override
        public IndexValue next() {
            tempNonZero = i +1;
            return new IndexValue(i, vector[i]);
        }
    }

    @Override
    public void setElement(int i, double element) {
        if (vector == null)
            vector = new double[0];
        double[] temp = new double[vector.length+1];
        for (int j = 0; j < vector.length; j++) {
            temp[j] = vector[j];
        }
        vector = temp;
        vector[i] = element;
    }

}
