package ru.sbt.practice.matrices;

/**
 * Created by artem on 10.11.14.
 */
public class MatrixFactory {
    public static Matrix create(Class resultClass, int nLines, int fooColumns) {
        if (resultClass.equals(SparseMatrix.class)){return new SparseMatrix(nLines, fooColumns);}
        if (resultClass.equals(Matrix2dArray.class)){return new Matrix2dArray(nLines, fooColumns);}
        System.out.println("problems");
        return null;
    }

    public static Vector createVector(Class vectorClass, int nColumns) {
        if (vectorClass.equals(SparseVector.class)){return new SparseVector(nColumns);}
        if (vectorClass.equals(ArrayVector.class)){return new ArrayVector(nColumns);}
        System.out.println("problems");
        return null;
    }
}
