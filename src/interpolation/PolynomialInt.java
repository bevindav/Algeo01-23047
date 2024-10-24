package interpolation;
import algebra.SPL;

public class PolynomialInt {
    public static double interpolasi(double[][] titik, double[] xToPred) {
        int n = titik.length - 1; // Derajat polinom berdasarkan jumlah titik yang diberikan
        double[][] A = new double[n + 1][n + 1]; // Matriks untuk menyimpan koefisien
        double[] B = new double[n + 1]; // Array untuk menyimpan hasil

        // Mengisi matriks A dan vektor B berdasarkan titik yang diberikan
        for (int i = 0; i <= n; i++) {
            double x = titik[i][0];
            double y = titik[i][1];
            B[i] = y;

            // Mengisi baris ke-i pada matriks A
            for (int j = 0; j <= n; j++) {
                A[i][j] = Math.pow(x, j); // Mengisi dengan x^j
            }
        }

        // Menyelesaikan SPL dengan metode Gauss untuk mendapatkan koefisien polinomial
        SPL solver = new SPL();
        double[] solusi = solver.gauss(A, B); 

        // Membuat persamaan polinomial
        StringBuilder persamaan = new StringBuilder();
        persamaan.append("Persamaan polinomial: y = ");
        for (int i = 0; i <= n; i++) {
            if (i > 0 && solusi[i] >= 0) {
                persamaan.append(" + ");
            } else if (solusi[i] < 0) {
                persamaan.append(" ");
                solusi[i] = solusi[i]; 
            }
            persamaan.append(solusi[i]);
            if (i > 0) {
                persamaan.append("x");
                if (i > 1) {
                    persamaan.append("^").append(i);
                }
            }
        }
        System.out.println(persamaan.toString());

        // Menghitung nilai polinom di x yang diestimasi
        double YPred = 0;
        for (int i = 0; i <= n; i++) {
            YPred += solusi[i] * Math.pow(xToPred[0], i);
        }

        // Mengembalikan hasil prediksi
        return YPred;
    }
}