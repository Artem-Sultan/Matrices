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
        super(data);
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
        return null;
    }


    @Override
    public Vector getLine(int line) {
        return null;
    }

    @Override
    public Vector getColumn(int column) {
        double[] tempColumn = new double[nLines];
        for (int line = 0; line < nLines; line++) {
            tempColumn[line] = dataMatrix[line][column];
        }
        return null;
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


