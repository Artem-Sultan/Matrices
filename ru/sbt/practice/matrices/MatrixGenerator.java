package ru.sbt.practice.matrices;

import java.util.LinkedHashSet;
import java.util.Random;

import static ru.sbt.practice.matrices.AbstractMatrix.KeyImpl;

/**
 * Created by artem on 17.11.14.
 */
public class MatrixGenerator implements MatrixGeneratorInterface{
    private static LinkedHashSet<AbstractMatrix.KeyImpl> randomKeys;

    public MatrixGenerator() {
        randomKeys = new LinkedHashSet<KeyImpl>();
    }

    @Override
    public Matrix randomSparse(Class matrixClass, int nLines, int nColumns, double loadFactor) {
        Matrix result = MatrixFactory.create(matrixClass, nLines,nColumns);
        int dataVolume = (int)Math.round(nLines*nColumns*loadFactor);
        Random randomGenerator = new Random();
        KeyImpl tmpKey;
        int x,y;
        for (int i = 0; i < dataVolume; i++) {
            do {
                x = randomGenerator.nextInt(nLines);
                y = randomGenerator.nextInt(nColumns);
                tmpKey = new KeyImpl(x,y);
            } while (!randomKeys.add(tmpKey));
            result.setElement(x,y,randomGenerator.nextDouble());
        }
        return result;
    }

    @Override
    public Matrix randomNonSparse(Class matrixClass, int nLines, int nColumns) {
        Matrix result = MatrixFactory.create(matrixClass, nLines,nColumns);
        Random randomGenerator = new Random();
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nColumns; j++) {
                result.setElement(i,j,randomGenerator.nextDouble());
            }
        }
        return result;
    }


/*
    @Override
    public Matrix generateRandomValueRandomIndexLineTightMatrix(Class matrixClass, int nLines, int nColumns, double loadFactor, double lineTightFactor) {
        Matrix result = MatrixFactory.create(matrixClass, nLines,nColumns);
        int dataVolume = (int)Math.round(nLines*nColumns*loadFactor);
        int lineVolume = (int)Math.round(nColumns*loadFactor*lineTightFactor);
        randomKeys = new LinkedHashSet<AbstractMatrix.KeyImpl>();
        LinkedHashSet<Integer> randomLines = new LinkedHashSet<Integer>();
        Random randomGenerator = new Random();
        KeyImpl tmpKey;
        int x,y,nextLine;
        int i;
        for ( i = 0; i < dataVolume; i= i + lineVolume) {
            do {
                nextLine = randomGenerator.nextInt(nLines);
            } while (randomLines.add(nextLine));
            for (int j = 0; j < lineVolume; j++) {
                do {
                    y = randomGenerator.nextInt(nColumns);
                    tmpKey = new KeyImpl(nextLine,y);
                } while (randomKeys.add(tmpKey));
                result.setElement(nextLine,y,randomGenerator.nextDouble());
            }
        }
        for (int j = i-lineVolume; j < dataVolume; j++) {
            do {
                y = randomGenerator.nextInt(nColumns);
                x = randomGenerator.nextInt(nLines);
                tmpKey = new KeyImpl(x,y);
            } while (randomKeys.add(tmpKey));
            result.setElement(x,y,randomGenerator.nextDouble());
        }
        return result;
    }


    @Override
    public Matrix generateRandomValueRandomIndexMatrixColumnTight(Class matrixClass, int nLines, int nColumns, double loadFactor, double columnTightFactor) {
        Matrix result = MatrixFactory.create(matrixClass, nLines,nColumns);
        int dataVolume = (int)Math.round(nLines*nColumns*loadFactor);
        int columnVolume = (int)Math.round(nColumns*loadFactor*columnTightFactor);
        randomKeys = new LinkedHashSet<AbstractMatrix.KeyImpl>();
        LinkedHashSet<Integer> randomColumns = new LinkedHashSet<Integer>();
        Random randomGenerator = new Random();
        KeyImpl tmpKey;
        int x,y,nextColumn;
        int i;

        for ( i = 0; i < dataVolume; i= i + columnVolume) {
            do {
                nextColumn = randomGenerator.nextInt(nLines);
            } while (randomColumns.add(nextColumn));
            for (int j = 0; j < columnVolume; j++) {
                do {
                    x = randomGenerator.nextInt(nColumns);
                    tmpKey = new KeyImpl(x,nextColumn);
                } while (randomKeys.add(tmpKey));
                result.setElement(x,nextColumn,randomGenerator.nextDouble());
            }
        }
        for (int j = i-columnVolume; j < dataVolume; j++) {
            do {
                y = randomGenerator.nextInt(nColumns);
                x = randomGenerator.nextInt(nLines);
                tmpKey = new KeyImpl(x,y);
            } while (randomKeys.add(tmpKey));
            result.setElement(x,y,randomGenerator.nextDouble());
        }
        return result;
    }


    @Override
    public Matrix generateRandomValueStrictIndexLineTightMatrix(Class matrixClass, int nLines, int nColumns, double lineLoadFactor, int[] lineIndexes) {
        Matrix result = MatrixFactory.create(matrixClass, nLines,nColumns);
        int  lineVolume = (int)Math.round(nColumns*lineLoadFactor);
        Random randomGenerator = new Random();
        for (int i = 0; i < lineIndexes.length; i++) {
            int lineIndex = lineIndexes[i];
            ArrayList<Double> line = new ArrayList<Double>();
            for (int j = 0; j < lineVolume; j++) {
                  line.add(randomGenerator.nextDouble());
            }
            for (int j = lineVolume; j < nColumns; j++) {
                  line.add(0.0);
            }
            Collections.shuffle(line);
            for (int j = 0; j < nColumns; j++) {
                  result.setElement(lineIndex,j,line.get(j));
            }
        }
        return result;
    }

    @Override
    public Matrix generateRandomValueStrictIndexColumnTightMatrix(Class matrixClass, int nLines, int nColumns, double columnLoadFactor, int[] columnIndexes) {
        Matrix result = MatrixFactory.create(matrixClass, nLines,nColumns);
        int  columnVolume = (int)Math.round(nLines*columnLoadFactor);
        Random randomGenerator = new Random();
        for (int i = 0; i < lineIndexes.length; i++) {
            int lineIndex = lineIndexes[i];
            ArrayList<Double> line = new ArrayList<Double>();
            for (int j = 0; j < lineVolume; j++) {
                line.add(randomGenerator.nextDouble());
            }
            for (int j = lineVolume; j < nColumns; j++) {
                line.add(0.0);
            }
            Collections.shuffle(line);
            for (int j = 0; j < nColumns; j++) {
                result.setElement(lineIndex,j,line.get(j));
            }
        }
        return result;    }
        */
}
