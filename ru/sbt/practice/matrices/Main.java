package ru.sbt.practice.matrices;

import java.io.IOException;

/**
 * Created by artem on 28.10.14.
 */
public class Main {

    public static void main(String[] args) {
        double[][] nums = {{1, 0, 1}, {0, 0, 1}};
        double[][] nums2 = {{1, 1}, {0,0}};
        double[][] nums4= {{0,0}, {0,0}};



        double[] nums3 = {0, 0, 0, 0, 5, 0, 0, 0, 0, 10, 0, 12, 0};

        SparseVector testVector1 = new SparseVector(nums3);
        SparseMatrix test1 = new SparseMatrix(nums);
        SparseMatrix test2 = new SparseMatrix(nums2);
        SparseMatrix test3 = new SparseMatrix(nums4);

        try {
            SparseMatrix tst3 = (SparseMatrix)test2.productWith(test1);
            System.out.println(tst3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(test3);

        //testVector1.transportate();

        //MatrixImpl test4 = (MatrixImpl) testVector1.castToMatrix();

    }
}
