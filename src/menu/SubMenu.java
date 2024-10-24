package menu;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import algebra.Determinan;
import algebra.Matriks;
import algebra.SPL;
import algebra.Inverse;
import interpolation.BicubicSplineInt;
import interpolation.PolynomialInt;
import regression.MultipleLinearRegression;
import regression.regresiKuadratikBerganda;
import utils.FileHandler;

public class SubMenu {
    public static void AskToWrite(String content) {
        System.out.println("Apakah ingin menulis ke file?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        Scanner input = new Scanner(System.in);
        while (!input.hasNextInt()) {
            System.out.println("Harap masukkan angka.");
            input.next();
        }
        if (input.nextInt()==1){
            Scanner input1 = new Scanner(System.in);
            System.out.println("Masukkan nama file:");
            String filePath = input1.nextLine();
            try {
                FileHandler.FileWriter(filePath, content);
            } catch (IOException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return;
        }
            
        }
    }
    public static int InputChoice() {
        System.out.println("1. Input dari file");
        System.out.println("2. Input secara manual");
        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (!input.hasNextInt()) {
            System.out.println("Harap masukkan angka.");
            input.next();
        }
        choice = input.nextInt();
        return choice;
    }
    public static Matriks MatrixSource(int choice, int code) throws FileNotFoundException{
        Matriks mat;
        if (choice==1){
            Scanner input = new Scanner(System.in);
            System.out.println("Masukkan nama file:");
            String filePath = input.nextLine();

            mat = new Matriks(filePath);

        } else {
            Scanner input = new Scanner(System.in);
            System.out.println("Jumlah baris (m):");
            while (!input.hasNextInt()){
                System.out.println("Jumlah baris (m):");
                input.next();
            }
            int m = input.nextInt();

            Scanner input2 = new Scanner(System.in);
            System.out.println("Jumlah kolom (n):");
            while (!input2.hasNextInt()){
                System.out.println("Jumlah kolom (n):");
                input2.next();
            }
            int n = input2.nextInt();
            mat = new Matriks(m, n, code);
        }
        return mat;
    }

    public static void SPL() {
        Scanner input = new Scanner(System.in);
        System.out.println("Pilih metode penyelesaian SPL:");
        System.out.println("1. Metode Gauss");
        System.out.println("2. Metode Gauss-Jordan");
        System.out.println("3. Metode Cramer");
        System.out.println("4. Metode Matriks Balikan");

        int choice = 0;
        while (!input.hasNextInt()) {
            System.out.println("Harap masukkan angka.");
            input.next();
        }
        choice = input.nextInt();

        int inputChoice = InputChoice();
        Matriks mat;
        try {
            mat = MatrixSource(inputChoice, 1);
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return;
        }

        // Memisahkan matriks augmented
        int n = mat.getRow();
        double[][] A = new double[n][n];
        double[] B = new double[n];

        // Mengisi matriks A dan vektor B langsung dari matriks augmented
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = mat.getElement(i, j);
            }
            B[i] = mat.getElement(i, n);
        }


