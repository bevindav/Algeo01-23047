package interpolation;

import algebra.SPL;

public class BicubicSplineInt {

    public static double bicubicInterpolation(double[][] inputMatrix, double a, double b) {
        // Membentuk matriks X (16x16)
        double[][] X = new double[16][16];
        for (int i = 0; i < 16; i++) {
            int xi = i % 4;
            int yi = i / 4;
            for (int j = 0; j < 16; j++) {
                int xj = j % 4;
                int yj = j / 4;
                X[i][j] = Math.pow(xi, xj) * Math.pow(yi, yj);
            }
        }

        // Membentuk array Y (16) dari nilai input
        double[] B = new double[16];
        for (int i = 0; i < 16; i++) {
            B[i] = inputMatrix[i / 4][i % 4];
        }

        // Menggunakan SPL untuk menyelesaikan sistem persamaan linear
        SPL solv = new SPL();
        double[] hasil  = solv.gauss(X, B); // Memanggil metode gauss untuk memodifikasi B

        // Setelah pemanggilan gauss, array B telah berisi solusi
        // Menghitung f(a, b)
        double result = 0;
        for (int i = 0; i < 16; i++) {
            int xi = i % 4;
            int yi = i / 4;
            result += hasil[i] * Math.pow(a, xi) * Math.pow(b, yi);
        }

        return result;
    }
    public static String StringHasil(double[][] inputMatrix, double a, double b){
        StringBuilder buffer = new StringBuilder(); // Membuat string baru
        double res = bicubicInterpolation(inputMatrix, a, b);
        buffer.append("f("+a+","+b+") = "+res);
        return buffer.toString();
    }
}

