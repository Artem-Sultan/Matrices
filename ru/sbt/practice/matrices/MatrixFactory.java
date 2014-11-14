package ru.sbt.practice.matrices;

/**
 * Created by artem on 10.11.14.
 */
public class MatrixFactory {
    public static Matrix create(Class resultClass, int nLines, int nColumns) {
        if (resultClass.equals(SparseMatrix.class)){return new SparseMatrix(nLines, nColumns);}
        if (resultClass.equals(Matrix2dArray.class)){return new Matrix2dArray(nLines, nColumns);}
        System.out.println("problems");
        return null;
    }

    public static Vector createVector(Class vectorClass, int vectorLength) {
        if (vectorClass.equals(SparseVector.class)){return new SparseVector(vectorLength);}
        if (vectorClass.equals(ArrayVector.class)){return new ArrayVector(vectorLength);}
        System.out.println("problems");
        return null;
    }
}
