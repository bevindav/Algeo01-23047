package utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public int row = 0;
    public int col = 0;
    public double[][] mat;
    public double [] xToPredict ; 

    public FileHandler(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanFile = new Scanner(file);
        boolean isPredict = false;
        // Hitung jumlah baris dan kolom
        while (scanFile.hasNextLine()) {
            String line = scanFile.nextLine();
            Scanner currentCol = new Scanner(line);
            int colCount = 0;

            while (currentCol.hasNextDouble()) {
                colCount++; // Menghitung jumlah kolom
                currentCol.nextDouble();
            }
            currentCol.close();

            if (this.row == 0) {
                this.col = colCount; // Menetapkan jumlah kolom berdasarkan baris pertama
            } else {
                if(colCount<this.col){
                    isPredict = true;
                }
            }
            this.row++; // Menambah jumlah baris
        }
        scanFile.close();

        // Alokasikan array untuk menyimpan data matriks
        if (isPredict){
            this.mat = new double[this.row - 1][this.col]; // Alokasikan matriks dengan satu baris lebih sedikit
        } else {
            this.mat = new double[this.row][this.col];
        }
        Scanner scanInsert = new Scanner(file);

        // Membaca data dari file
        for (int i = 0; i < this.row; i++) {
            String line = scanInsert.nextLine();
            Scanner currentCol = new Scanner(line);
            int colCount = 0;

            // Hitung jumlah kolom di baris saat ini
            while (currentCol.hasNextDouble()) {
                colCount++;
                currentCol.nextDouble();
            }

            currentCol.close();
            currentCol = new Scanner(line); // Buat ulang Scanner untuk membaca ulang elemen di baris
            if (i == this.row - 1 && colCount < this.col){
                this.xToPredict = new double[colCount];
                    for (int j=0; j<colCount; j++){
                        this.xToPredict[j] = currentCol.nextDouble();
                    }
            } else {
                for (int j = 0; j < colCount; j++){
                    this.mat[i][j] = currentCol.nextDouble();
                }
            }
            currentCol.close();
        }
        scanInsert.close();
    }

    public static void FileWriter(String filePath, String string) throws IOException {
        FileWriter file = new FileWriter(filePath);
        file.write(string);
        file.close();
        System.out.println("Penulisan sukses.");
    }
}
