package ru.sbt.practice.matrices;

import java.util.Iterator;

/**
 * Created by artem on 28.10.14.
 */
public class Matrix2dArray extends AbstractMatrix {
    private double[][] dataMatrix;


    public Matrix2dArray(int nLines, int nColumns) {
        super(nLines,nColumns);
        this.dataMatrix = new double[nLines][nColumns];
    }

    public Matrix2dArray(double[][] data) {
        super(data[0].length,data.length);
        dataMatrix = data;
    }

    @Override
    public double getElement(int i, int j) {
        return dataMatrix[i][j];
    }

    @Override
    public void setElement(int i, int j, double element) {
        dataMatrix[i][j] = element;
    }
    @Override
    public Iterator<KeyImpl> notZeroIterator() {
         class notZeroIterator implements Iterator<KeyImpl> {
             private int tempLineNotZero=0;
             private int tempColumnNotZero=0;

             boolean hadNext = false;
             @Override
             public boolean hasNext() {
                 for (int i=tempLineNotZero; i<nLines; i++) {
                     for (int j = tempColumnNotZero; j < nColumns; j++) {
                         if (dataMatrix[i][j] != 0) {
                             if (!hadNext) {
                                 tempLineNotZero = i;
                                 tempColumnNotZero = j;
                             }
                             hadNext = true;
                             return true;
                         }
                     }
                     tempColumnNotZero = 0;
                 }
                 return false;
             }

             @Override
             public KeyImpl next() {
                 hadNext = false;
                 if (tempColumnNotZero == nColumns-1) {
                     int tmp = tempColumnNotZero;
                     tempColumnNotZero = 0;
                     return new KeyImpl(tempLineNotZero++, tmp);
                 }
                 return new KeyImpl(tempLineNotZero, tempColumnNotZero++);

             }

         }
        return new notZeroIterator();
    }


    @Override
    public Vector getLine(int line) {
        return new WrapVector(nColumns,line,true);
    }

    @Override
    public Vector getColumn(int column) {
        return new WrapVector(nLines,column,false);
    }

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
            return isLine ? Matrix2dArray.this.getElement(positionInMatrix, i) : Matrix2dArray.this.getElement(i, positionInMatrix);
        }

        @Override
        public void setElement(int i, double element) {
            if (isLine) Matrix2dArray.this.setElement(positionInMatrix,i,element);
            else Matrix2dArray.this.setElement(i,positionInMatrix,element);
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

    @Override
    public String toString(){
        String tmp = new String();
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nColumns; j++) {
                tmp = tmp+ dataMatrix[i][j] +" ";
            }
            tmp += '\n';
        }
       return tmp;
    }
}


