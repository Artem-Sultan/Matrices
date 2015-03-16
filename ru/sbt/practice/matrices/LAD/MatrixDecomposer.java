package ru.sbt.practice.matrices.LAD;

import ru.sbt.practice.matrices.Matrix;

/**
 * Created by artem on 15.12.14.
 */
public class MatrixDecomposer {
    private Class destinationMatrixClass;
    private AlgorithmEM algorithm;

    public MatrixDecomposer(Class destinationMatrixClass, AlgorithmEM algorithm) {
        this.destinationMatrixClass = destinationMatrixClass;
        this.algorithm = algorithm;
    }

    public Matrix[] decomposeWithNumberOfThemes(Matrix sourceMatrix, int themesT) {
        algorithm.setDestinationMatrixClass(destinationMatrixClass);
        return algorithm.decompose(sourceMatrix,themesT);
    }

    public void setDestinationMatrixClass(Class destinationMatrixClass) {
        this.destinationMatrixClass = destinationMatrixClass;
    }

    public void setAlgorithm(AlgorithmLAD algorithm) {
        this.algorithm = algorithm;
    }





    private void runEmAlgorithmOnMatrices(Matrix phi, Matrix theta){


    }
}
