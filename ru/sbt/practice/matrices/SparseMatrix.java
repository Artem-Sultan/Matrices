package ru.sbt.practice.matrices;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by artem on 05.11.14.
 */
public class SparseMatrix extends AbstractMatrix {
    private Map<KeyImpl, Double> sparseData = new HashMap<KeyImpl, Double>();


    public SparseMatrix(int nLines, int nColumns) {
        super(nLines, nColumns);
    }

    public SparseMatrix(double[][] data) {
        super(data);
    }

    @Override
    public double getElement(int i, int j) {
        Double result = sparseData.get(new KeyImpl(i, j);
        return result  == null ? 0 : result;
    }

    @Override
    public void setElement(int i, int j, double element) {
        if (i < 0 || i > nLines)
            throw new ArrayIndexOutOfBoundsException("row index out of bounds");
        if (j < 0 || j > nColumns)
            throw new ArrayIndexOutOfBoundsException("col index out of bounds");
        if (element != 0) sparseData.put(new KeyImpl(i,j), element);
    }


    //wrapper
    @Override
    public Vector getLine(int line) {

        return temp;
    }


    @Override
    public Vector getColumn(int column) {

        return temp;
    }

    private class WarpVector implements Vector {
        private final boolean isLine;
        private final int positionInMatrix;
        private boolean isTransported;

        private WarpVector(boolean isLine, int positionInMatrix) {
            this.isLine = isLine;
            isTransported = !isLine;
            this.positionInMatrix = positionInMatrix;
        }


        @Override
        public int getLength() {
            return isLine ? nColumns:nLines ;
        }

        @Override
        public double getElement(int i) {
            return isLine ? SparseMatrix.this.getElement(positionInMatrix,i):SparseMatrix.this.getElement(i,positionInMatrix);
        }

        @Override
        public void transportate() {
            isTransported = !isTransported;
        }

        @Override
        public boolean isTransported() {
            return isTransported;
        }

        @Override
        public void setElement(int i, double element) {
            if (isLine) SparseMatrix.this.setElement(positionInMatrix,i,element);
            else SparseMatrix.this.setElement(i,positionInMatrix,element);
        }


        @Override
        public Iterator iteratorNotZero() {
            Collection entries = sparseData.entrySet();
            entries.
            Iterator entryIt = entries.iterator();
            Map.Entry<Integer, Double> entry;

             class ItNotZero implements Iterator<AbstractVector.IndexValue>{


                int i =0;
                int nextIndexNotZero = -1;
                private final int vectorLength;
                {
                     if (isLine) vectorLength=nColumns;
                     else vectorLength = nLines;
                }

                @Override
                public boolean hasNext() {
                    return i<length;
                }

                @Override
                public AbstractVector.IndexValue next() {
                    if (nextIndexNotZero < i) {
                        if (entryIt.hasNext()) {
                            entry = (Map.Entry<Integer, Double>) entryIt.next();
                            nextIndexNotZero = entry.getKey();
                        }
                        else nextIndexNotZero = length;
                    }
                    if (i == nextIndexNotZero) {
                        i++;
                        return new AbstractVector.IndexValue(entry.getKey(), entry.getValue());
                    }
                    i++;
                    return new AbstractVector.IndexValue(i, 0);
                }
            }
        }

        @Override
        public Iterator iteratorAll() {
            class ItAll implements Iterator<AbstractVector.IndexValue>{
                private int i=0;
                private final int vectorLength;
                {
                    if (isLine) vectorLength=nColumns;
                    else vectorLength = nLines;
                }

                @Override
                public boolean hasNext() {
                    return i<vectorLength;
                }

                @Override
                public AbstractVector.IndexValue next() {
                    i++;
                    return new AbstractVector.IndexValue(i-1,WarpVector.this.getElement(i-1));
                }
            }
            return new ItAll();
        }

        @Override
        public double scalarProductWith(Vector fooVector) throws IllegalArgumentException {
            return 0;
        }

        @Override
        public Matrix castToMatrix() {
            return null;
        }
    }














    @Override
    public String toString() {
        String tmp = new String();
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nColumns; j++) {
                tmp = tmp + getElement(i, j) + " ";
            }
            tmp += '\n';
        }
        return tmp;
    }



    public class KeyImpl {
        private final int x;
        private final int y;

        public KeyImpl(int x, int y) {
            this.x = x;
            this.y =y;
        }
        @Override
        public boolean equals(Object o){
            if (this == o) return true;
            if (!(o instanceof KeyImpl)) return false;
            KeyImpl key = (KeyImpl)o;
            return x == key.x && y == key.y;
        }
        @Override
        public int hashCode() {
            int res = 17;
            res = res * 31 + Math.min(x, y);
            res = res * 31 + Math.max(y, x);
            return res;
        }
    }
}


