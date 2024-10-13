package algebra;

public class Inverse {
    // Metode untuk menghitung transpose matriks
    public static double[][] transpose(double[][] matriks) {
        int row = matriks.length;       // Jumlah baris
        int col = matriks[0].length;    // Jumlah kolom
        double[][] transpose = new double[col][row];  // Matriks hasil transpose
        
        // Loop untuk menukar baris menjadi kolom
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                transpose[j][i] = matriks[i][j];  // Menukar elemen baris menjadi kolom
            }
        }
        
        return transpose;
    }
   
    // Metode untuk menghitung invers dari sebuah matriks
    public static double[][] invers(Matriks m) {
        int n = m.getRow();
        double det = Determinan.determinanReduksiBaris(m);  // Menggunakan metode determinan dari Determinan.java
    
        if (det == 0) {
            throw new ArithmeticException("Matriks tidak memiliki invers");
        }

        // Matriks untuk menampung adjoint
        Matriks adj = new Matriks(n, n, true);

        // Menghitung kofaktor untuk setiap elemen
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Menggunakan metode kofaktor dari kelas Matriks
                Matriks submatriks = m.submatriks(i, j);  // Mendapatkan submatriks untuk kofaktor
                double kofaktor = Determinan.determinanEkspansiKofaktor(submatriks);  // Hitung determinan submatriks
                adj.mat[i][j] = Math.pow(-1, i + j) * kofaktor;  // Simpan kofaktor ke dalam matriks adj
            }
        }

        // Transpose adjoint
        double[][] adjTranspose = transpose(adj.mat);

        // Matriks untuk menampung invers
        double[][] invers = new double[n][n];
    
        // Menghitung invers dengan membagi setiap elemen dengan determinan
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invers[i][j] = adjTranspose[i][j] / det;  // Elemen invers = adjTranspose / determinan
            }
        }
    
        return invers;
    }
}
