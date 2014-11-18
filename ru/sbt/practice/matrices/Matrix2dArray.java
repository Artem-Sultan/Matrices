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
        return new WrapVector(nColumns,line,true,this);
    }

    @Override
    public Vector getColumn(int column) {
        return new WrapVector(nLines,column,false,this);
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


