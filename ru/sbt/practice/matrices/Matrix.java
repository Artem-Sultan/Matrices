package ru.sbt.practice.matrices;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by artem on 28.10.14.
 */
public interface Matrix {
    int getNumberOfLines();
    int getNumberOfColumns();
    double getElement (int i,int j);
    void setElement(int i, int j, double element);
    boolean isProductable(Matrix foo);
    Matrix plus(Matrix M);
    Iterator<AbstractMatrix.KeyImpl> notZeroIterator ();
    Matrix productWith(Matrix foo, Class resultClass) throws IllegalArgumentException;
    Matrix transpose();
    Vector getLine(int line);
    Vector getColumn(int column);
}
