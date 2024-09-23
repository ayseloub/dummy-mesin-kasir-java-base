import java.util.Scanner;

import com.config.cConfig;
import com.view.cView;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        
        
        while (true) {
            System.out.println("=== Daftar Menu ===\n" +"0. Exit\n" + "1. Lihat Daftar Menu\n" + "2. Lihat Semua Transaksi\n" + "3. Lihat Total Belanja Pelanggan\n" + "4. Cetak Struk\n" + "5. Buat Pesanan Baru" );

            int pilihan = input.nextInt();
            input.nextLine();

            if( pilihan == 0){
                System.out.println("Terimakasih!");
                break;

            }

            switch (pilihan) {
                case 1 :
                    cView.getMenu();
                    break;
                
                case 2 :
                    cView.getAllNotaResto();
                    break;

                case 3 :
                    cView.getTotalBelanja();
                    break;

                case 4 :
                    cView.getCetakStruk();
                    break;

                case 5 :
                    cView.getPesananBaru();
                    break;
            
                default:
                    break;
            }

        }

        input.close();

    }
}
