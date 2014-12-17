package ru.sbt.practice.matrices;

import ru.sbt.practice.matrices.Containers.KeyImpl;
import ru.sbt.practice.matrices.Containers.TripleImpl;

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

    public SparseMatrix(Matrix matrixCopy) {super(matrixCopy);}

    @Override
    public double getElement(int i, int j) {
        if (i < 0 || i >= nLines)
            throw new ArrayIndexOutOfBoundsException("row index out of bounds");
        if (j < 0 || j >= nColumns)
            throw new ArrayIndexOutOfBoundsException("col index out of bounds");
        Double result = sparseData.get(new KeyImpl(i, j));
        return result  == null ? 0 : result;
    }

    @Override
    public void setElement(int i, int j, double element) {
        if (i < 0 || i >= nLines)
            throw new ArrayIndexOutOfBoundsException("row index out of bounds");
        if (j < 0 || j >= nColumns)
            throw new ArrayIndexOutOfBoundsException("col index out of bounds");
        if (sparseData==null) sparseData = new HashMap<KeyImpl, Double>();
        if (element != 0) sparseData.put(new KeyImpl(i,j), element);
    }


    @Override
    public Iterator<TripleImpl> notZeroIterator() {
        final Iterator<Map.Entry<KeyImpl, Double>> it = sparseData.entrySet().iterator();
        class notZeroIt  implements Iterator<TripleImpl>{
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public TripleImpl next() {
                Map.Entry<KeyImpl, Double> entry = it.next();
                return new TripleImpl(entry.getKey().getX(), entry.getKey().getY(),entry.getValue());
            }
        }
        return new notZeroIt();
    }

    @Override
    public Iterator<Double> notZeroEntryIterator() {
        return sparseData.values().iterator();
    }

    @Override
    public Vector getLine(int line) {
        if (line < 0 || line >= nLines)
            throw new ArrayIndexOutOfBoundsException("row index out of bounds");
        return new WrapVector(nColumns,line,true,this);
    }

    @Override
    public Vector getColumn(int column) {
        if (column < 0 || column >= nColumns)
            throw new ArrayIndexOutOfBoundsException("column index out of bounds");
        return new WrapVector(nLines,column,false,this);
    }
}


