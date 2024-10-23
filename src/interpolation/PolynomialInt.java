package interpolation;
import algebra.SPL;

public class PolynomialInt {

    // Fungsi interpolasi menggunakan eliminasi Gauss
    public static double interpolasiGauss(double[][] A, double[] B, double titik) {
        SPL solver = new SPL(); // objek dari kelas SPL
        solver.gauss(A, B); // Menyelesaikan SPL dengan eliminasi Gauss

        // Menampilkan persamaan polinomial yang diperoleh
        System.out.println("Persamaan polinomial: y = " + B[0] + " + " + B[1] + "x + " + B[2] + "x^2");

        // Menghitung nilai polinom di x yang diestimasi
        return B[0] + B[1] * titik + B[2] * Math.pow(titik, 2);
    }

    // Fungsi interpolasi menggunakan eliminasi Gauss-Jordan
    public static double interpolasiGaussJordan(double[][] A, double[] B, double titik) {
        SPL solver = new SPL();
        solver.gaussJordan(A, B); // Menyelesaikan SPL dengan eliminasi Gauss-Jordan

        // Menampilkan persamaan polinomial yang diperoleh
        System.out.println("Persamaan polinomial: y = " + B[0] + " + " + B[1] + "x + " + B[2] + "x^2");

        // Menghitung nilai polinom di x yang diestimasi
        return B[0] + B[1] * titik + B[2] * Math.pow(titik, 2);
    }

}
