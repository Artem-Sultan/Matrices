package ru.sbt.practice.matrices;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by artem on 05.11.14.
 */
public class SparseMatrix extends AbstractMatrix {
    private Map<KeyImpl, Double> sparseData;

    public SparseMatrix(int nLines, int nColumns) {
        super(nLines, nColumns);
    }

    public SparseMatrix(double[][] data) {
        super(data);
    }

    @Override
    public double getElement(int i, int j) {
        Double result = sparseData.get(new KeyImpl(i, j));
        return result  == null ? 0 : result;
    }

    @Override
    public void setElement(int i, int j, double element) {
        if (i < 0 || i > nLines)
            throw new ArrayIndexOutOfBoundsException("row index out of bounds");
        if (j < 0 || j > nColumns)
            throw new ArrayIndexOutOfBoundsException("col index out of bounds");
        if (sparseData==null) sparseData = new HashMap<KeyImpl, Double>();
        if (element != 0) sparseData.put(new KeyImpl(i,j), element);
    }



    @Override
    public Iterator<KeyImpl> notZeroIterator() {
        final Iterator<KeyImpl> it = sparseData.keySet().iterator();
        class notZeroIt  implements Iterator<KeyImpl>{
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public KeyImpl next() {
                return it.next();
            }
        }
        return new notZeroIt();
    }


    @Override
    public Vector getLine(int line) {
        return new WrapVector(nColumns,line,true);
    }

    @Override
    public Vector getColumn(int column) {
        return new WrapVector(nLines,column,false);
    }

    //wrapper
    private class WrapVector extends AbstractVector {
        private final boolean isLine;
        private final int positionInMatrix;

        private WrapVector(int length, int positionInMatrix, boolean isLine) {
            super(length);
            this.isLine = isLine;
            if (!isLine) super.transportate();
            this.positionInMatrix = positionInMatrix;
        }

        @Override
        public double getElement(int i) {
            return isLine ? SparseMatrix.this.getElement(positionInMatrix, i) : SparseMatrix.this.getElement(i, positionInMatrix);
        }

        @Override
        public void setElement(int i, double element) {
            if (isLine) SparseMatrix.this.setElement(positionInMatrix,i,element);
            else SparseMatrix.this.setElement(i,positionInMatrix,element);
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

}


