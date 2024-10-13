package algebra;

public class Determinan {

    // Ekspansi kofaktor --> pilih mau baris/kolom
    public static double determinanEkspansiKofaktor(Matriks m) {
        int n = m.getRow(); // Mendapatkan ukuran matriks
    
        // Jika matriks berukuran 1x1, kembalikan elemen tersebut
        if (n == 1) {
            return m.getElement(0, 0);
        }
    
        // Jika matriks berukuran 2x2, hitung determinan secara langsung
        if (n == 2) {
            return m.getElement(0, 0) * m.getElement(1, 1) - m.getElement(0, 1) * m.getElement(1, 0);
        }
    
        double det = 0;
    
        // Ekspansi kofaktor pada baris pertama (baris 0)
        for (int j = 0; j < n; j++) {
            // Ambil submatriks yang mengabaikan baris 0 dan kolom j
            Matriks submatriks = m.kofaktor(0, j);
            
            // Hitung kofaktor untuk elemen m(0, j) dan tambahkan ke determinan
            det += Math.pow(-1, j) * m.getElement(0, j) * determinanEkspansiKofaktor(submatriks);
        }
    
        return det;
    }
    

    // Metode untuk menghitung determinan menggunakan reduksi baris
    public static double determinanReduksiBaris(Matriks m) {
        double[][] matriks = m.mat;
        int n = m.getRow();
        double det = 1;

        for (int i = 0; i < n; i++) {
            if (matriks[i][i] == 0) {
                // Lakukan swap baris jika elemen diagonal adalah 0
                boolean swapped = false;
                for (int j = i + 1; j < n; j++) {
                    if (matriks[j][i] != 0) {
                        // Menggunakan metode swap_rows dari MatrixUtils
                        utils.MatrixUtils.swap_rows(matriks, i, j);
                        det *= -1;  // Swap mengubah tanda determinan
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    return 0;  // Jika tidak bisa di-swap, determinan adalah 0
                }
            }
            // Membuat elemen bawah diagonal menjadi 0
            for (int j = i + 1; j < n; j++) {
                double faktor = matriks[j][i] / matriks[i][i];
                for (int k = i; k < n; k++) {
                    matriks[j][k] -= faktor * matriks[i][k];
                }
            }
        }

        // Kalikan elemen diagonal untuk mendapatkan determinan
        for (int i = 0; i < n; i++) {
            det *= matriks[i][i];
        }

        return det;
    }
}
