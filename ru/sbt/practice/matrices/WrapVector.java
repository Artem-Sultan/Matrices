package ru.sbt.practice.matrices;

import java.util.Iterator;

/**
 * Created by artem on 18.11.14.
 */
public class WrapVector extends AbstractVector {
    private final boolean isLine;
    private final int positionInMatrix;
    private Matrix wrappedMatrix;

    public WrapVector(int length, int positionInMatrix, boolean isLine, Matrix wrappedMatrix) {
        super(length);
        this.isLine = isLine;
        this.wrappedMatrix = wrappedMatrix;
        if (!isLine) super.transportate();
        this.positionInMatrix = positionInMatrix;
    }

    @Override
    public double getElement(int i) {
        return isLine ? wrappedMatrix.getElement(positionInMatrix, i) : wrappedMatrix.getElement(i, positionInMatrix);
    }

    @Override
    public void setElement(int i, double element) {
        if (isLine) wrappedMatrix.setElement(positionInMatrix, i, element);
        else wrappedMatrix.setElement(i,positionInMatrix,element);
    }


    @Override
    public Iterator iteratorNotZero() {
        class ItNotZero implements Iterator<AbstractVector.IndexValue>{
            private int tempNotZero=0;
            boolean hadNext = false;
            @Override
            public boolean hasNext() {
                for (int j=tempNotZero; j<length; j++) {
                    if (getElement(j) !=0 ) {
                        if (!hadNext) tempNotZero = j;
                        hadNext = true;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public AbstractVector.IndexValue next() {
                hadNext = false;
                return new AbstractVector.IndexValue(tempNotZero, getElement(tempNotZero++));
            }
        }
        return new ItNotZero();
    }

    @Override
    public Iterator iteratorAll() {
        class ItAll implements Iterator<AbstractVector.IndexValue>{
            private int i=0;

            @Override
            public boolean hasNext() {
                return i<length;
            }

            @Override
            public AbstractVector.IndexValue next() {
                i++;
                return new AbstractVector.IndexValue(i-1,getElement(i - 1));
            }
        }
        return new ItAll();
    }
}
