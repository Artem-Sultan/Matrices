package ru.sbt.practice.matrices;

import java.io.IOException;

/**
 * Created by artem on 28.10.14.
 */
public interface Matrix {
    int getNumberOfLines();
    int getNumberOfColumns();
    double getElement (int i,int j);
    void setElement(int i, int j, double element);
    boolean isProductable(Matrix foo);
    Matrix productWith(Matrix foo, Class resultClass) throws IllegalArgumentException;
    Vector getLine(int line);
    Vector getColumn(int column);
}
