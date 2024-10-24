import menu.SubMenu;
import java.util.Scanner;

public class Main {
    public static boolean keluar = false;

    public static void main(String[] args) {
        System.out.println("=========================");
        System.out.println("|        CEPALTA        |");
        System.out.println("=========================");
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!keluar) {
            System.out.println("===========MAIN==========");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic Spline");
            System.out.println("6. Regresi");
            System.out.println("0. KELUAR");
            Scanner input = new Scanner(System.in);
            while (!input.hasNextInt() && input.nextInt() <= 6) {
                System.out.println("Harap masukkan angka.");
                input.next();
            }
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    SubMenu.SPL();
                    break;
                case 2:
                    SubMenu.Determinan();
                    break;
                case 3:
                    SubMenu.Inverse();
                    break;
                case 4:
                    SubMenu.InterpolasiPol();
                    break;
                case 5:
                    SubMenu.InterpolasiBic();
                    break;
                case 6:
                    SubMenu.Regresi();
                    break;
                default:
                    keluar = true;
                    break;
            }
        }
    }
}
