package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        GUI gui = new GUI();

//        float[] list = {0.0f,0.3f,0.8f};
//        int c0=0,c1=0,c2=0;
//        for (int i=0;i<100;i++) {
//            int a = rand(list);
//
//            System.out.println(a);
//            if (a == 0)
//                c0++;
//            if (a == 1)
//                c1++;
//            if (a == 2)
//                c2++;
//        }
//        System.out.println("0="+c0+"\n1="+c1+"\n2="+c2);
//


//        Random r = new Random();
//        int i =0;
//        while (i<100) {
//            i++;
//            System.out.println(r.nextFloat()*.2);
//        }

//        int i=0;
//        float max = 0;
//        while (i<1000) {
//            float a = r.nextFloat() ;
//            if (a>max){
//                max=a;
//            }
//            System.out.println(a);
//            i++;
//        }
//        System.out.println("this is MAX: "+max);

//        double[] values = new double[1000];
//        double max = 0;
//        for (int i=0;i<1000;i++){
//            values[i] = generateValue(.5,.5);
//            if (values[i]>max)
//                max=values[i];
//        }
//        for (int i=0;i<values.length;i++){
//            System.out.println(values[i]/(max+.1));
//
//        }

//        int x = 5;
//        float lambda = 3;
//        float p =0;
//
//        int fact = 1;
//        for (int j=0;j<x;j++)
//            fact *= (j+1);
//        System.out.println("fact = "+fact);
//        p = (float)(( Math.exp(-lambda)*Math.pow(lambda,x))/fact);
//        System.out.println(p);
    }

//    public static int getPoisson(double lambda) {
//        double L = Math.exp(-lambda);
//        double p = 1.0;
//        int k = 0;
//
//        do {
//            k++;
//            p *= Math.random();
//        } while (p > L);
//
//        return k - 1;
//    }
//    private static Random rnd = new Random();
//
//    public static double generateValue(double mean, double stdDev)
//    {
//        double l = Math.exp(-mean);
//        int k = 0;
//        double p = 1;
//
//        do
//        {
//            k++;
//            p *= rnd.nextDouble();
//        } while (p > l);
//
//        return k - 1;
//    }


//    public static int rand(float []items){
//        double totalWeight = 0.0d;
//        for (float i : items)
//        {
//            totalWeight += i;
//        }
//// Now choose a random item
//        int randomIndex = -1;
//        double random = Math.random() * totalWeight;
//        for (int i = 0; i < items.length; ++i)
//        {
//            random -= items[i];
//            if (random <= 0.0d)
//            {
//                randomIndex = i;
//                break;
//            }
//        }
//        return randomIndex;
//    }

//    public static float[] setWeight(float[] list){
//        int zeroItemCount =
//        for (int i=0;i<list.length;i++){
//            if ()
//        }
//    }

}
