package interpolation;

import algebra.SPL;

public class BicubicSplineInt {

    public static void bicubicInterpolation(double[][] inputMatrix, double a, double b) {
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
        SPL splSolver = new SPL();
        splSolver.gauss(X, B); // Memanggil metode gauss untuk memodifikasi B

        // Setelah pemanggilan gauss, array B telah berisi solusi
        // Menghitung f(a, b)
        double result = 0;
        for (int i = 0; i < 16; i++) {
            int xi = i % 4;
            int yi = i / 4;
            result += B[i] * Math.pow(a, xi) * Math.pow(b, yi);
        }

        // Menampilkan hasil
        System.out.printf("Nilai interpolasi bicubic f(%.2f, %.2f) adalah %.4f%n", a, b, result);
    }
}

