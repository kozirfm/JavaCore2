package JavaCore2.lesson5;

import java.util.Arrays;

public class Main {

    final static int SIZE = 1000000;
    final static int HALF = SIZE/2;

    public static void main(String[] args) {

        System.out.println("Результат работы первого метода = " + makeArr1() + "ms");
        System.out.println("Результат работы второго метода =  " + makeArr2() + "ms");

    }

    public static long makeArr1(){

        float [] arr = new float[SIZE];
        long a = System.currentTimeMillis();

        Arrays.fill(arr, 1);

        calculateArr(arr, 0);

        return System.currentTimeMillis() - a;
    }

    public static long makeArr2(){

        float [] arr = new float[SIZE];
        float [] arr1 = new float[HALF];
        float [] arr2 = new float[HALF];
        long a = System.currentTimeMillis();

        Arrays.fill(arr, 1);

        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        Thread t1 = new Thread(()-> calculateArr(arr1, 0));

        Thread t2 = new Thread(()-> calculateArr(arr2, HALF));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);

       return System.currentTimeMillis() - a;

    }

    public static void calculateArr(float[] arr, int startNum){
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (startNum + i) / 5) * Math.cos(0.2f + (startNum + i) / 5) * Math.cos(0.4f + (startNum + i) / 2));
        }
    }

}