        // Memanggil metode penyelesaian SPL sesuai pilihan
        SPL solver = new SPL();
        double[] solusi;
        switch (choice) {
            case 1:
                solusi = solver.gauss(A, B);
                if(solver.tidakSolusi){
                    System.out.println("Tidak ada solusi.");
                } else if(solver.parametrik){
                    System.out.print(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                    AskToWrite(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                } else {
                    System.out.println(solver.StringHasil(solusi));
                    AskToWrite(solver.StringHasil(solusi));
                }
                break;
            case 2:
                solusi = solver.gaussJordan(A, B);
                if(solver.tidakSolusi){
                    System.out.println("Tidak ada solusi.");
                } else if(solver.parametrik){
                    System.out.print(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                    AskToWrite(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                } else {
                    System.out.println(solver.StringHasil(solusi));
                    AskToWrite(solver.StringHasil(solusi));
                }
                break;
            case 3:
                solusi = solver.cramer(mat, B);
                if(solver.tidakSolusi){
                    System.out.println("Tidak ada solusi.");
                } else if(solver.parametrik){
                    System.out.print(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                    AskToWrite(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                } else {
                    System.out.println(solver.StringHasil(solusi));
                    AskToWrite(solver.StringHasil(solusi));
                }
                break;
            case 4:
                solusi = SPL.splbalikan(mat);
                if(solver.tidakSolusi){
                    System.out.println("Tidak ada solusi.");
                } else if(solver.parametrik){
                    System.out.print(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                    AskToWrite(solver.ParametrikStringHasil(solver.parametrikA, solver.parametrikB));
                } else {
                    System.out.println(solver.StringHasil(solusi));
                    AskToWrite(solver.StringHasil(solusi));
                }
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                return;
        }
    }

    public static void Determinan() {
        Scanner input = new Scanner(System.in);
        System.out.println("Pilih metode penyelesaian determinan:");
        System.out.println("1. Metode Ekspansi Kofaktor");
        System.out.println("2. Metode Reduksi Baris");

        int choice = 0;
        while (!input.hasNextInt()) {
            System.out.println("Harap masukkan angka.");
            input.next();
        }
        choice = input.nextInt();

        int inputChoice = InputChoice();
        Matriks mat;
        try {
            mat = MatrixSource(inputChoice,2);
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return;
        }

        // Memanggil metode penyelesaian determinan sesuai pilihan
        switch (choice) {
            case 1:
                double det = Determinan.determinanEkspansiKofaktor(mat);
                System.out.println(Determinan.StringHasil(det));
                AskToWrite(Determinan.StringHasil(det));
                break;
            case 2:
                double det2 = Determinan.determinanReduksiBaris(mat);
                System.out.println(Determinan.StringHasil(det2));
                AskToWrite(Determinan.StringHasil(det2));
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                return;
        }
    }

    public static void Inverse(){
        int inputChoice = InputChoice();
        Matriks mat;
        try {
            mat = MatrixSource(inputChoice,3);
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return;
        }

        double[][] inv = Inverse.invers(mat);
        System.out.print(Inverse.StringHasil(inv));
        AskToWrite(Inverse.StringHasil(inv));
    }

    public static void InterpolasiPol() {
        int inputChoice = InputChoice(); // Memilih sumber input untuk data titik
        Matriks m;
        try {
            m = MatrixSource(inputChoice, 4); // Mengambil input matriks titik dengan 3 sebagai contoh baris
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return;
        }
    
        // Meminta pengguna untuk memasukkan nilai x yang ingin diprediksi
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nilai x yang ingin diprediksi: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Harap masukkan angka.");
            scanner.next();
        }
        double xPred = scanner.nextDouble();
        double[] xToPred = {xPred};
    
        // Panggil metode interpolasi untuk mendapatkan hasil prediksi
        double hasilPrediksi = PolynomialInt.interpolasi(m.mat, xToPred);
    
        // Cetak persamaan polinomial dan hasil prediksi
        System.out.println("Hasil prediksi Y pada x = " + xPred + " adalah " + hasilPrediksi);
    }
    public static void InterpolasiBic(){
        int inputChoice = InputChoice();
        Matriks m;
        try {
            m = MatrixSource(inputChoice,5);
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return;
        }
        System.out.println(BicubicSplineInt.StringHasil(m.mat, m.xToPredict[0], m.xToPredict[1]));
        AskToWrite(BicubicSplineInt.StringHasil(m.mat, m.xToPredict[0], m.xToPredict[1]));
    }
    
    public static void Regresi(){
        Scanner input = new Scanner(System.in);
        System.out.println("Pilih metode regresi:");
        System.out.println("1. Linear Berganda");
        System.out.println("2. Kuadratik Berganda");
        while (!input.hasNextInt()) {
            System.out.println("Harap masukkan angka.");
            input.next();
        }
        int choice = input.nextInt();
        int inputChoice = InputChoice();
        Matriks m;
        try {
            m = MatrixSource(inputChoice,6);
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return;
        }
        if (choice==1){
            double [] hasilReg = MultipleLinearRegression.regresiLinearGauss(m.mat);
            double hasil = MultipleLinearRegression.prediksi(hasilReg, m.xToPredict[0]);
            System.out.println(MultipleLinearRegression.StringHasil(hasilReg, hasil, m.xToPredict[0]));
            AskToWrite(MultipleLinearRegression.StringHasil(hasilReg, hasil, m.xToPredict[0]));

        } else if (choice==2){
            for (int a=0;a<m.mat.length;a++){
                for(int b=0;b<m.mat[a].length;b++){
                    System.out.print(m.mat[a][b]+" ");
                }
                System.out.println();
            }
            System.out.println(regresiKuadratikBerganda.StringHasil(m));
            AskToWrite(regresiKuadratikBerganda.StringHasil(m));
        }

    }


}
 