class Anorganik extends Sampah {
    public Anorganik(String nama, double hargaDasar, int fluktuasi)
    { super(nama, hargaDasar, fluktuasi); }
    
    @Override
    public double hitungHarga(double fluktuasiPersen) { 
    return getHargaDasar() * (1 + ((fluktuasiPersen + 10) / 100)); 
    }
 
    @Override
    public String getKategori() {
    return "Anorganik"; 
    }
}