package ru.sbt.practice.matrices;

import java.util.Iterator;

/**
 * Created by artem on 10.11.14.
 */

public abstract class AbstractVector implements Vector {
    protected boolean isTransported = false;
    protected final int length;

    public AbstractVector(double[] vector) {
        length = vector.length;
        for (int i = 0; i < length; i++) {
            this.setElement(i, vector[i]);
        }
    }

    public AbstractVector(int _length) {
        length = _length;
    }

    @Override
    public boolean isTransported() {
        return isTransported;
    }

    @Override
    public abstract Iterator<IndexValue> iteratorAll();

    @Override
    public abstract Iterator<IndexValue> iteratorNotZero();

    public static class IndexValue {
        private final int index;
        private final double value;

        public int getIndex() {
            return index;
        }

        public double getValue() {
            return value;
        }

        public IndexValue(int _index, double _value) {
            index = _index;
            value = _value;
        }
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public abstract double getElement(int i);

    @Override
    public abstract void setElement(int i, double element);

    @Override
    public void transportate() {
        isTransported = !isTransported;
    }

    @Override
    public double scalarProductWith(Vector fooVector) throws IllegalArgumentException {
        if (length != fooVector.getLength())
            throw new IllegalArgumentException("lengths don't match");
        else {
            double sum = 0;
            Iterator<IndexValue> it1 = this.iteratorNotZero();
            Iterator<IndexValue> it2 = fooVector.iteratorNotZero();

            while (it1.hasNext() && it2.hasNext()) {
                IndexValue iv1 = it1.next();
                IndexValue iv2 = it2.next();
                while (iv1.index != iv2.index) {
                    if (iv1.index < iv2.index && it1.hasNext()) {
                        iv1 = it1.next();
                    } else if (iv2.index < iv1.index && it2.hasNext()) {
                        iv2 = it2.next();
                    } else
                        break;
                }
                if (iv1.index == iv2.index) sum = sum + iv1.value * iv2.value;

            }
            return sum;
        }
    }

    public class MatrixWrap extends AbstractMatrix {
        protected MatrixWrap(int nLines, int nColumns) {
            super(nLines, nColumns);
        }

        @Override
        public double getElement(int i, int j) {
            return isTransported ? AbstractVector.this.getElement(i) : AbstractVector.this.getElement(j);
        }

        @Override
        public void setElement(int i, int j, double element) {
            if (isTransported) {
                AbstractVector.this.setElement(i, element);
            } else {
                AbstractVector.this.setElement(j, element);
            }
        }

        @Override
        public Iterator<KeyImpl> notZeroIterator() {
            return null;
        }

        @Override
        public Vector getLine(int line) {
            if (!isTransported && line == 0) return AbstractVector.this;
            double[] temp = new double[1];
            temp[0] = AbstractVector.this.getElement(line);
            return new ArrayVector(temp);
        }

        @Override
        public Vector getColumn(int column) {
            if (!isTransported && column == 0) return AbstractVector.this;
            double[] temp = new double[1];
            temp[0] = AbstractVector.this.getElement(column);
            return new ArrayVector(temp);
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < length; i++) {
            res = res + getElement(i) + " ";
        }
        return res;
    }

    @Override
    public Matrix castToMatrix() {
        return isTransported ? new MatrixWrap(length, 1) : new MatrixWrap(1, length);
    }

}
