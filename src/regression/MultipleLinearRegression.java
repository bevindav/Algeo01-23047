package regression;

import algebra.Matriks;
import algebra.SPL;

public class MultipleLinearRegression {

    public static double[][] SampleToMatrix(Matriks file, int SampleNum) {
        int num = file.col - 1;
        double[][] hasilSample = new double[1+num][1+num]; // Matriks kosong
        
        hasilSample[0][0] = 1; // Elemen pertama adalah konstanta
        
        // Mengisi variabel linear
        for (int j = 1; j < 1+num; j++) {
            hasilSample[0][j] = file.mat[SampleNum][j-1];
            hasilSample[j][0] = file.mat[SampleNum][j-1];
        }
        // Mengisi elemen-elemen matriks kuadratik
        for (int i = 1; i < 1+num; i++) {
            for (int j = 1; j < 1+num; j++) {
                hasilSample[i][j] = hasilSample[i][0] * hasilSample[0][j];
            }
        }
        return hasilSample;
    }

    // Membuat array dari sampel Y untuk regresi
    public static double[] SampleToYMatrix(Matriks file, int SampleNum) {
        int num = file.col - 1;
        double[] hasilSample = new double[1+num]; // Matriks kosong
        
        hasilSample[0] = file.mat[SampleNum][num]; // Elemen pertama adalah konstanta
        
        // Mengisi elemen matriks
        for (int j = 1; j < 1+num; j++) {
            hasilSample[j] = SampleToMatrix(file, SampleNum)[j][0]*hasilSample[0];
        }
        return hasilSample;
    }

    // Membuat matriks X untuk regresi kuadratik berganda
    public static double[][] toMatrixRKB(Matriks file) {
        int num = file.col - 1;
        double[][] matX = new double[1+num][1+num]; 
        
        // Mengisi matriks X dari sampel
        for (int i = 0; i < file.row; i++) {
            for (int j = 0; j < 1+num; j++) {
                for (int k = 0; k < 1+num; k++) {
                    matX[j][k] += SampleToMatrix(file, i)[j][k];
                }
            }
        }
        double divider = matX[0][0];
        for (int i=0; i<matX.length;i++){
            for(int j=0;j<matX[i].length;j++){
                matX[i][j] /= divider;
            }
        }
        return matX;
    }

<<<<<<< HEAD
    // Membuat matriks Y untuk regresi kuadratik berganda
    public static double[] toMatrixYRKB(Matriks file) {
        int num = file.col - 1;
        double[] matY = new double[1+num];
=======
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
        return beta[0] + beta[1] * xBaru;  
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
>>>>>>> d3951f12025a156b884f1a589bf582206439ef30
        
        // Mengisi matriks Y dari sampel
        for (int i = 0; i < file.row; i++) {
            for (int j = 0; j < 1+num; j++) {
                matY[j] += SampleToYMatrix(file, i)[j];
            }
        }
        double divider = matY[0];
        for (int i = 0; i < matY.length; i++) {
            matY[i] /= divider;
        }
        return matY;
    }

    // Menggunakan eliminasi Gauss untuk menyelesaikan SPL dan mendapatkan koefisien β
    public static double[] gaussRKB(Matriks file) {
        SPL solver = new SPL();
        double[][] x = toMatrixRKB(file);
        double[] y = toMatrixYRKB(file);
        solver.gauss(x, y); // Menyelesaikan SPL dengan eliminasi Gauss
        return y; // Y berisi koefisien β setelah eliminasi Gauss
    }

    // Metode untuk memprediksi hasil Y berdasarkan variabel input xk dan hasil SPL
    public static double prediksi(double[] hasilSPL, double[] xk) {
        int num = hasilSPL.length;
        double hasilPrediksi = hasilSPL[0];
        for (int i=1;i<num;i++){
            hasilPrediksi += hasilSPL[i]*xk[i-1];
        }
        return hasilPrediksi;
    }

    public static String StringHasil(Matriks file){
        int num = file.col-1;
        double [] koef = gaussRKB(file);
        StringBuilder buffer = new StringBuilder();
        buffer.append("f(x) = "+koef[0]);
        for (int j=1; j<koef.length;j++){
            if (koef[j]>=0){
                buffer.append(" +"+koef[j]+"x"+j);
            } else{
                buffer.append(" "+koef[j]+"x"+j);
            }
        }
        buffer.append(", f(xk) = "+prediksi(koef, file.xToPredict));
        return buffer.toString();
    }
}