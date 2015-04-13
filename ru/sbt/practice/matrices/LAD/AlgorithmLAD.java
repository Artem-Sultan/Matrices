package ru.sbt.practice.matrices.LAD;

import ru.sbt.practice.matrices.Containers.TripleImpl;
import ru.sbt.practice.matrices.Matrix;
import ru.sbt.practice.matrices.MatrixFactory;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by artem on 16.03.15.
 */
public class AlgorithmLAD extends AlgorithmEM
{
    private final AlgorithmLADParameters parameters;
    private Class destinationMatrixClass=null;
    private Matrix phi;
    private Matrix theta;
    private AlgorithmConstants constants;

    public AlgorithmLAD(AlgorithmLADParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setDestinationMatrixClass(Class destinationMatrixClass) {
        this.destinationMatrixClass = destinationMatrixClass;
    }

    @Override
    public Matrix[] decompose(Matrix sourceMatrix, int themesT) {
        if (!destinationMatrixClassIsSet()) setDestinationMatrixClass(sourceMatrix.getClass());
        constants = new AlgorithmConstants(sourceMatrix, themesT);
        initPhiThetaMatrices();
        loopEMnTimes();
        return new Matrix[]{phi,theta};
    }

    private boolean destinationMatrixClassIsSet() {
        return destinationMatrixClass == null ? false : true;
    }

    private void initPhiThetaMatrices() {
        phi = MatrixFactory.create(destinationMatrixClass, constants.wordsW, constants.themesT);
        theta = MatrixFactory.create(destinationMatrixClass,constants.themesT,constants.docsD);
        setRandom(phi);
        setRandom(theta);
    }

    private static void setRandom(Matrix m) {
        Random rand = new Random();
        for (int i = 0; i < m.getNumberOfLines(); i++) {
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                m.setElement(i,j,rand.nextDouble());
            }
        }
    }

    private void loopEMnTimes() {
        for (int i = 0; i < parameters.getnIterations(); i++) {
            EMLoop loop = new EMLoop();
            loop.emLoop();
        }
    }

    private class EMLoop {
        private final Matrix nWT, nTD;
        private final double[] nT, nD;
        private double nTWD;
        private final Iterator<TripleImpl> notZeroIterator;

        public EMLoop() {
            nWT = MatrixFactory.create(destinationMatrixClass, constants.wordsW, constants.themesT);
            nTD = MatrixFactory.create(destinationMatrixClass, constants.themesT,constants.docsD);
            nT = new double[constants.themesT];
            nD = new double[constants.docsD];
            notZeroIterator = constants.sourceMatrix.notZeroIterator();
        }

        public void emLoop(){
            eLoop();
            mLoop();
        }

        private void eLoop() {
            while (notZeroIterator.hasNext()) {
                TripleImpl triple = notZeroIterator.next();
                computeELoopVariables(triple);
            }
        }

        private void computeELoopVariables(TripleImpl triple) {
            int w = triple.getX();
            int d = triple.getY();
            double element = triple.getElement();
            double normCoefficient = phi.getLine(w).scalarProductWith(theta.getColumn(d));
            for (int t = 0; t < constants.themesT; t++) {
                nTWD = element * phi.getElement(w, t) * theta.getElement(t, d) / normCoefficient;
                nWT.setElement(w, t, nWT.getElement(w, t) + nTWD);
                nTD.setElement(t, d, nTD.getElement(t, d) + nTWD);
                nT[t] += nTWD;
                nD[d] += nTWD;
            }
        }

        private void mLoop() {
            for (int t = 0; t < constants.themesT; t++) {
                populateThemeInPhi(t);
                populateThemeInTheta(t);
            }
        }

        private void populateThemeInPhi(int t) {
            for (int w = 0; w < constants.wordsW; w++) {
                phi.setElement(w,t, computePhiElement(t, w));
            }
        }

        private void populateThemeInTheta(int t) {
            for (int d = 0; d < constants.docsD; d++) {
                theta.setElement(t,d, computeThetaElement(t, d));
            }
        }

        private double computePhiElement(int t, int w) {
            return (nWT.getElement(w,t)+constants.alpha)/(nT[t] + parameters.getConstAlpha());
        }

        private double computeThetaElement(int t, int d) {
            return (nTD.getElement(t,d)+constants.beta)/(nD[d]+parameters.getConstBeta());
        }

    }

    private class AlgorithmConstants {
        final double alpha,beta;
        final int wordsW, docsD, themesT;
        final Matrix sourceMatrix;

        public AlgorithmConstants(Matrix sourceMatrix, int themesT) {
            this.sourceMatrix = sourceMatrix;
            this.themesT = themesT;
            alpha = parameters.getConstAlpha()/themesT;
            beta = parameters.getConstBeta()/sourceMatrix.getNumberOfLines();
            wordsW = sourceMatrix.getNumberOfLines();
            docsD = sourceMatrix.getNumberOfColumns();
        }
    }
}
