public class Organik extends Sampah {
    public Organik(String nama, double hargaDasar, int fluktuasi) 
    { super(nama, hargaDasar, fluktuasi); }
    
    @Override
    public double hitungHarga(double fluktuasiPersen) { 
        return getHargaDasar() * (1 + (fluktuasiPersen / 100)) * 0.95;
    }

    @Override
    public String getKategori() { 
        return "Organik"; 
    }
}