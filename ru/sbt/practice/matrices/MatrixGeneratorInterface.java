package ru.sbt.practice.matrices;

/**
 * Created by artem on 17.11.14.
 *
 * generateRandomValueMatrix создает матрицу соответствующих размеров с заполненностью 1-sparseFactor
 * подходит только для рас
 *
 * generateRandomValueMatrixLineTight создает матрицу соответствующих размеров с заполненностью 1-sparseFactor и
 * относительной заполненностью по строке lineTightFactor: случайно выбирается строка и в ней случайно заполняется
 * lineTightFactor*(1-sparseFactor) часть элементов.
 *
 *
 *
 */
public interface MatrixGeneratorInterface {
    Matrix generateRandomValueRandomIndexMatrix(Class matrixClass, int nLines, int nColumns, double sparseFactor);
    Matrix generateRandomValueRandomIndexLineTightMatrix(Class matrixClass, int nLines, int nColumns, double loadFactor, double lineTightFactor);
    Matrix generateRandomValueRandomIndexMatrixColumnTight(Class matrixClass, int nLines, int nColumns, double loadFactor, double columnTightFactor);
    Matrix generateRandomValueStrictIndexLineTightMatrix(Class matrixClass,int nLines, int nColumns, double lineLoadFactor, int[] lineIndexes);
    Matrix generateRandomValueStrictIndexColumnTightMatrix(Class matrixClass,int nLines, int nColumns, double columnLoadFactor, int[] columnIndexes);
}
