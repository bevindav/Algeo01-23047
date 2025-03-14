package algebra;

import utils.MatrixUtils;
public class SPL {
    public boolean parametrik; 
    public double[][] parametrikA;
    public double[] parametrikB;
    public boolean tidakSolusi;

   // Tambahan pengecekan untuk kasus tidak ada solusi
   public double[] gauss(double[][] A, double[] B) {
    int N = B.length;
    boolean adaVariabelBebas = false; // Flag untuk cek variabel bebas
    boolean tidakAdaSolusi = false;   // Flag untuk cek jika tidak ada solusi
    double[] solusi = new double[N];

    // Proses eliminasi Gauss
    for (int k = 0; k < N; k++) {
        int max = k;
        // Cari baris dengan elemen maksimum di kolom ini untuk pivot
        for (int i = k + 1; i < N; i++) {
            if (Math.abs(A[i][k]) > Math.abs(A[max][k])) {
                max = i;
            }
        }

        // Tukar baris k dengan baris yang memiliki elemen maksimum
        MatrixUtils.swap_rows(A, k, max);
        double temp = B[k];
        B[k] = B[max];
        B[max] = temp;

        // Cek jika elemen pivot adalah nol
        if (Math.abs(A[k][k]) < 1e-9) {
            // Jika elemen pivot adalah nol dan nilai B[k] tidak nol, maka tidak ada solusi
            if (Math.abs(B[k]) > 1e-9) {
                tidakAdaSolusi = true;
                break;
            }
            adaVariabelBebas = true; // Jika elemen pivot dan B[k] sama-sama nol, ada variabel bebas
            continue;
        }

        // Lakukan eliminasi baris ke bawah
        for (int i = k + 1; i < N; i++) {
            double faktor = A[i][k] / A[k][k];
            B[i] -= faktor * B[k];
            for (int j = k; j < N; j++) {
                A[i][j] -= faktor * A[k][j];
            }
        }
    }

    // Jika tidak ada solusi, return null
    if (tidakAdaSolusi) {
        this.tidakSolusi = true;
        return null; // Tidak ada solusi
    }

    // Lakukan substitusi balik jika tidak ada variabel bebas
    if (!adaVariabelBebas) {
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A[i][j] * solusi[j];
            }
            solusi[i] = (B[i] - sum) / A[i][i];
        }
    } else {
        this.parametrikA = A;
        this.parametrikB = B;
        solusi = getParametricSolution(A, B); // Jika ada variabel bebas, hitung solusi parametrik
    }

    return solusi; // Return solusi
}


private double[] getParametricSolution(double[][] A, double[] B) {
    this.parametrik = true;
    int N = B.length;
    double[] solusi = new double[N];

    // Kasih nilai variabel bebas (misal 0 buat sementara) dan tanda dengan parameter
    for (int i = 0; i < N; i++) {
        if (A[i][i] != 0) {
            solusi[i] = B[i];  // Variabel dengan koefisien tidak nol
        } else {
            // Tandai variabel bebas dengan nilai NaN untuk dideteksi di StringHasil
            solusi[i] = Double.NaN;
        }
    }
    return solusi;
}

// Modifikasi StringHasil untuk mendeteksi parameter
public String ParametrikStringHasil(double[][] A, double[] B) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("Solusi dalam bentuk parametrik:\n");
    int N = B.length;
    char param = 's';  // Awali dengan parameter s, t, u, dst

    for (int i = 0; i < N; i++) {
        if (A[i][i] != 0) {
            // Jika hasil tidak nol, tulis x(i+1) = B[i] + ...
            buffer.append("x" + (i + 1) + " = " + B[i]);
            for (int j = i + 1; j < N; j++) {
                if (A[i][j] != 0) {
                    buffer.append(" + (" + (-A[i][j]) + ") * " + param);
                }
            }
            buffer.append("\n");
        } else {   
            // Jika variabel bebas, tandai dengan parameter s, t, dst.
            buffer.append("x" + (i + 1) + " = " + param + "\n");
            param++;  // Pindah ke parameter berikutnya
        }
    }
    return buffer.toString();
}

