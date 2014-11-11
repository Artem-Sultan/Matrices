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
    public boolean isTransported() {return isTransported;}

    @Override
    public abstract Iterator <IndexValue> iteratorAll();
    @Override
    public abstract Iterator <IndexValue> iteratorNotZero();

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

            mainCycle:
            while (it1.hasNext() && it2.hasNext()) {
                IndexValue iv1 = it1.next();
                IndexValue iv2 = it2.next();
                while (iv1.index != iv2.index) {
                    if (iv1.index < iv2.index && it1.hasNext()) iv1 = it1.next();
                    if (iv2.index < iv1.index && it2.hasNext()) iv2 = it2.next();
                    if (!it1.hasNext() || !it2.hasNext()) break mainCycle;
                }
                sum = sum + iv1.value*iv2.value;
            }
            return sum;
        }
    }

    public class MatrixWarp implements Matrix{
        @Override
        public int getNumberOfLines() {
            return isTransported ? length : 1;
        }
        @Override
        public int getNumberOfColumns() {
            return isTransported ? 1 : length;
        }
        @Override
        public double getElement(int i, int j) {
            return isTransported ?  AbstractVector.this.getElement(i):AbstractVector.this.getElement(j);
        }
        @Override
        public void setElement(int i, int j, double element) {
            if (isTransported) { AbstractVector.this.setElement(i,element);}
            else {AbstractVector.this.setElement(j,element);}
        }
        @Override
        public boolean isProductable(Matrix foo) {
            if (isTransported) return (length == foo.getNumberOfColumns());
            else return (length == foo.getNumberOfLines());
        }

        @Override
        public Matrix productWith(Matrix foo, Class resultClass) throws IllegalArgumentException {
            int fooColumns = foo.getNumberOfColumns();
            double temp;
            if (!isProductable(foo)) {
                throw new IllegalArgumentException("can't make the product, dimensions don't match");
            } else {
                Matrix product = MatrixFactory.create(resultClass, length, fooColumns);
                for (int i = 0; i < length; i++) {
                    for (int j = 0; j < fooColumns; j++) {
                        try {
                            temp = this.getLine(i).scalarProductWith(foo.getColumn(j));
                            product.setElement(i, j, temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return product;
            }
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
    public Matrix castToMatrix(){
        return new MatrixWarp();
    }

}
