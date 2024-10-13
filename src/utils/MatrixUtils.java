package utils;

public class MatrixUtils {
    
    // Fungsi untuk menukar dua baris dalam matriks
    public static void swap_rows(double[][] mat, int row1, int row2) {
        for (int i = 0; i < mat[0].length; i++) { // mat[0].length = jumlah kolom
            double temp = mat[row1][i];
            mat[row1][i] = mat[row2][i];
            mat[row2][i] = temp;
        }
    }   

    // Fungsi untuk mengalikan satu baris dengan skalar
    public static void multiply_row(double[][] mat, int row, double scal) {
        for (int i = 0; i < mat[0].length; i++) {
            mat[row][i] *= scal;
        }
    }

    // Fungsi untuk menambahkan satu baris ke baris lain dengan skalar
    public static void add_other_row(double[][] mat, int row, int added_row, double scal) {
        for (int i = 0; i < mat[0].length; i++) {
            mat[row][i] += scal * mat[added_row][i];
        }
    }
}


// FileHandler m = new FileHandler("path/to/matrix_file.txt");
// MatrixUtils.swap_rows(m.mat, 0, 1);