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
        Matrix2dArray test3 = new Matrix2dArray(nums2);
        Matrix2dArray test4 = new Matrix2dArray(nums1);


        SparseMatrix newM1 = (SparseMatrix)test2.productWith(test1, SparseMatrix.class);
        MatrixGenerator mGenerator = new MatrixGenerator();
        SparseMatrix newM2 = (SparseMatrix)mGenerator.randomSparse(SparseMatrix.class,10,10,0.3);
        SparseMatrix newM3 = (SparseMatrix)mGenerator.randomNonSparse(SparseMatrix.class, 10,10);

        System.out.println(test3);
        System.out.println(test4);
        System.out.println(test3.productWith(test4,Matrix2dArray.class));



    }
}
