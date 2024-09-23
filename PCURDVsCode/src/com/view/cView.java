package com.view;
import com.config.cConfig;
import java.lang.String;

import java.util.Scanner;

public class cView {
    private static Scanner input = new Scanner(System.in);
    
    public static void getMenu(){

        System.out.println("=== Daftar Menu ===");

        System.out.println(cConfig.getMenu());

        
    }
    
    public static void getAllNotaResto(){

        System.out.println("=== Daftar nota Toko===");

        System.out.println(cConfig.getAllNotaResto());

        
    }
    
    public static void getTotalBelanja(){

        System.out.println("=== Daftar nota Pelanggan ===");

        System.out.println(cConfig.getTotalBelanja());

        
    }

    public static void getCetakStruk(){

        System.out.println("=== Cetak Struk Belanja ===");

        System.out.print("nama Pelanggan: ");

        String nama = input.nextLine();
        
        System.out.println(cConfig.getCetakStruk(nama));

    }

    public static void getPesananBaru(){
        int i = 1;
        String pilihan = "Y";

        System.out.println("=== Pesanan Baru ===");

        System.out.print("nama Pelanggan: ");

        String nama = input.nextLine();

        System.out.print("No Handphone: ");

        int nohp = input.nextInt();
        input.nextLine();

        System.out.println(cConfig.getnewcust(nama, nohp));

        System.out.println(cConfig.getnewOrder(nama, nohp));

        while (pilihan == "Y") {
            cConfig.getMenu();
            System.out.println("masukan menu -" + i);
            System.out.print("nomor menu: ");
            int menu = input.nextInt();
            input.nextLine();
            System.out.print("Jumlah: ");
            int jumlah = input.nextInt();
            input.nextLine();

            System.out.println(cConfig.getcustmenu(nama, menu, jumlah));

            System.out.println("apakah ingin menambah pesanan lagi?(Y/N)");
            pilihan = input.nextLine();
            i++;
            
        }

    }

}
