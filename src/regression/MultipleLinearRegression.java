package regression;

import algebra.SPL;

public class MultipleLinearRegression {

    // Fungsi untuk mengubah input menjadi format Matriks siap untuk dihitung
    public static double[][] ubahInputMenjadiMatriks(double[][] titikSampel) {
        int n = titikSampel.length; // jumlah sampel
        double[][] matX = new double[n][2]; // Membuat matriks koefisien X dengan kolom 1 untuk β0

        for (int i = 0; i < n; i++) {
            matX[i][0] = 1;  // Kolom pertama selalu 1 (untuk β0)
            matX[i][1] = titikSampel[i][0];  // Kolom kedua berisi nilai x dari sampel
        }
        return matX;
    }

    // Fungsi untuk memisahkan Y dari sampel
    public static double[] pisahkanNilaiY(double[][] titikSampel) {
        int n = titikSampel.length;
        double[] Y = new double[n];
        for (int i = 0; i < n; i++) {
            Y[i] = titikSampel[i][1];  // Ambil nilai y dari tiap sampel
        }
        return Y;
    }

    // Fungsi regresi linear menggunakan Gauss
    public static double[] regresiLinearGauss(double[][] titikSampel) {
        double [][] X = ubahInputMenjadiMatriks(titikSampel);
        double[] Y = pisahkanNilaiY(titikSampel);
        SPL solver = new SPL();
        solver.gauss(X, Y);  // Menggunakan eliminasi Gauss untuk SPL
        return Y;  // Y berisi koefisien β setelah eliminasi Gauss
    }


    // Fungsi untuk prediksi nilai y baru berdasarkan x baru
    public static double prediksi(double[] beta, double xBaru) {
        return beta[0] + beta[1] * xBaru;  // Menggunakan β0 + β1 * xBaru
    }

    public static String StringHasil (double[] hasilReg, double pred, double xBaru){
        StringBuilder buffer = new StringBuilder(); // Membuat string baru
        buffer.append("f(x) = "+hasilReg[0]+" + "+hasilReg[1]+" x, f("+xBaru+") = "+pred);
        return buffer.toString();

    }

    // public static void main(String[] args) {
    //     // Titik-titik sampel yang diketahui (x, y)
    //     double[][] titikSampel = {
    //         {1, 2},  // x = 1, y = 2
    //         {2, 4},  // x = 2, y = 4
    //         {4, 5}   // x = 4, y = 5
    //     };

    //     // Nilai-nilai x yang ingin diprediksi
    //     double[] xBaru = {5, 6, 7};

    //     // Ubah input menjadi matriks X dan vektor Y
    //     double[][] X = ubahInputMenjadiMatriks(titikSampel);
    //     double[] Y = pisahkanNilaiY(titikSampel);

    //     // Hitung koefisien regresi menggunakan eliminasi Gauss
    //     double[] betaGauss = regresiLinearGauss(X, Y);
    //     System.out.println("Persamaan regresi (Gauss): y = " + betaGauss[0] + " + " + betaGauss[1] + "x");

    //     // Hitung koefisien regresi menggunakan eliminasi Gauss-Jordan
    //     double[] betaGaussJordan = regresiLinearGaussJordan(X, Y);
    //     System.out.println("Persamaan regresi (Gauss-Jordan): y = " + betaGaussJordan[0] + " + " + betaGaussJordan[1] + "x");

    //     // Hitung koefisien regresi menggunakan metode Cramer
    //     double[] betaCramer = regresiLinearCramer(X, Y);
    //     System.out.println("Persamaan regresi (Cramer): y = " + betaCramer[0] + " + " + betaCramer[1] + "x");

    //     // Hitung koefisien regresi menggunakan metode Invers
    //     double[] betaInverse = regresiLinearInverse(X, Y);
    //     System.out.println("Persamaan regresi (Invers): y = " + betaInverse[0] + " + " + betaInverse[1] + "x");

    //     // Prediksi nilai y untuk nilai x baru
    //     for (double x : xBaru) {
    //         double yPrediksi = prediksi(betaGauss, x);
    //         System.out.println("Nilai prediksi y untuk x = " + x + " (Gauss): " + yPrediksi);
        
    
}
