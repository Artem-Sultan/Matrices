package ru.sbt.practice.matrices;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by artem on 28.10.14.
 */
public interface Vector {
    int getLength();
    double getElement(int i);
    void transportate();
    boolean isTransported();
    void setElement(int i, double element);
    Iterator iteratorNotZero();
    Iterator iteratorAll();
    double scalarProductWith (Vector fooVector) throws IllegalArgumentException;
    public Matrix castToMatrix();
}
