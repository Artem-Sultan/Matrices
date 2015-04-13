package ru.sbt.practice.matrices;

import ru.sbt.practice.matrices.Product.ProductMaker;

/**
 * Created by artem on 28.10.14.
 */
public class Main {

    public static void main(String[] args) {
        double[][] nums1 = {{0, 2, 1, 0}, {0, 1, 1, 0}, {1, 0, 4, 1}, {2, 0, 1, 0}, };
        double[][] nums2 = {{1, 0, 1, 0}, {0, 3, 1, 0}, {0, 0, 0, 1}, {0, 1, 0, 2}, };


        double[][] M1 = {{1, 1}, {0,0}};
        double[][] M2= {{0,1}, {0,2}};


        double[] nums3 = {0, 1};
        double[] nums5 = {2, 2}; //, 5, 0, 0, 0, 0, 10, 0, 12, 0};
/*
        SparseVector testVector1 = new SparseVector(nums3);
        SparseVector testVector2 = new SparseVector(nums5);
        SparseMatrix test1 = new SparseMatrix(nums);
    */
        SparseMatrix test1 = new SparseMatrix(nums1);
        SparseMatrix test2 = new SparseMatrix(nums2);
        Matrix2dArray test3 = new Matrix2dArray(nums1);
        Matrix2dArray test4 = new Matrix2dArray(nums2);


        SparseMatrix newM1 = (SparseMatrix)test1.productWith(test2, SparseMatrix.class);
        Matrix2dArray newM2 = (Matrix2dArray)test3.productWith(test2, Matrix2dArray.class);



        int nThreads = Runtime.getRuntime().availableProcessors();

        ProductMaker productMaker = new ProductMaker();




        MatrixGenerator mGenerator = new MatrixGenerator();
        SparseMatrix newM22 = (SparseMatrix)mGenerator.randomSparse(SparseMatrix.class,1000,200,0.3);


/*
        long startTime = System.currentTimeMillis();

        Matrix result1 = newM22.productWith(newM32, SparseMatrix.class);

        long stopTime = System.currentTimeMillis();
        System.out.println("no opt: " + (stopTime-startTime));


        long startTime2 = System.currentTimeMillis();
        Matrix result0 = productMaker.product(newM22,newM32,SparseMatrix.class);
        long stopTime2 = System.currentTimeMillis();
        System.out.println("with opt:" + (stopTime2-startTime2));
*/
        long startTime3 = System.currentTimeMillis();

/*
        Matrix[] lad = MatrixDecomposer.decomposeWithNumberOfThemes(newM22, 15, SparseMatrix.class, 50, 1, 50);
        Matrix result11 = newM22.productWithScalar(-1);
        Matrix finalM = result11.plus(lad[0].productWith(lad[1],SparseMatrix.class), SparseMatrix.class);
        Iterator <Double> nz = finalM.notZeroEntryIterator();

        double maxElement = 0;

        while (nz.hasNext()) {
            double temp = nz.next();
            maxElement = maxElement < temp ? temp : maxElement;
        }


                System.out.println(maxElement);



        //result11= productMaker.optProduct(newM22, newM32, SparseMatrix.class);
        long stopTime3 = System.currentTimeMillis();




        System.out.println("with opt2:" + (stopTime3-startTime3));


        //System.out.println(result1);
        //System.out.println(result);
        //System.out.println(result11.getElement(0,0));
        //System.out.println(result11.getElement(0,0));
        //System.out.println(test1.productWith(test2,Matrix2dArray.class));

*/

    }
}
