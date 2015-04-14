package ru.sbt.practice.matrices;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by artem on 10.11.14.
 */
public abstract class AbstractMatrix implements Matrix {
    protected final int nLines;
    protected final int nColumns;
    protected AbstractMatrix(int nLines, int nColumns) {
        this.nLines = nLines;
        this.nColumns = nColumns;
    }
    protected AbstractMatrix(double[][] data) {
        this.nLines = data.length;
        this.nColumns = data[0].length;
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nColumns; j++) {
                this.setElement(i, j, data[i][j]);
            }
        }
    }
    @Override
    public int getNumberOfLines() {
        return nLines;
    }
    @Override
    public int getNumberOfColumns() {
        return nColumns;
    }
    @Override
    public abstract double getElement(int i, int j);
    @Override
    public abstract void setElement(int i, int j, double element);
    @Override
    public boolean isProductable(Matrix foo) {
        return (nColumns == foo.getNumberOfLines());
    }
    @Override
    public Matrix sparsePlus(Matrix M, Class matrixClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<KeyImpl, String> cash = new HashMap<KeyImpl, String>();
        final String foo = " ";
        Matrix result = MatrixFactory.create(matrixClass,nLines, nColumns);
        Iterator<KeyImpl> it = this.notZeroIterator();
        while (it.hasNext()) {
            KeyImpl pair = it.next();
            int x = pair.getX();
            int y = pair.getY();
            result.setElement(x, y, M.getElement(x, y) + this.getElement(x, y));
            cash.put(pair, foo);
        }
        Iterator<KeyImpl> it2 = M.notZeroIterator();
        while (it2.hasNext()) {
            KeyImpl pair = it2.next();
            if (cash.get(pair) == null) {
                int x = pair.getX();
                int y = pair.getY();
                result.setElement(x, y, M.getElement(x, y));
            }
        }
        return result;
    }
    @Override
    public Matrix plus(Matrix M, Class matrixClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Matrix result = MatrixFactory.create(matrixClass,nLines, nColumns);
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nColumns; j++) {
                result.setElement(i, j, M.getElement(i, j) + this.getElement(i, j));
            }
        }
        return null;
    }
    @Override
    public Matrix productWith(Matrix foo, Class resultClass) throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int fooColumns = foo.getNumberOfColumns();
        double temp;
        if (!isProductable(foo)) {
            throw new IllegalArgumentException("can't make the product, dimensions don't match");
        } else {
            Matrix product = MatrixFactory.create(resultClass, nLines, fooColumns);
            for (int i = 0; i < nLines; i++) {
                for (int j = 0; j < fooColumns; j++) {
                    try {
                        Vector line = this.getLine(i);
                        Vector column =foo.getColumn(j) ;
                        temp = line.scalarProductWith(column);
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
    public abstract Iterator<KeyImpl> notZeroIterator();
    @Override
    public Matrix transpose() {
        class WrapTransposed extends AbstractMatrix {
            protected WrapTransposed(int nLines, int nColumns) {
                super(nColumns, nLines);
            }
            @Override
            public double getElement(int i, int j) {
                return AbstractMatrix.this.getElement(j, i);
            }
            @Override
            public void setElement(int i, int j, double element) {
                AbstractMatrix.this.setElement(j, i, element);
            }
            @Override
            public Iterator<KeyImpl> notZeroIterator() {
                final Iterator<KeyImpl> it = AbstractMatrix.this.notZeroIterator();
                class transposedIterator implements Iterator<KeyImpl> {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }
                    @Override
                    public KeyImpl next() {
                        KeyImpl tmp = it.next();
                        return new KeyImpl(tmp.y, tmp.x);
                    }
                }
                return new transposedIterator();
            }
            @Override
            public Vector getLine(int line) {
                return AbstractMatrix.this.getColumn(line);
            }
            @Override
            public Vector getColumn(int column) {
                return AbstractMatrix.this.getLine(column);
            }
        }
        return new WrapTransposed(nLines, nColumns);
    }
    @Override
    public abstract Vector getLine(int line);
    @Override
    public abstract Vector getColumn(int column);
    public static class KeyImpl {
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        private final int x;
        private final int y;
        public KeyImpl(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof KeyImpl)) return false;
            KeyImpl key = (KeyImpl) o;
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
}