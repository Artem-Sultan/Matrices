package ru.sbt.practice.matrices.LAD;

/**
 * Created by artem on 16.03.15.
 */
public class AlgorithmLADParameters {
    private final int nIterations;
    private final double constAlpha;
    private final double constBeta;

    public AlgorithmLADParameters(int nIterations, double constAlpha, double constBeta) {
        this.nIterations = nIterations;
        this.constAlpha = constAlpha;
        this.constBeta = constBeta;
    }

    public int getnIterations() {
        return nIterations;
    }

    public double getConstAlpha() {
        return constAlpha;
    }

    public double getConstBeta() {
        return constBeta;
    }
}
