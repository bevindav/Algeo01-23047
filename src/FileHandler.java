import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileHandler {
    public int row = 0;
    public int col = 0;
    public double [][] mat;

    public FileHandler(String filePath) throws FileNotFoundException{
        File file = new File(filePath);
        Scanner scanFile = new Scanner(file);
        while (scanFile.hasNextLine()) {
            String line = scanFile.nextLine();
            Scanner currentCol = new Scanner(line);
            int colCount = 0;

            while (currentCol.hasNextDouble()){
                colCount++; // Menambahkan jumlah kolom
                currentCol.nextDouble();
            }
            currentCol.close();

            if (this.row == 0){
                this.col = colCount;
            }
            this.row++; // Menambahkan jumlah baris

        }   
        this.mat = new double[this.row][this.col];

        // Membaca elemen-elemen matriks dari file
        Scanner scanInsert = new Scanner(file);
        for (int i = 0; i < this.row; i ++){
            for (int j = 0; j < this.col; j ++){
                if (scanInsert.hasNextDouble()) {
                    this.mat[i][j] = scanInsert.nextDouble(); // Baca elemen matriks
                }
            }
        }
        scanInsert.close();
        scanFile.close(); // Tutup scanner
    }
}



