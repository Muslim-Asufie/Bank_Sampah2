public abstract class Sampah {
    private String nama;
    private double hargaDasar;
    private int fluktuasi;

    public Sampah(String nama, double hargaDasar, int fluktuasi) {
        this.nama = nama;
        this.hargaDasar = hargaDasar;
        this.fluktuasi = fluktuasi;
    }

    public String getNama() { 
        return nama; 
    }

    public double getHargaDasar() { 
        return hargaDasar; 
    }

    public int getFluktuasi() { 
        return fluktuasi; 
    }
    
    public abstract double hitungHarga(double fluktuasiPersen);
    public abstract String getKategori();
}