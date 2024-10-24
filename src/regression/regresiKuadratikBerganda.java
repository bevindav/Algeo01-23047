package regression;

import algebra.Matriks;
import algebra.SPL; 
import utils.general;  

public class regresiKuadratikBerganda {

    // Mengubah sampel X menjadi matriks kuadratik
    public static double[][] SampleToMatrix(Matriks file, int SampleNum) {
        int num = file.col - 1; // Jumlah variabel independen
        int total = 1 + 2 * num + general.nCr(num, 2); // Total variabel termasuk interaksi dan kuadrat
        double[][] hasilSample = new double[total][total]; // Matriks kosong ukuran total x total
        
        hasilSample[0][0] = 1; // Elemen pertama adalah konstanta
        
        int i = 1;
        // Mengisi variabel linear
        for (int j = 0; j < num; j++) {
            hasilSample[0][i] = file.mat[SampleNum][j];
            hasilSample[i][0] = file.mat[SampleNum][j];
            i++;
        }
        // Mengisi variabel kuadrat
        for (int j = 0; j < num; j++) {
            hasilSample[0][i] = file.mat[SampleNum][j] * file.mat[SampleNum][j];
            hasilSample[i][0] = file.mat[SampleNum][j] * file.mat[SampleNum][j];
            i++;
        }
        // Mengisi variabel interaksi antara dua variabel
        if (general.nCr(num, 2) == 1) {
            hasilSample[0][i] = file.mat[SampleNum][0] * file.mat[SampleNum][1];
            hasilSample[i][0] = file.mat[SampleNum][0] * file.mat[SampleNum][1];
            i++;
        } else {
            for (int j = 0; j < num - 1; j++) {
                for (int k = j + 1; k < num; k++) {
                    hasilSample[0][i] = file.mat[SampleNum][j] * file.mat[SampleNum][k];
                    hasilSample[i][0] = file.mat[SampleNum][j] * file.mat[SampleNum][k];
                    i++;
                }
            }
        }
        // Mengisi elemen-elemen matriks kuadratik
        for (i = 1; i < total; i++) {
            for (int j = 1; j < total; j++) {
                hasilSample[i][j] = hasilSample[i][0] * hasilSample[0][j];
            }
        }
        return hasilSample;
    }

    // Membuat array dari sampel Y untuk regresi
    public static double[] SampleToYMatrix(Matriks file, int SampleNum) {
        int num = file.col - 1;
        int total = 1 + 2 * num + general.nCr(num, 2);
        double[] hasilSample = new double[total];
        
        hasilSample[0] = file.mat[SampleNum][num]; // Nilai variabel Y
        
        // Mengisi elemen Y dengan matriks yang sudah dibentuk
        for (int i = 1; i < total; i++) {
            hasilSample[i] = SampleToMatrix(file, SampleNum)[i][0] * hasilSample[0];
        }
        return hasilSample;
    }

    // Membuat matriks X untuk regresi kuadratik berganda
    public static double[][] toMatrixRKB(Matriks file) {
        int num = file.col - 1;
        int total = 1 + 2 * num + general.nCr(num, 2); 
        double[][] matX = new double[total][total]; 
        
        // Mengisi matriks X dari sampel
        for (int i = 0; i < file.row; i++) {
            for (int j = 0; j < total; j++) {
                for (int k = 0; k < total; k++) {
                    matX[j][k] += SampleToMatrix(file, i)[j][k];
                }
            }
        }
        double divider = matX[0][0];
        for (int i=0;i<matX.length;i++){
            for (int j=0; j<matX[i].length;j++){
                matX[i][j] /= divider;
            }
        }
        /*for (int i=num;i<matX.length;i++){
            for (int j=0; j<matX[i].length;j++){
                matX[i][j] /= divider;
            }
        }*/
        for (int i=0;i<matX.length;i++){
            for (int j=num; j<matX[i].length;j++){
                matX[i][j] /= divider;
            }
        }
        
        return matX;
    }

    // Membuat matriks Y untuk regresi kuadratik berganda
    public static double[] toMatrixYRKB(Matriks file) {
        int num = file.col - 1;
        int total = 1 + 2 * num + general.nCr(num, 2);
        double[] matY = new double[total];
        
        // Mengisi matriks Y dari sampel
        for (int i = 0; i < file.row; i++) {
            for (int j = 0; j < total; j++) {
                matY[j] += SampleToYMatrix(file, i)[j];
            }
        }
        double divider = matY[0];
        for (int i=0;i<matY.length;i++){
            matY[i] /= divider;
        }
        /*for (int i=num;i<matY.length;i++){
            matY[i] /= divider;
        }*/
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
        int num = xk.length;
        double[] peubah = new double[1 + 2 * num + general.nCr(num, 2)];
        double hasilPrediksi = 0;
        
        int k = 1;
        peubah[0] = 1;
        // Menyusun peubah linear
        for (int j = 0; j < num; j++) {
            peubah[k] = xk[j];
            k++;
        }
        // Menyusun peubah kuadrat
        for (int j = 0; j < num; j++) {
            peubah[k] = xk[j] * xk[j];
            k++;
        }
        // Menyusun peubah interaksi
        for (int j = 0; j < general.nCr(num, 2) - 1; j++) {
            for (int l = j + 1; l < general.nCr(num, 2); l++) {
                peubah[k] = xk[j] * xk[l];
                k++;
            }
        }
        // Menghitung prediksi dengan koefisien yang telah diperoleh
        for (int m = 0; m < peubah.length; m++) {
            hasilPrediksi += hasilSPL[m] * peubah[m];
        }
        return hasilPrediksi;
    }

    // Metode untuk mencetak hasil regresi kuadratik berganda ke output
    public static String StringHasil(Matriks file) {
        int j;
        int num = file.col - 1; // Jumlah variabel independen (kolom data - 1 untuk variabel Y)
        int[][] KombinasiPeubah = general.nCrMember(num, 2); // Kombinasi dua variabel untuk interaksi
        double[] koefisien = gaussRKB(file); // Menghitung koefisien regresi dengan eliminasi Gauss
        StringBuilder buffer = new StringBuilder(); // Membuat string baru
        int i = 1;
        // Menambahkan variabel linear x1, x2, ..., xn ke string
        for (j = 1; j <= num; j++) {
            buffer.append("x" + i + " = x" + j+"\n");
            i++;
        }
        // Menambahkan variabel kuadrat x1^2, x2^2, ..., xn^2 ke string
        for (j = 1; j <= num; j++) {
            buffer.append("x" + i + " = x" + j + "^2\n");
            i++;
        }
        // Menambahkan variabel interaksi (kombinasi dua variabel) ke string
        for (j = 0; j < general.nCr(num, 2); j++) {
            buffer.append("x" + i + " = x" + KombinasiPeubah[j][0] + " * x" + KombinasiPeubah[j][1]+"\n");
            i++;
        }

        // Menambahkan fungsi regresi dengan koefisien yang sudah dihitung
        buffer.append("f(x) = " + koefisien[0]); // Konstanta
        // Menambahkan sisa koefisien untuk variabel
        for (j = 1; j < koefisien.length; j++) {
            if(koefisien[j]>=0){
                buffer.append(" +" + koefisien[j] + "x" + j);
            } else {
                buffer.append(" " + koefisien[j] + "x" + j);
            }
        }
        buffer.append(", f(xk) = "+prediksi(koefisien, file.xToPredict));
        return buffer.toString(); // Mengembalikan string keseluruhan
    }
}


