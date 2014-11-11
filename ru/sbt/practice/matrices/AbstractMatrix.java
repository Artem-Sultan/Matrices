package ru.sbt.practice.matrices;

import java.io.IOException;


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
    public abstract Vector getLine(int line);

    @Override
    public abstract Vector getColumn(int column);

}
