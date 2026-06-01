import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection koneksi;

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_bank_sampah";
                String user = "root";
                String password = ""; 
                
                // Meregistrasikan driver MySQL
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                
                System.out.println("Koneksi Berhasil ke database: db_bank_sampah");
            } catch (SQLException e) {
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }

    // Method main ditaruh di sini untuk melakukan pengecekan
    public static void main(String[] args) {
        // Memanggil method getKoneksi untuk tes
        getKoneksi();
    }
}