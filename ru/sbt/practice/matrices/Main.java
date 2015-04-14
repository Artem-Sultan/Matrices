package ru.sbt.practice.matrices;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by artem on 28.10.14.
 */
public class Main {

    public static void main(String[] args) {
        double[][] nums = {{1, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}, {0, 0, 0 ,2}};
        double[][] nums2 = {{0, 0, 1, 0}, {0, 1, 1, 0}, {1, 0, 0, 1}, {0, 0, 1, 0}};

//        Matrix matrix = new SparseMatrix2(nums);

        Map<Integer, Double> shit = new TreeMap<Integer, Double>();
        shit.put(0, 23423423.0);
        shit.put(3, 453453453.0);
        shit.put(1, 342.0);
        shit.put(8, 23423443.0);
        shit.put(2, 45342.0);
        shit.put(5, 3243423.0);
        shit.put(9, 2321.0);
        shit.put(6, 23.0);
        shit.put(1435, 9999999999.0);
        shit.put(999999999, 3323.0);

        SparseMatrix2 m = new SparseMatrix2(5, 5);
        m.setElement(3, 2, 6.0);
        m.setElement(3, 1, 6754.0);
        m.setElement(3, 0, 54.0);
        m.setElement(2, 4, 54.0);
        m.setElement(2, 1, 1234.0);
        System.out.println(m);
        System.out.println(m.getElement(0, 2));
        SparseVector gavno = (SparseVector)m.getColumn(1);
   //     gavno.print();
        Matrix product = null;

        try {
            product = MatrixFactory.create(SparseMatrix2.class, 10, 10);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        System.out.println(product.getNumberOfLines());
        System.out.println(product.getNumberOfColumns());

        double[] suka = {8.0, 5.0, 4.0, 3.0, 2.0, 5.0, 7.0};
        Vector vector1 = new ArrayVector(suka);
        double result = vector1.scalarProductWith(vector1);
        System.out.println(result);
        System.out.println(((SparseVector) m.getLine(2)).scalarProductWith((SparseVector) m.getLine(3)));

        SparseMatrix m2 = new SparseMatrix(nums);
        SparseMatrix m3 = new SparseMatrix(nums2);

        SparseMatrix2 res = null;
        try {
            res = (SparseMatrix2)m2.productWith(m3, SparseMatrix2.class);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(res);

        Iterator<AbstractVector.IndexValue> iter;
        for (iter = m2.getLine(0).iteratorNotZero(); iter.hasNext(); ) {
            AbstractVector.IndexValue iv = iter.next();
            System.out.println(iv.getIndex() + " " + iv.getValue());
        }

        for (iter = m3.getColumn(2).iteratorNotZero(); iter.hasNext(); ) {
            AbstractVector.IndexValue iv = iter.next();
            System.out.println(iv.getIndex() + " " + iv.getValue());
        }


/*
        for (Map.Entry<Integer, Double> entry: shit.entrySet())
            System.out.println(entry.getKey() + " = " + entry.getValue());*/

    }
}
