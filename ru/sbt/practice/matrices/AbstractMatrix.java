package ru.sbt.practice.matrices;

import ru.sbt.practice.matrices.Containers.TripleImpl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


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


    protected AbstractMatrix(Matrix matrixCopy){
        this.nLines = matrixCopy.getNumberOfLines();
        this.nColumns = matrixCopy.getNumberOfColumns();
        Iterator<TripleImpl> iterator = matrixCopy.notZeroIterator();
        TripleImpl tmp;
        int x,y;
        double element;
        while (iterator.hasNext()) {
            tmp = iterator.next();
            x = tmp.getX();
            y = tmp.getY();
            element = tmp.getElement();
            this.setElement(x,y,element);
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
    public Matrix sparsePlus(Matrix M, Class matrixClass) {
        Set<TripleImpl> cash = new HashSet<TripleImpl>();
        Matrix result = MatrixFactory.create(matrixClass,nLines, nColumns);
        Iterator<TripleImpl> it = this.notZeroIterator();
        while (it.hasNext()) {
            TripleImpl triple = it.next();
            int x = triple.getX();
            int y = triple.getY();
            double element = triple.getElement();
            result.setElement(x, y, M.getElement(x, y) + element);
            cash.add(triple);
        }
        Iterator<TripleImpl> it2 = M.notZeroIterator();
        while (it2.hasNext()) {
            TripleImpl triple = it2.next();
            if (!cash.contains(triple)) {
                int x = triple.getX();
                int y = triple.getY();
                double element = triple.getElement();
                result.setElement(x, y, element);
            }
        }
        return result;
    }

    @Override
    public Matrix plus(Matrix M, Class matrixClass) {
        Matrix result = MatrixFactory.create(matrixClass,nLines, nColumns);
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nColumns; j++) {
                 result.setElement(i, j, M.getElement(i, j) + this.getElement(i, j));
            }
        }
        return result;
    }

    @Override
    public Matrix productWith(Matrix foo, Class resultClass) throws IllegalArgumentException {
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
    public abstract Iterator<TripleImpl> notZeroIterator();


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
            public Iterator<TripleImpl> notZeroIterator() {
                final Iterator<TripleImpl> it = AbstractMatrix.this.notZeroIterator();

                class transposedIterator implements Iterator<TripleImpl> {

                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public TripleImpl next() {
                        TripleImpl tmp = it.next();
                        return new TripleImpl(tmp.getY(), tmp.getX(), tmp.getElement());
                    }
                }
                return new transposedIterator();
            }

            @Override
            public Iterator<Double> notZeroEntryIterator() {
                return AbstractMatrix.this.notZeroEntryIterator();
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

    @Override
    public Matrix productWithScalar(double scalar) {
        Class myClass = this.getClass();
        Matrix result = MatrixFactory.create(myClass,nLines,nColumns);
        Iterator<TripleImpl> it = notZeroIterator();
        while (it.hasNext()) {
            TripleImpl nextTriple =  it.next();
            result.setElement(nextTriple.getX(),nextTriple.getY(),scalar*nextTriple.getElement());
        }
        return result;
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

