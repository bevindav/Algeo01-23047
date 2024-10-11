package algebra;
// tes
import utils.MatrixUtils;

public class SPL {
    public void splgauss(double[][] A, double[] B) {
        int N = B.length;

        for (int k = 0; k < N; k++) {
            // Mencari baris pivot dgn cek di 1 kolom mana yg plg gede (buat diag utamanya)
            int max = k;
            for (int i = k + 1; i < N; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                    max = i;
                }
            }

            // Menukar baris
            MatrixUtils.swap_rows(A, k, max);

            // Menukar nilai pada matriks B (hasil)
            double temp = B[k];
            B[k] = B[max];
            B[max] = temp;

            // Melakukan eliminasi
            for (int i = k + 1; i < N; i++) {
                double factor = A[i][k] / A[k][k];
                B[i] -= factor * B[k];
                for (int j = k; j < N; j++) {
                    A[i][j] -= factor * A[k][j];
                }
            }
        }

        // Substitusi mundur untuk mendapatkan solusi
        double[] x = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (B[i] - sum) / A[i][i];
        }

        // Cetak hasil solusi
        System.out.println("Solusi SPL:");
        for (int i = 0; i < N; i++) {
            System.out.println("x" + (i + 1) + " = " + x[i]);
        }
    }

    public void gaussJordan(double[][] A, double[] B) {
        int N = B.length;

        for (int k = 0; k < N; k++) {
            // Mencari baris pivot
            int max = k;
            for (int i = k + 1; i < N; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                    max = i;
                }
            }

            // Menukar baris menggunakan swap_rows dari MatrixUtils
            MatrixUtils.swap_rows(A, k, max);

            // Menukar nilai di matriks B (konstanta)
            double temp = B[k];
            B[k] = B[max];
            B[max] = temp;

            // Normalisasi baris pivot
            double pivot = A[k][k];
            for (int j = 0; j < N; j++) {
                A[k][j] /= pivot;
            }
            B[k] /= pivot;

            // Eliminasi pada baris lain
            for (int i = 0; i < N; i++) {
                if (i != k) {        // eliminasi semua baris kec pivot
                    double factor = A[i][k];
                    for (int j = 0; j < N; j++) {
                        A[i][j] -= factor * A[k][j];
                    }
                    B[i] -= factor * B[k];
                }
            }
        }

        // Cetak hasil solusi
        System.out.println("Solusi SPL (Gauss-Jordan):");
        for (int i = 0; i < N; i++) {
            System.out.println("x" + (i + 1) + " = " + B[i]);
        }
    }
}