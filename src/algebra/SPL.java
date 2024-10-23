package algebra;

import utils.MatrixUtils;

public class SPL {

    // Metode Gauss buat nyari solusi SPL, bisa non-parametrik atau parametrik
    public double[] gauss(double[][] A, double[] B) {
        int N = B.length;
        boolean adaVariabelBebas = false;  // Flag buat cek ada nggak variabel bebas
        double[] solusi = new double[N];

        // Proses eliminasi Gauss
        for (int k = 0; k < N; k++) {
            int max = k;
            // Nyari baris dengan elemen maksimum di kolom ini
            for (int i = k + 1; i < N; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                    max = i;
                }
            }
            // Swap baris k sama baris dengan elemen maksimum
            MatrixUtils.swap_rows(A, k, max);
            double temp = B[k];
            B[k] = B[max];
            B[max] = temp;

            // Eliminasi ke bawah
            for (int i = k + 1; i < N; i++) {
                if (A[k][k] == 0) {
                    adaVariabelBebas = true;  // Kalau ketemu elemen 0, berarti ada variabel bebas
                    continue;
                }
                double faktor = A[i][k] / A[k][k];
                B[i] -= faktor * B[k];
                for (int j = k; j < N; j++) {
                    A[i][j] -= faktor * A[k][j];
                }
            }
        }

        // Kalau nggak ada variabel bebas, hitung solusinya
        if (!adaVariabelBebas) {
            for (int i = N - 1; i >= 0; i--) {
                double sum = 0.0;
                for (int j = i + 1; j < N; j++) {
                    sum += A[i][j] * B[j];
                }
                solusi[i] = (B[i] - sum) / A[i][i];
            }
        } else {
            solusi = getParametricSolution(A, B);  // Kalau ada variabel bebas, hitung solusi parametrik
        }

        return solusi;  // Return solusinya aja, print nanti di main
    }

    // Fungsi buat hitung solusi parametrik
    private double[] getParametricSolution(double[][] A, double[] B) {
        int N = B.length;
        double[] solusi = new double[N];
        // Kasih nilai variabel bebas (misal 0 buat sementara)
        for (int i = 0; i < N; i++) {
            if (A[i][i] != 0) {
                solusi[i] = B[i];  // Variabel dengan koefisien nggak nol
            } else {
                solusi[i] = 0;  // Variabel bebas
            }
        }
        return solusi;
    }

    // Metode Gauss-Jordan buat nyari solusi SPL
    public double[] gaussJordan(double[][] A, double[] B) {
        int N = B.length;
        boolean adaVariabelBebas = false;
        double[] solusi = new double[N];

        // Proses eliminasi Gauss-Jordan
        for (int k = 0; k < N; k++) {
            int max = k;
            // Nyari elemen maksimum di kolom
            for (int i = k + 1; i < N; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                    max = i;
                }
            }
            // Swap baris
            MatrixUtils.swap_rows(A, k, max);
            double temp = B[k];
            B[k] = B[max];
            B[max] = temp;

            // Normalisasi pivot
            double pivot = A[k][k];
            if (pivot == 0) {
                adaVariabelBebas = true;
                continue;
            }
            for (int j = 0; j < N; j++) {
                A[k][j] /= pivot;
            }
            B[k] /= pivot;

            // Eliminasi ke atas dan ke bawah
            for (int i = 0; i < N; i++) {
                if (i != k) {
                    double faktor = A[i][k];
                    for (int j = 0; j < N; j++) {
                        A[i][j] -= faktor * A[k][j];
                    }
                    B[i] -= faktor * B[k];
                }
            }
        }

        // Kalau nggak ada variabel bebas, hitung solusinya
        if (!adaVariabelBebas) {
            for (int i = 0; i < N; i++) {
                solusi[i] = B[i];
            }
        } else {
            solusi = getParametricSolution(A, B);
        }

        return solusi;
    }

    // Metode Cramer buat SPL (kalau bisa dihitung determinannya)
    public double[] cramer(Matriks A, double[] B) {
        int N = A.getRow();
        double[] solusi = new double[N];
        double detA = Determinan.determinanEkspansiKofaktor(A);

        if (detA == 0) {
            return null;  // Kalau determinan 0, nggak ada solusi unik
        }

        // Ganti kolom satu per satu sama B, lalu hitung determinannya
        for (int i = 0; i < N; i++) {
            Matriks temp = new Matriks(A.getRow(), A.getCol(), true);
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    temp.mat[r][c] = A.mat[r][c];
                }
            }
            for (int j = 0; j < N; j++) {
                temp.mat[j][i] = B[j];
            }

            double detTemp = Determinan.determinanEkspansiKofaktor(temp);
            solusi[i] = detTemp / detA;  // Solusi variabel ke-i
        }

        return solusi;
    }

    // Metode Invers Matriks (buat SPL yang solusinya unik)
    public static double[] splbalikan(Matriks A, double[] B) {
        int n = A.getRow();
        if (n != A.getCol()) {
            throw new IllegalArgumentException("Matriks harus persegi buat metode invers.");
        }

        double[][] inversA = Inverse.invers(A);
        double[] solusi = new double[n];
        for (int i = 0; i < n; i++) {
            solusi[i] = 0;
            for (int j = 0; j < n; j++) {
                solusi[i] += inversA[i][j] * B[j];
            }
        }

        return solusi;
    }
}
