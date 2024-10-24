package algebra;

import java.io.FileNotFoundException;
import java.util.Scanner;
import utils.FileHandler;

public class Matriks {
    public int row;
    public int col;
    public double[][] mat;
    public double [] xToPredict;

    // Konstruktor matriks dari file
    // Konstruktor matriks dari file, menggunakan FileHandler
    public Matriks(String filePath) throws FileNotFoundException {
        FileHandler fileHandler = new FileHandler(filePath); // Gunakan FileHandler untuk membaca file
        this.row = fileHandler.row;  // Ambil jumlah baris dari FileHandler
        this.col = fileHandler.col;  // Ambil jumlah kolom dari FileHandler
        this.mat = fileHandler.mat;  // Ambil elemen matriks dari FileHandler
        this.xToPredict = fileHandler.xToPredict;
    }

    // Konstruktor matriks tanpa input (untuk membuat kofaktor atau submatriks)
    public Matriks(int row, int col, boolean isEmpty) {
        this.row = row;
        this.col = col;
        this.mat = new double[row][col]; // Inisialisasi matriks kosong
    }

    // Konstruktor matriks dari input (keyboard)
    public Matriks(int row, int col, int code) {
        this.row = row;
        this.col = col;
        this.mat = new double[row][col];
        Scanner input = new Scanner(System.in);

        System.out.println("Masukkan elemen-elemen matriks:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("Elemen [" + i + "][" + j + "]: ");
                while (!input.hasNextDouble()) {
                    System.out.println("Harap masukkan angka.");
                    input.next(); // Buang input yang tidak valid
                }
                this.mat[i][j] = input.nextDouble();
            }
        }
        if (code==5){
            System.out.println("Masukkan elemen x fungsi yang dicari:");
            this.xToPredict = new double[2];
            for(int i=0; i<2; i++){
                System.out.print("Elemen [" + i + "]: ");
                while (!input.hasNextDouble()) {
                    System.out.println("Harap masukkan angka.");
                    input.next(); // Buang input yang tidak valid
                }
                this.xToPredict[i] = input.nextDouble();
            }
        } else if (code==6){
            System.out.println("Masukkan elemen x fungsi yang dicari:");
            this.xToPredict = new double[this.col-1];
            for(int i=0; i<xToPredict.length; i++){
                System.out.print("X [" + i + "]: ");
                while (!input.hasNextDouble()) {
                    System.out.println("Harap masukkan angka.");
                    input.next(); // Buang input yang tidak valid
                }
                this.xToPredict[i] = input.nextDouble();
            }
        }
        
    }

    // Menulis matriks ke konsol
    public void tulisMatriks() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.print(this.mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Kofaktor
    public Matriks kofaktor(int p, int q) {
        Matriks submatriks = new Matriks(row - 1, col-1, 1);
        int row = 0, col = 0;

        // Loop untuk membentuk submatriks dengan mengabaikan baris ke-p dan kolom ke-q
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i != p && j != q) {
                    submatriks.mat[row][col++] = mat[i][j];

                    // Pindah ke baris berikutnya saat kolom sudah penuh
                    if (col == row - 1) {
                        col = 0;
                        row++;
                    }
                }
            }
        }
        return submatriks;
    }

    // Submatriks untuk kofaktor
    public Matriks submatriks(int rowExclude, int colExclude) {
        Matriks submat = new Matriks(this.row - 1, this.col - 1, true); // Pakai konstruktor baru
        int subi = 0;
        for (int i = 0; i < this.row; i++) {
            if (i == rowExclude) continue;
            int subj = 0;
            for (int j = 0; j < this.col; j++) {
                if (j == colExclude) continue;
                submat.mat[subi][subj] = this.mat[i][j];
                subj++;
            }
            subi++;
        }
        return submat;
    }

    public double getElement(int i, int j) {
        return this.mat[i][j];
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
