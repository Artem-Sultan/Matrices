package ru.sbt.practice.matrices.LAD;

import ru.sbt.practice.matrices.Matrix;

/**
 * Created by artem on 16.03.15.
 */
public abstract class AlgorithmEM {
    public abstract void setDestinationMatrixClass(Class destinationMatrixClass);
    public abstract Matrix[] decompose(Matrix sourceMatrix, int themesT);
}
