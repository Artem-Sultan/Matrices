package ru.sbt.practice.matrices;

/**
 * Created by artem on 28.10.14.
 */
public class Main {

    public static void main(String[] args) {
        double[][] nums1 = {{0, 0, 1, 0}, {0, 1, 1, 0}, {1, 0, 0, 1}, {0, 0, 1, 0}, };
        double[][] nums2 = {{1, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}, {0, 0, 0, 2}, };


        double[][] M1 = {{1, 1}, {0,0}};
        double[][] M2= {{0,1}, {0,2}};


        double[] nums3 = {0, 1};
        double[] nums5 = {2, 2}; //, 5, 0, 0, 0, 0, 10, 0, 12, 0};

        SparseVector testVector1 = new SparseVector(nums3);
        SparseVector testVector2 = new SparseVector(nums5);
       // SparseMatrix test1 = new SparseMatrix(nums);
        SparseMatrix test1 = new SparseMatrix(nums1);
        SparseMatrix test2 = new SparseMatrix(nums2);
/*
        try {
            SparseMatrix tst3 = (SparseMatrix) test2.productWith(test1,SparseMatrix.class);
            System.out.println(tst3);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }




        System.out.println("matrix1: \n" + test2 + "matrix2: \n" + test3);
        for (int i = 0; i < test3.nLines; i++) {
            for (int j = 0; j < test3.nColumns; j++) {
                test3.setElement(i, j, i + 2 * j);
            }

        }


        System.out.println("Matrix2 after setters: \n" + test3);

        System.out.println("1 productable with 2: \n" + test2.isProductable(test3));
        System.out.println("scalar = " + testVector1.scalarProductWith(testVector2));
*/
        SparseMatrix newM1 = (SparseMatrix)test2.productWith(test1,SparseMatrix.class);



         //SparseMatrix newM = (SparseMatrix)test2.productWith(test3, SparseMatrix.class);
         //System.out.println("Product:\n" + test3.getColumn(0).scalarProductWith(testVector2));
        System.out.println("sum : \n" + newM1);
        //System.out.println("Product:\n" + test2.getColumn(0).scalarProductWith(test3.getColumn(0)));

        //testVector1.transportate();

        //MatrixImpl test4 = (MatrixImpl) testVector1.castToMatrix();

    }
}
