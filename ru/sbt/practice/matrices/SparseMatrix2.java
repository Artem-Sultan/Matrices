package ru.sbt.practice.matrices;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SparseMatrix2 extends AbstractMatrix {
    private Map<Integer, TreeMap<Integer, Double>> data = new HashMap<Integer, TreeMap<Integer, Double>>();
    private Map<Integer, TreeMap<Integer, Double>> data2 = new HashMap<Integer, TreeMap<Integer, Double>>();

    protected SparseMatrix2(int nLines, int nColumns) {
        super(nLines, nColumns);
    }

    protected SparseMatrix2(double[][] data) {
        super(data.length, data[0].length);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] != 0)
                    this.setElement(i, j, data[i][j]);
            }
        }
    }

    @Override
    public int getNumberOfLines() {
        return super.getNumberOfLines();
    }

    @Override
    public int getNumberOfColumns() {
        return super.getNumberOfColumns();
    }

    @Override
    public double getElement(int i, int j) {
        if (i < 0 || i >= nLines)
            throw new ArrayIndexOutOfBoundsException("Row index is out of bounds");
        else if (j < 0 || j >= nColumns)
            throw new ArrayIndexOutOfBoundsException("Col index is out of bounds");
        else {
            if (data.keySet().contains(i)) {
                return data.get(i).containsKey(j) ? data.get(i).get(j) : 0;
            }
        }
        return 0;
    }

    @Override
    public void setElement(int i, int j, double element) {
        if (i < 0 || i >= nLines)
            throw new ArrayIndexOutOfBoundsException("Row index is out of bounds");
        else if (j < 0 || j >= nColumns)
            throw new ArrayIndexOutOfBoundsException("Col index is out of bounds");
        else {
            if (!data.keySet().contains(i)) {
                data.put(i, new TreeMap<Integer, Double>());
            }
            if (!data2.keySet().contains(j)) {
                data2.put(j, new TreeMap<Integer, Double>());
            }
            data.get(i).put(j, element);
            data2.get(j).put(i, element);
        }
    }

    @Override
    public boolean isProductable(Matrix foo) {
        return super.isProductable(foo);
    }

    @Override
    public Matrix productWith(Matrix foo, Class resultClass) throws IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return super.productWith(foo, resultClass);
    }

    @Override
    public Iterator<KeyImpl> notZeroIterator() {
        return null;
    }

    @Override
    public Vector getLine(int line) {
        return new SparseVector(data.get(line), nColumns);
    }

    @Override
    public Vector getColumn(int column) {
        Vector columnVector = new SparseVector(data2.get(column), nLines);
        columnVector.transportate();
        return columnVector;
    }
}
