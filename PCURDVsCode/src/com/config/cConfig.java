package com.config;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


public class cConfig {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restoran";
    private static final String USER = "root";
    private static final String PASS = "";
    
    private static Connection connect;
    private static Statement statement;
    private static ResultSet resultData;

    private static void Connection(){

        try {
            Class.forName(JDBC_DRIVER);

            connect = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("koneksi Berhasil");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static String getMenu(){

        cConfig.Connection();

        String data = "Error dalam Pengambilan data";

        try {

            statement = connect.createStatement();

            String query = "SELECT idMenu, JenisMenu, JumlahStock, NamaMenu, Harga FROM menu"; 

            resultData = statement.executeQuery(query);

            data = "";

            while (resultData.next()) {
                
                data += "| Id: " + resultData.getInt("idMenu") + "|Jenis Menu: " + resultData.getString("JenisMenu") + "| JumlahStock: " + resultData.getString("JumlahStock") + "| Nama Menu: " + resultData.getString("NamaMenu") + "| Harga: Rp. " + resultData.getString("Harga") + " |\n"; 
                
            }
            
                    statement.close();
                    connect.close();
                    
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                
                return data;
                
            }

    public static String getAllNotaResto() {

        cConfig.Connection();
            
        String data = "Error dalam Pengambilan data";
            
        try {
            
            statement = connect.createStatement();
            
            String query = "SELECT pemesanan.idPemesanan, detailpemesanan.idDetailPemesanan, pelanggan.NamaPelanggan, menu.NamaMenu, detailpemesanan.jumlah, CONCAT('RP. ', menu.Harga) AS HargaSatuan, CONCAT('Rp. ', menu.Harga * detailpemesanan.jumlah) AS TotalHarga, CONCAT('Rp. ', (SELECT SUM(menu.Harga * detailpemesanan.jumlah) FROM pemesanan JOIN detailpemesanan ON pemesanan.idPemesanan = detailpemesanan.idPemesanan JOIN menu ON detailpemesanan.idMenu = menu.idMenu)) AS TotalSemua FROM pemesanan JOIN pelanggan ON pemesanan.idPelanggan = pelanggan.idPelanggan JOIN detailpemesanan ON pemesanan.idPemesanan = detailpemesanan.idPemesanan JOIN menu ON detailpemesanan.idMenu = menu.idMenu";
            
            resultData = statement.executeQuery(query);
            
            data = "";
            
                while (resultData.next()) {
            
                    data += "|idPemesanan: " + resultData.getInt("idPemesanan") + "| idDetailPemesanan: " + resultData.getInt("idDetailPemesanan") + "| NamaPelanggan: " + resultData.getString("NamaPelanggan") + "| NamaMenu: " + resultData.getString("NamaMenu") + "| Jumlah: " + resultData.getInt("jumlah") + "| HargaSatuan: " + resultData.getString("HargaSatuan") + "| Total Belanja: " + resultData.getString("TotalSemua") + " |\n";
            
                    }
            
            statement.close();
            connect.close();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
            
        return data;
    }


    public static String getTotalBelanja() {
        cConfig.Connection();
        String data = "Error dalam Pengambilan data";
                
        try {
            statement = connect.createStatement();
                    
            String query = "SELECT\r\n" + 
            "  `pemesanan`.`idPemesanan`   AS `idPemesanan`,\r\n" + 
            "  `pelanggan`.`idPelanggan`   AS `idPelanggan`,\r\n" + 
            "  `pelanggan`.`NamaPelanggan` AS `NamaPelanggan`,\r\n" + 
            "  CONCAT('Rp. ',(SELECT SUM(`menu`.`Harga` * `detailpemesanan`.`jumlah`) FROM (`detailpemesanan` JOIN `menu` ON(`detailpemesanan`.`idMenu` = `menu`.`idMenu`)) WHERE `detailpemesanan`.`idPemesanan` = `pemesanan`.`idPemesanan`)) AS `TotalBelanja`\r\n" + 
            "FROM (`pemesanan`\r\n" + 
            "   JOIN `pelanggan`\r\n" + 
            "     ON (`pemesanan`.`idPelanggan` = `pelanggan`.`idPelanggan`))";
                    
            resultData = statement.executeQuery(query);
                    
            data = "";
                    
            while (resultData.next()) {
                data += "|idPemesanan: " + resultData.getInt("idPemesanan") +
                "| idPelanggan: " + resultData.getInt("idPelanggan") +
                "| NamaPelanggan: " + resultData.getString("NamaPelanggan") +
                "| TotalBelanja: " + resultData.getString("TotalBelanja") + " |\n";
            }
                    
            statement.close();
            connect.close();
                    
            } catch (Exception e) {
                e.printStackTrace();
            }
                
            return data;
    }

    public static String getCetakStruk(String nama) {
        cConfig.Connection();
        String data = "Data Tidak Ditemukan";
    
        try {
            statement = connect.createStatement();
            double Total_Belanja = 0.0;
    
            String query = "SELECT pemesanan.idPemesanan, detailpemesanan.idDetailPemesanan, pelanggan.idPelanggan, pelanggan.NamaPelanggan, menu.NamaMenu, menu.Harga, detailpemesanan.jumlah "
                    + "FROM pemesanan "
                    + "JOIN pelanggan ON pemesanan.idPelanggan = pelanggan.idPelanggan "
                    + "JOIN detailpemesanan ON pemesanan.idPemesanan = detailpemesanan.idPemesanan "
                    + "JOIN menu ON detailpemesanan.idMenu = menu.idMenu "
                    + "WHERE pelanggan.NamaPelanggan = ?";
    
    
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, nama);
    
            resultData = preparedStatement.executeQuery();
            data = "";
            
            if (resultData.next()) {
                data += "=== Struk Belanja ===\n";
                data += "ID Pesanan: " + resultData.getInt("idPemesanan");
                data += "Nama Pelanggan: " + resultData.getString("NamaPelanggan") + "\n";

                do {
                    int idDetailPemesanan = resultData.getInt("idDetailPemesanan");
                        String NamaMenu = resultData.getString("NamaMenu");
                        int jumlah = resultData.getInt("jumlah");
                        double Harga = resultData.getDouble("Harga");

                        
                        double totalHarga = jumlah * Harga;
                        Total_Belanja += totalHarga;

                        data += "| Id Detail Pemesanan: " + idDetailPemesanan;
                        data += "| Menu: " + NamaMenu;
                        data += "| Jumlah: " + jumlah;
                        data += "| Harga: " + Harga;
                        data += "| Total Harga: " + totalHarga + " |\n";
                } while (resultData.next());

                data += "=== Total Belanja: " + Total_Belanja + " ===";
            } else {
                data = "Data not found for the given name: " + nama;
            }
    
            statement.close();
            connect.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return data;
    }
    
    
    public static String getnewcust(String nama, int nohp) {
        cConfig.Connection();
        String data = "Error dalam Pengambilan data";
    
        try {
            statement = connect.createStatement();
    
            String query = "INSERT INTO restoran.`pelanggan`(NamaPelanggan, noTelepon)" +
                           "VALUES ('" + nama + "', '" + nohp + "')";
            int rowsAffected = statement.executeUpdate(query);
    
            if (rowsAffected > 0) {
                data = "==== Data berhasil masuk! ====";
            } else {
                data = "Gagal menambahkan data ke database.";
            }
    
            statement.close();
            connect.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return data;
    }
    
    public static String getnewOrder(String nama, int nohp) {
        cConfig.Connection();
        String data = "Error dalam Pengambilan data";
    
        try {
            
            String query = "INSERT INTO restoran.pemesanan (idPelanggan) SELECT idPelanggan FROM pelanggan WHERE namaPelanggan = ? AND noTelepon = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, nama);
            preparedStatement.setInt(2, nohp);
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                data = "==== Pesanan Siap Dimasukan! ====";
            } else {
                data = "Gagal menambahkan data ke database.";
            }
    
            preparedStatement.close();
            connect.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return data;
    }
    
    public static String getcustmenu(String namaPelanggan, int idMenu, int jumlah) {
        cConfig.Connection();
        String data = "Error dalam Pengambilan data";
    
        try {
            
            String query = "INSERT INTO restoran.detailpemesanan (idPemesanan, idMenu, jumlah) " +
                           "SELECT p.idPemesanan, ?, ? " +
                           "FROM pemesanan p " +
                           "JOIN pelanggan pl ON p.idPelanggan = pl.idPelanggan " +
                           "WHERE pl.NamaPelanggan = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, idMenu); 
            preparedStatement.setInt(2, jumlah);
            preparedStatement.setString(3, namaPelanggan);
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                data = "==== Pesanan Berhasil Ditambahkan! ====";
            } else {
                data = "Gagal menambahkan data ke database.";
            }
    
            preparedStatement.close();
            connect.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return data;
    }
    
            
}
        