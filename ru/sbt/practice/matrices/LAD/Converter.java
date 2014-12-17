package ru.sbt.practice.matrices.LAD;

import ru.sbt.practice.matrices.Containers.TripleImpl;
import ru.sbt.practice.matrices.Matrix;
import ru.sbt.practice.matrices.MatrixFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by artem on 15.12.14.
 */
public class Converter {
    public static Matrix[] convert (Matrix matrix, int themesT, Class matrixClass, int nIterations, double constAlpha, double constBeta) {
        final double alpha = constAlpha/themesT;
        final double beta = constBeta/matrix.getNumberOfLines();

        int wordsW = matrix.getNumberOfLines();
        int docsD = matrix.getNumberOfColumns();

        Matrix phi = MatrixFactory.create(matrixClass, wordsW, themesT);
        Matrix theta = MatrixFactory.create(matrixClass,themesT,docsD);

        setRandom(phi);
        setRandom(theta);
        Iterator<TripleImpl> notZeroIterator = matrix.notZeroIterator();

        for (int i = 0; i < nIterations; i++) {
            //double[][] nWT = new double[wordsW][themesT];
            Matrix nWT = MatrixFactory.create(matrixClass, wordsW,themesT);
            //double[][] nTD = new double[themesT][docsD];
            Matrix nTD = MatrixFactory.create(matrixClass, themesT,docsD);
            double[] nT = new double[themesT];
            double[] nD = new double[docsD];
            double nTWD;
            while (notZeroIterator.hasNext()) {
                TripleImpl triple = notZeroIterator.next();
                int d = triple.getX();
                int w = triple.getY();
                double element = triple.getElement();
                double normCoefficient = phi.getLine(w).scalarProductWith(theta.getColumn(d));
                for (int t = 0; t < themesT; t++) {
                    nTWD = element * phi.getElement(w, t) * theta.getElement(t, d) / normCoefficient;

                    nWT.setElement(w,t,nWT.getElement(w,t)+nTWD);
                    nTD.setElement(t,d,nTD.getElement(w,t)+nTWD);
                    nT[t] += nTWD;
                    nD[d] += nTWD;
                }

            }

            for (int t = 0; t < themesT; t++) {
                for (int w = 0; w < wordsW; w++) {
                    phi.setElement(w,t, (nWT.getElement(w,t)+alpha)/(nT[t] + constAlpha));
                }
                for (int d = 0; d < docsD; d++) {
                    theta.setElement(t,d, (nTD.getElement(t,d)+beta)/(nD[d]+constBeta));
                }
            }
        }
        return new Matrix[]{phi,theta};
    }

    private static void setRandom(Matrix m) {
        Random rand = new Random();
        for (int i = 0; i < m.getNumberOfLines(); i++) {
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                m.setElement(i,j,rand.nextDouble());
            }

        }
    }

    private static void setZero(double[] array) {
            Arrays.fill(array, 0);
    }
    private static void setZero(double[][] array) {
        for (double[] line : array) {
            Arrays.fill(line, 0);
        }
    }
}
