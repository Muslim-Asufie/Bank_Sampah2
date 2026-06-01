import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class MainAppFrame extends JFrame {
    private DefaultTableModel modelHarga;
    private JTable tabelHarga;
    private ArrayList<Sampah> listMasterSampah = new ArrayList<>();

    private DefaultTableModel modelHistory;
    private JTable tabelHistory;

    private JComboBox<String> cbPilihSampah;
    private JTextField txtBerat;
    private JLabel lblHasilKalkulasi;

    public MainAppFrame() {
        setTitle("Sistem Manajemen Sampah");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // 1. Panel Atas: Tabel Harga Acuan (Read-Only)
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setBorder(BorderFactory.createTitledBorder("Daftar Harga Sampah Saat Ini"));
        panelAtas.setPreferredSize(new Dimension(880, 180));

        String[] colHarga = {"Jenis Sampah", "Kategori", "Harga Dasar", "Tren Pasar", "Harga Hari Ini"};
        modelHarga = new DefaultTableModel(colHarga, 0);
        tabelHarga = new JTable(modelHarga);
        tabelHarga.setDefaultRenderer(Object.class, new KategoriRowRenderer());
        tabelHarga.setEnabled(false); 
        panelAtas.add(new JScrollPane(tabelHarga), BorderLayout.CENTER);

        // 2. Panel Tengah: Form Input Penimbangan User
        JPanel panelTengah = new JPanel(new GridBagLayout());
        panelTengah.setBorder(BorderFactory.createTitledBorder("Form Input Penimbangan Sampah"));
        panelTengah.setPreferredSize(new Dimension(880, 150));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelTengah.add(new JLabel("Pilih Jenis Sampah:"), gbc);
        gbc.gridx = 1;
        cbPilihSampah = new JComboBox<>();
        panelTengah.add(cbPilihSampah, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelTengah.add(new JLabel("Berat Sampah (Kg):"), gbc);
        gbc.gridx = 1;
        txtBerat = new JTextField("1.0");
        panelTengah.add(txtBerat, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JButton btnHitungSimpan = new JButton("Simpan Transaksi");
        panelTengah.add(btnHitungSimpan, gbc);
        gbc.gridx = 1;
        lblHasilKalkulasi = new JLabel("Total Konversi Nilai: Rp 0.00");
        lblHasilKalkulasi.setFont(new Font("Arial", Font.BOLD, 13));
        panelTengah.add(lblHasilKalkulasi, gbc);

        // 3. Panel Bawah: Tabel Riwayat History User
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBorder(BorderFactory.createTitledBorder("History Catatan Setoran Sampah"));
        panelBawah.setPreferredSize(new Dimension(880, 250));

        String[] colHistory = {"Jenis Sampah", "Kategori", "Berat (Kg)", "Total Nilai Diterima (Rp)", "Waktu Input"};
        modelHistory = new DefaultTableModel(colHistory, 0);
        tabelHistory = new JTable(modelHistory);
        tabelHistory.setDefaultRenderer(Object.class, new KategoriRowRenderer());
        tabelHistory.setEnabled(false); 
        panelBawah.add(new JScrollPane(tabelHistory), BorderLayout.CENTER);

        add(panelAtas);
        add(panelTengah);
        add(panelBawah);

        muatTabelHargaMaster();
        muatTabelHistoryTransaksi();

        // Listener untuk update kalkulasi harga otomatis
        Runnable updateKalkulasi = () -> {
            try {
                int selectedIndex = cbPilihSampah.getSelectedIndex();
                if (selectedIndex == -1 || listMasterSampah.isEmpty()) {
                    lblHasilKalkulasi.setText("Total Konversi Nilai: Rp 0.00");
                    return;
                }
                String text = txtBerat.getText().trim();
                if (text.isEmpty()) {
                    lblHasilKalkulasi.setText("Total Konversi Nilai: Rp 0.00");
                    return;
                }
                double berat = Double.parseDouble(text);
                if (berat <= 0) {
                    lblHasilKalkulasi.setText("Total Konversi Nilai: Rp 0.00");
                    return;
                }
                Sampah sampahTerpilih = listMasterSampah.get(selectedIndex);
                double hargaFinalPerKg = sampahTerpilih.hitungHarga(sampahTerpilih.getFluktuasi()); 
                double totalHarga = hargaFinalPerKg * berat;
                lblHasilKalkulasi.setText(String.format("Total Konversi Nilai: Rp %.2f", totalHarga));
            } catch (NumberFormatException ex) {
                lblHasilKalkulasi.setText("Total Konversi Nilai: Rp 0.00");
            }
        };

        txtBerat.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateKalkulasi.run(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateKalkulasi.run(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateKalkulasi.run(); }
        });

        cbPilihSampah.addActionListener(e -> updateKalkulasi.run());

        // Menjalankan kalkulasi awal saat pertama kali buka
        updateKalkulasi.run();

        // Event Tombol Simpan
        btnHitungSimpan.addActionListener(e -> {
            try {
                int selectedIndex = cbPilihSampah.getSelectedIndex();
                if (selectedIndex == -1) return;

                double berat = Double.parseDouble(txtBerat.getText().trim());
                if (berat <= 0) throw new Exception("Berat harus lebih besar dari 0!");

                Sampah sampahTerpilih = listMasterSampah.get(selectedIndex);
                
                // Mengambil nilai fluktuasi dinamis dari properti database objeknya sendiri
                double hargaFinalPerKg = sampahTerpilih.hitungHarga(sampahTerpilih.getFluktuasi()); 
                double totalHarga = hargaFinalPerKg * berat;

                lblHasilKalkulasi.setText(String.format("Total Konversi Nilai: Rp %.2f", totalHarga));

                // Simpan ke MySQL
                Connection conn = Koneksi.getKoneksi();
                String sqlInsert = "INSERT INTO t_history_transaksi (nama_sampah, kategori, berat, total_harga) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sqlInsert);
                pst.setString(1, sampahTerpilih.getNama());
                pst.setString(2, sampahTerpilih.getKategori());
                pst.setDouble(3, berat);
                pst.setDouble(4, totalHarga);
                pst.executeUpdate();

                // Simpan ke File CSV
                CsvWriter.simpanKeCsv(sampahTerpilih.getNama(), sampahTerpilih.getKategori(), berat, totalHarga);

                JOptionPane.showMessageDialog(this, "Transaksi Berhasil Dicatat!");
                
                muatTabelHistoryTransaksi();
                txtBerat.setText("1.0");

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Input berat harus berupa angka desimal!", "Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void muatTabelHargaMaster() {
        modelHarga.setRowCount(0);
        cbPilihSampah.removeAllItems();
        listMasterSampah.clear();

        try {
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM m_sampah");

            while (rs.next()) {
                String nama = rs.getString("nama");
                String kat = rs.getString("kategori");
                double harga = rs.getDouble("harga_dasar");
                int fluktuasi = rs.getInt("fluktuasi");

                Sampah s;
                if (kat.equalsIgnoreCase("Organik")) s = new Organik(nama, harga, fluktuasi);
                else if (kat.equalsIgnoreCase("Anorganik")) s = new Anorganik(nama, harga, fluktuasi);
                else s = new B3(nama, harga, fluktuasi);

                listMasterSampah.add(s);
                
                double hargaHariIni = s.hitungHarga(fluktuasi);
                String teksTren = (fluktuasi > 0 ? "+" : "") + fluktuasi + "%";

                modelHarga.addRow(new Object[]{nama, kat, "Rp " + harga, teksTren, String.format("Rp %.2f", hargaHariIni)});
                cbPilihSampah.addItem(nama + " (" + kat + ")");
            }
        } catch (SQLException ex) {
            System.out.println("Gagal Load Master Data: " + ex.getMessage());
        }
    }

    private void muatTabelHistoryTransaksi() {
        modelHistory.setRowCount(0);
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM t_history_transaksi ORDER BY waktu_input DESC");

            while (rs.next()) {
                modelHistory.addRow(new Object[]{
                    rs.getString("nama_sampah"),
                    rs.getString("kategori"),
                    rs.getDouble("berat") + " Kg",
                    "Rp " + rs.getDouble("total_harga"),
                    rs.getTimestamp("waktu_input")
                });
            }
        } catch (SQLException ex) {
            System.out.println("Gagal Load History: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainAppFrame().setVisible(true));
    }
}