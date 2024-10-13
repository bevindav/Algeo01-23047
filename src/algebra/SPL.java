package algebra;
// tes
import utils.MatrixUtils;

public class SPL {

    // Metode Gauss yang mempertimbangkan solusi tidak tunggal
    public void gauss(double[][] A, double[] B) {
        int N = B.length;
        boolean hasFreeVariables = false;

        for (int k = 0; k < N; k++) {
            int max = k;
            for (int i = k + 1; i < N; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                    max = i;
                }
            }
            MatrixUtils.swap_rows(A, k, max);
            double temp = B[k];
            B[k] = B[max];
            B[max] = temp;

            for (int i = k + 1; i < N; i++) {
                if (A[k][k] == 0) {
                    hasFreeVariables = true;
                    continue;
                }
                double factor = A[i][k] / A[k][k];
                B[i] -= factor * B[k];
                for (int j = k; j < N; j++) {
                    A[i][j] -= factor * A[k][j];
                }
            }
        }

        if (!hasFreeVariables) {
            for (int i = N - 1; i >= 0; i--) {
                double sum = 0.0;
                for (int j = i + 1; j < N; j++) {
                    sum += A[i][j] * B[j];
                }
                B[i] = (B[i] - sum) / A[i][i];
            }

            System.out.println("Solusi SPL:");
            for (int i = 0; i < N; i++) {
                System.out.println("x" + (i + 1) + " = " + B[i]);
            }
        } else {
            System.out.println("Solusi dalam bentuk parametrik:");
            printParametricSolution(A, B);
        }
    }

    private void printParametricSolution(double[][] A, double[] B) {
        int N = B.length;
        char param = 's';  // Awali dengan parameter s, t, u, dst
        for (int i = 0; i < N; i++) {
            if (A[i][i] != 0) {
                System.out.print("x" + (i + 1) + " = " + B[i]);
                for (int j = i + 1; j < N; j++) {
                    if (A[i][j] != 0) {
                        System.out.print(" + (" + (-A[i][j]) + ") * x" + (j + 1));
                    }
                }
                System.out.println();
            } else {
                // Jika variabel bebas, tandai dengan parameter s, t, dst.
                System.out.println("x" + (i + 1) + " = " + param);
                param++;
            }
        }
    }

    // Metode Gauss-Jordan yang juga mempertimbangkan solusi parametrik
    public void gaussJordan(double[][] A, double[] B) {
        int N = B.length;
        boolean hasFreeVariables = false;

        for (int k = 0; k < N; k++) {
            int max = k;
            for (int i = k + 1; i < N; i++) {
                if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                    max = i;
                }
            }
            MatrixUtils.swap_rows(A, k, max);
            double temp = B[k];
            B[k] = B[max];
            B[max] = temp;

            double pivot = A[k][k];
            if (pivot == 0) {
                hasFreeVariables = true;
                continue;
            }
            for (int j = 0; j < N; j++) {
                A[k][j] /= pivot;
            }
            B[k] /= pivot;

            for (int i = 0; i < N; i++) {
                if (i != k) {
                    double factor = A[i][k];
                    for (int j = 0; j < N; j++) {
                        A[i][j] -= factor * A[k][j];
                    }
                    B[i] -= factor * B[k];
                }
            }
        }

        if (!hasFreeVariables) {
            System.out.println("Solusi SPL (Gauss-Jordan):");
            for (int i = 0; i < N; i++) {
                System.out.println("x" + (i + 1) + " = " + B[i]);
            }
        } else {
            System.out.println("Solusi dalam bentuk parametrik (Gauss-Jordan):");
            printParametricSolution(A, B);
        }
    }

    // Metode Cramer
    public double[] cramer(Matriks A, double[] B) {
        int N = A.getRow();
        double[] solusi = new double[N];
        double detA = Determinan.determinanEkspansiKofaktor(A);

        if (detA == 0) {
            System.out.println("Sistem tidak memiliki solusi unik (determinannya 0)");
            return null;
        }

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
            solusi[i] = detTemp / detA;
        }

        System.out.println("Solusi SPL (Cramer):");
        for (int i = 0; i < N; i++) {
            System.out.println("x" + (i + 1) + " = " + solusi[i]);
        }

        return solusi;
    }

    // Metode Matriks Invers yang mempertimbangkan solusi parametrik
    public static double[] splbalikan(Matriks A, double[] B) {
        int n = A.getRow();
        if (n != A.getCol()) {
            throw new IllegalArgumentException("Matriks harus persegi untuk menggunakan metode invers.");
        }

        double[][] inversA = Inverse.invers(A);
        double[] solusi = new double[n];
        for (int i = 0; i < n; i++) {
            solusi[i] = 0;
            for (int j = 0; j < n; j++) {
                solusi[i] += inversA[i][j] * B[j];
            }
        }

        // Cetak solusi
        System.out.println("Solusi SPL (Metode Matriks Invers):");
        for (int i = 0; i < n; i++) {
            System.out.println("x" + (i + 1) + " = " + solusi[i]);
        }

        return solusi;
    }
}