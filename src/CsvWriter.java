import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CsvWriter {
    private static final String FILE_NAME = "history_transaksi_user.csv";

    public static void simpanKeCsv(String nama, String kategori, double berat, double totalHarga) {
        File file = new File(FILE_NAME);
        boolean isBaru = !file.exists();

        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            if (isBaru) {
                pw.println("Nama Sampah,Kategori,Berat (Kg),Total Harga (Rp),Waktu Input");
            }
            
            String waktuInput = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pw.println(nama + "," + kategori + "," + berat + "," + totalHarga + "," + waktuInput);
        } catch (IOException e) {
            System.out.println("Gagal menulis ke CSV: " + e.getMessage());
        }
    }
}