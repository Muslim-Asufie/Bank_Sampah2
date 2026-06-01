class B3 extends Sampah {
    public B3(String nama, double hargaDasar, int fluktuasi) 
    { super(nama, hargaDasar, fluktuasi); }

    @Override
    public double hitungHarga(double fluktuasiPersen) { 
    return getHargaDasar() * (1 + (fluktuasiPersen / 100)) - 500; 
    }

    @Override
    public String getKategori() {
    return "B3"; 
    }
}