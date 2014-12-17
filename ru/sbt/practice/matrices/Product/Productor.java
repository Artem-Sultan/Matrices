package ru.sbt.practice.matrices.Product;

import ru.sbt.practice.matrices.Matrix;

import java.util.concurrent.RecursiveAction;

/**
 * Created by artem on 26.11.14.
 */
public class Productor extends RecursiveAction {
    private Matrix first;
    private Matrix second;
    private Matrix result;
    private int lineIndexStart;
    private int lineIndexEnd;
    private int columnIndexStart;
    private int columnIndexEnd;

    public Productor(Matrix first, Matrix second, Matrix result, int iLineStart, int iLineEnd, int jLineStart, int jLineEnd) {
        this.first = first;
        this.second = second;
        this.result = result;
        lineIndexEnd = iLineEnd;
        lineIndexStart = iLineStart;
        columnIndexStart = jLineStart;
        columnIndexEnd = jLineEnd;
    }


    @Override
    protected void compute() {
        if (lineIndexEnd == lineIndexStart && columnIndexEnd == columnIndexStart) {
            double temp = first.getLine(lineIndexEnd).scalarProductWith(second.getColumn(columnIndexEnd));
            synchronized (result) {
                result.setElement(lineIndexEnd, columnIndexEnd, temp);
            }
        } else {
            if (lineIndexEnd != lineIndexStart) {
                int lineMidPoint = lineIndexStart + (lineIndexEnd - lineIndexStart) / 2;
                Productor p1 = new Productor(first, second, result, lineIndexStart, lineMidPoint, columnIndexStart, columnIndexEnd);
                Productor p2 = new Productor(first, second, result, lineMidPoint + 1, lineIndexEnd, columnIndexStart, columnIndexEnd);
                invokeAll(p1, p2);
            } else {
                int colMidPoint = columnIndexStart + (columnIndexEnd - columnIndexStart) / 2;
                Productor p1 = new Productor(first, second, result, lineIndexStart, lineIndexEnd, columnIndexStart, colMidPoint);
                Productor p2 = new Productor(first, second, result, lineIndexStart, lineIndexEnd, colMidPoint + 1, columnIndexEnd);
                invokeAll(p1, p2);
            }
        }
    }
}

