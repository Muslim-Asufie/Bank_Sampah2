import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampahTest {

    @Test
    public void testOrganik() {
        Organik organik = new Organik("Sisa Makanan", 1000, 5);
        
        assertEquals("Sisa Makanan", organik.getNama(), "Nama organik harus sesuai");
        assertEquals(1000.0, organik.getHargaDasar(), 0.001, "Harga dasar organik harus sesuai");
        assertEquals(5, organik.getFluktuasi(), "Fluktuasi organik harus sesuai");
        assertEquals("Organik", organik.getKategori(), "Kategori organik harus 'Organik'");
        
        // Rumus Organik: getHargaDasar() * (1 + (fluktuasiPersen / 100)) * 0.95
        // Jika fluktuasi = 10, maka: 1000 * (1 + 10/100) * 0.95 = 1000 * 1.1 * 0.95 = 1045
        double expectedHarga = 1000 * (1 + (10.0 / 100.0)) * 0.95;
        assertEquals(expectedHarga, organik.hitungHarga(10.0), 0.001, "Perhitungan harga Organik tidak sesuai");
    }

    @Test
    public void testAnorganik() {
        Anorganik anorganik = new Anorganik("Botol Plastik", 2000, 2);
        
        assertEquals("Botol Plastik", anorganik.getNama(), "Nama anorganik harus sesuai");
        assertEquals(2000.0, anorganik.getHargaDasar(), 0.001, "Harga dasar anorganik harus sesuai");
        assertEquals(2, anorganik.getFluktuasi(), "Fluktuasi anorganik harus sesuai");
        assertEquals("Anorganik", anorganik.getKategori(), "Kategori anorganik harus 'Anorganik'");
        
        // Rumus Anorganik: getHargaDasar() * (1 + ((fluktuasiPersen + 10) / 100))
        // Jika fluktuasi = 5, maka: 2000 * (1 + (15 / 100)) = 2000 * 1.15 = 2300
        double expectedHarga = 2000 * (1 + ((5.0 + 10.0) / 100.0));
        assertEquals(expectedHarga, anorganik.hitungHarga(5.0), 0.001, "Perhitungan harga Anorganik tidak sesuai");
    }

    @Test
    public void testB3() {
        B3 b3 = new B3("Baterai Bekas", 5000, 0);
        
        assertEquals("Baterai Bekas", b3.getNama(), "Nama B3 harus sesuai");
        assertEquals(5000.0, b3.getHargaDasar(), 0.001, "Harga dasar B3 harus sesuai");
        assertEquals(0, b3.getFluktuasi(), "Fluktuasi B3 harus sesuai");
        assertEquals("B3", b3.getKategori(), "Kategori B3 harus 'B3'");
        
        // Rumus B3: getHargaDasar() * (1 + (fluktuasiPersen / 100)) - 500
        // Jika fluktuasi = 5, maka: 5000 * (1 + 5/100) - 500 = 5000 * 1.05 - 500 = 5250 - 500 = 4750
        double expectedHarga = 5000 * (1 + (5.0 / 100.0)) - 500;
        assertEquals(expectedHarga, b3.hitungHarga(5.0), 0.001, "Perhitungan harga B3 tidak sesuai");
    }
}