public String StringHasil(double[] solusi){
    StringBuilder buffer = new StringBuilder(); // Membuat string baru
    buffer.append("Solusi SPL:\n");
    for(int i=0; i<solusi.length; i++){
        buffer.append("x"+(i+1)+" = "+solusi[i]+"\n");
    }
    return buffer.toString();
}

    // Metode Gauss-Jordan buat nyari solusi SPL
    public double[] gaussJordan(double[][] A, double[] B) {
        int N = B.length;
        boolean tidakAdaSolusi = false;   // Flag untuk cek jika tidak ada solusi
        boolean adaVariabelBebas = false;
        double[] solusi = new double[N];

        // Proses eliminasi Gauss-Jordan
        for (int k = 0; k < N; k++) {
            int max = k;
            // Mencari elemen maksimum di kolom
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

            // Cek jika elemen pivot adalah nol
            // Normalisasi pivot
            double pivot = A[k][k];

            if (Math.abs(pivot) < 1e-9) {
                // Jika elemen pivot adalah nol dan nilai B[k] tidak nol, maka tidak ada solusi
                if (Math.abs(B[k]) > 1e-9) {
                    tidakAdaSolusi = true;
                    break;
                }
                adaVariabelBebas = true; // Jika elemen pivot dan B[k] sama-sama nol, ada variabel bebas
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
        if (tidakAdaSolusi) {
            this.tidakSolusi = true;
            return null; // Tidak ada solusi
        }

        // Kalau tidak ada variabel bebas, hitung solusinya
        if (!adaVariabelBebas) {
            for (int i = 0; i < N; i++) {
                solusi[i] = B[i];
            }
        } else {
            solusi = getParametricSolution(A, B);
        }

        return solusi;
    }

    // Metode Cramer (kalau bisa dihitung determinannya)
    public double[] cramer(Matriks A, double[] B) {
        int N = A.getRow();
        double[] solusi = new double[N];
        double detA = Determinan.determinanEkspansiKofaktor(A);
    
        if (Math.abs(detA) < 1e-9) {
            tidakSolusi = true;
            return null;  // Jika determinan nol atau sangat kecil, sistem tidak memiliki solusi unik
        }
    
        // Ganti kolom satu per satu dengan B, lalu hitung determinannya
        for (int i = 0; i < N; i++) {
            // Buat matriks sementara untuk menggantikan kolom
            Matriks temp = new Matriks(A.getRow(), A.getCol(), true);
    
            // Salin elemen-elemen dari matriks A ke matriks temp
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    temp.mat[r][c] = A.mat[r][c];
                }
            }
    
            // Ganti kolom ke-i di matriks sementara dengan elemen-elemen vektor B
            for (int j = 0; j < N; j++) {
                temp.mat[j][i] = B[j];
            }
    
            // Hitung determinan dari matriks sementara
            double detTemp = Determinan.determinanEkspansiKofaktor(temp);
            solusi[i] = detTemp / detA;  // Solusi variabel ke-i
        }
    
        return solusi;
    }

    // Metode Invers Matriks (buat SPL yang solusinya unik)
    public static double[] splbalikan(Matriks augmented) {
        int n = augmented.getRow();
        int m = augmented.getCol();
    
        // Pastikan matriks augmented memiliki ukuran n x (n+1)
        if (m != n + 1) {
            throw new IllegalArgumentException("Matriks augmented harus berukuran n x (n+1).");
        }
    
        // Buat matriks A dan vektor B
        Matriks A = new Matriks(n, n, true); // Matriks koefisien persegi n x n
        double[] B = new double[n];
    
        // Pisahkan matriks A dan vektor B dari matriks augmented
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A.mat[i][j] = augmented.mat[i][j];
            }
            B[i] = augmented.mat[i][n]; // Kolom terakhir adalah vektor B
        }
    
        // Pastikan matriks A adalah matriks persegi
        if (A.getRow() != A.getCol()) {
            throw new IllegalArgumentException("Matriks koefisien A harus persegi.");
        }
    
        // Hitung determinan dari A untuk memastikan matriks dapat diinvers
        double det = Determinan.determinanEkspansiKofaktor(A);
        if (Math.abs(det) < 1e-9) {
            throw new ArithmeticException("Matriks tidak memiliki invers karena determinannya adalah 0.");
        }
    
        // Hitung invers dari matriks A
        double[][] inversA = Inverse.invers(A);
    
        // Inisialisasi array solusi
        double[] solusi = new double[n];
    
        // Hitung solusi dengan mengalikan invers dari A dengan vektor B
        for (int i = 0; i < n; i++) {
            solusi[i] = 0;
            for (int j = 0; j < n; j++) {
                solusi[i] += inversA[i][j] * B[j];
            }
        }
    
        return solusi;
    }
}
