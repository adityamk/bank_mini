/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LaporanPDF;
import Model.Nasabah;
import static Model.Nasabah.getNasabahList;
import Model.Transaksi;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

/**
 *
 * @author Aditya Maulana
 */
public class LaporanController implements Initializable {
    @FXML
    private JFXDatePicker DariTanggalDateKeuangan;
    @FXML
    private JFXDatePicker SampaiTanggalDateKeuangan;
    @FXML
    private JFXDatePicker DariTanggalDatePembayaran;
    @FXML
    private JFXDatePicker SampaiTanggalDatePembayaran;  
    @FXML
    private JFXTreeTableView<Transaksi> Tablekeuangan;
    @FXML
    private JFXTreeTableView<Transaksi> Tablepembayaran;    
    @FXML
    private JFXDatePicker sampaitanggalDateBank;
    @FXML
    private JFXTreeTableView<Transaksi> TableLaporanBank;
    @FXML
    private JFXDatePicker daritanggalDateBank;
    @FXML
    private JFXComboBox<String> jenisComboBoxBank;
    @FXML
    private JFXTreeTableView<Nasabah> TableDaftarSiswa;   
    @FXML
    private JFXTreeTableView<Transaksi> TableLaporan;    
    @FXML
    private JFXTextField norekTextField;       
    @FXML
    private JFXDatePicker sampaitanggalDate;
    @FXML
    private JFXDatePicker daritanggalDate;
    @FXML
    private JFXComboBox<String> jenisComboBox;
    
    private final ObservableList<Transaksi> laporanList = FXCollections.observableArrayList();
    private final ObservableList<Transaksi> laporanListBank = FXCollections.observableArrayList();
    
    private boolean validasi_laporan_siswa() {
    return !norekTextField.getText().equals("") &&
            jenisComboBox.getValue() != null &&
            daritanggalDate.getValue() != null && 
            sampaitanggalDate.getValue() != null;
    }
    
    private boolean validasi_laporan_trx_bank() {
    return  jenisComboBoxBank.getValue() != null &&
            daritanggalDateBank.getValue() != null && 
            sampaitanggalDateBank.getValue() != null;
    }
    
    private boolean validasi_laporan_keuangan() {
    return  DariTanggalDateKeuangan.getValue() != null && 
            SampaiTanggalDateKeuangan.getValue() != null;
    }
    
    private boolean validasi_laporan_pembayaran() {
    return  DariTanggalDatePembayaran.getValue() != null && 
            SampaiTanggalDatePembayaran.getValue() != null;
    }
    
    @FXML
    void prosesAction(ActionEvent event) {
        if (validasi_laporan_siswa()){ 
            Nasabah nasabah = Nasabah.nasabah(Integer.parseInt(norekTextField.getText()));
            if (nasabah != null){
                List<Transaksi> transaksi = Transaksi.transaksi(
                        nasabah, 
                        jenisComboBox.getSelectionModel().getSelectedItem(), 
                        daritanggalDate.getValue(), sampaitanggalDate.getValue()
                );
                if (transaksi.size() > 0) {
                    laporanList.setAll(transaksi);
                } else {
                    laporanList.clear();
                }
            }
        } else {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Laporan");
             alert.setContentText("Masukkan Data Laporan");
             alert.show();
        }
    }
    
    @FXML
    void prosesActionBank(ActionEvent event) {
        if (validasi_laporan_trx_bank()) {
            List<Transaksi> transaksi;
            if (jenisComboBoxBank.getSelectionModel().getSelectedItem().equals("Semua Transaksi")) {
                transaksi = Transaksi.transaksiBank(daritanggalDateBank.getValue(), sampaitanggalDateBank.getValue());
            } else {
                transaksi = Transaksi.transaksiBank(
                        daritanggalDateBank.getValue(), 
                        sampaitanggalDateBank.getValue(), 
                        jenisComboBoxBank.getSelectionModel().getSelectedItem());
            }

            if (transaksi.size() > 0) {
                laporanListBank.setAll(transaksi);
            } else {
                laporanListBank.clear();
            }
        } else {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Laporan");
             alert.setContentText("Masukkan Data Laporan");
             alert.show();
        }
    }
    
    @FXML
    void prosesActionKeuangan(ActionEvent event) {
        if (validasi_laporan_keuangan()) {
            List<Transaksi> transaksi = Transaksi.transaksi_keuangan(DariTanggalDateKeuangan.getValue() , SampaiTanggalDateKeuangan.getValue()); 
            if (transaksi.size() > 0) {
              Transaksi.getTransaksiListbyket().setAll(transaksi);
            } else {
              Transaksi.getTransaksiListbyket().clear();
            }
        } else {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Laporan");
             alert.setContentText("Masukkan Data Laporan");
             alert.show();
        }
    }

    @FXML
    void prosesActionPembayaran(ActionEvent event) {
        if (validasi_laporan_pembayaran()) {
            List<Transaksi> transaksi = Transaksi.transaksi_pembayaran(DariTanggalDatePembayaran.getValue() , SampaiTanggalDatePembayaran.getValue()); 
            if (transaksi.size() > 0) {
              Transaksi.getTransaksiListbyket_pem().setAll(transaksi);
            } else {
              Transaksi.getTransaksiListbyket_pem().clear();
            }
        } else {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Laporan");
             alert.setContentText("Masukkan Data Laporan");
             alert.show();  
        }
    }
    
    @FXML
    void cetakActionKeuangan(ActionEvent event) throws IOException {
        LaporanPDF.bank_semuatrx(Transaksi.getTransaksiListbyket());
    }

    @FXML
    void cetakActionPembayaran(ActionEvent event) throws IOException {
        LaporanPDF.bank_semuatrx(Transaksi.getTransaksiListbyket_pem());
    }

    @FXML
    void cetakActionSiswa(ActionEvent event) throws IOException {
       LaporanPDF.daftar_siswa();
    }
    
    
    @FXML
    void cetakActionBank(ActionEvent event) throws IOException {
        LaporanPDF.bank_semuatrx(laporanListBank);
    }
    
     @FXML
    void cetakAction(ActionEvent event) throws IOException {
        Nasabah nasabah = Nasabah.nasabah(Integer.parseInt(norekTextField.getText()));
        if (jenisComboBox.getSelectionModel().getSelectedItem().equals("Semua Transaksi")) {
            LaporanPDF.nasabah_semuatrx(nasabah, laporanList);
        } else {
            LaporanPDF.nasabah(nasabah, laporanList);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList <String> jenis_trx = FXCollections.observableArrayList("Semua Transaksi","Setoran","Penarikan","Pemotongan");
        jenisComboBox.setItems(jenis_trx);
        
        TreeTableColumn<Transaksi, Integer> notrx = new TreeTableColumn<>("No. Transaksi");
        TreeTableColumn<Transaksi, Integer> norek = new TreeTableColumn<>("No Rek");
        TreeTableColumn<Transaksi, String> nama = new TreeTableColumn<>("Nama");
        TreeTableColumn<Transaksi, String> jenis = new TreeTableColumn<>("Jenis Transaksi");
        TreeTableColumn<Transaksi, String> tgl = new TreeTableColumn<>("Tanggal");
        TreeTableColumn<Transaksi, String> jumlah = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<Transaksi, String> ket = new TreeTableColumn<>("Keterangan");
        //Setting data column
        notrx.setCellValueFactory(param -> param.getValue().getValue().notrxProperty());
        norek.setCellValueFactory(param -> param.getValue().getValue().norekProperty());
        nama.setCellValueFactory(param -> {
            Nasabah nasabah = Nasabah.nasabah(param.getValue().getValue());
            return nasabah.namaProperty();
        });
        jenis.setCellValueFactory(param -> param.getValue().getValue().jenisProperty());
        tgl.setCellValueFactory(param -> param.getValue().getValue().tanggalProperty());
        jumlah.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        ket.setCellValueFactory(param -> param.getValue().getValue().ketProperty());
        
        TableLaporan.getColumns().add(notrx);
        TableLaporan.getColumns().add(norek);
        TableLaporan.getColumns().add(nama);
        TableLaporan.getColumns().add(jenis);
        TableLaporan.getColumns().add(tgl);
        TableLaporan.getColumns().add(jumlah);
        TableLaporan.getColumns().add(ket);
        TableLaporan.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
//        norek.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nis.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nama.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        kelas.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        no_telp.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        alamat.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.3));
        
        //buat root table
 
        TreeItem<Transaksi> trxRoot = new RecursiveTreeItem<>(laporanList, RecursiveTreeObject::getChildren);
        TableLaporan.setRoot(trxRoot);
       // set root ke table
        TableLaporan.setShowRoot(false);  
        
        //laporanBANK
        jenisComboBoxBank.setItems(jenis_trx);
        
        TreeTableColumn<Transaksi, Integer> notrxbank = new TreeTableColumn<>("No. Transaksi");
        TreeTableColumn<Transaksi, Integer> norekbank = new TreeTableColumn<>("No Rek");
        TreeTableColumn<Transaksi, String> namabank = new TreeTableColumn<>("Nama");
        TreeTableColumn<Transaksi, String> jenisbank = new TreeTableColumn<>("Jenis Transaksi");
        TreeTableColumn<Transaksi, String> tglbank = new TreeTableColumn<>("Tanggal");
        TreeTableColumn<Transaksi, String> jumlahbank = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<Transaksi, String> ketbank = new TreeTableColumn<>("Keterangan");
        //Setting data column
        notrxbank.setCellValueFactory(param -> param.getValue().getValue().notrxProperty());
        norekbank.setCellValueFactory(param -> param.getValue().getValue().norekProperty());
        namabank.setCellValueFactory(param -> {
            Nasabah nasabah = Nasabah.nasabah(param.getValue().getValue());
            return nasabah.namaProperty();
        });
        jenisbank.setCellValueFactory(param -> param.getValue().getValue().jenisProperty());
        tglbank.setCellValueFactory(param -> param.getValue().getValue().tanggalProperty());
        jumlahbank.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        ket.setCellValueFactory(param -> param.getValue().getValue().ketProperty());
        TableLaporanBank.getColumns().add(notrxbank);
        TableLaporanBank.getColumns().add(norekbank);
        TableLaporanBank.getColumns().add(namabank);
        TableLaporanBank.getColumns().add(jenisbank);
        TableLaporanBank.getColumns().add(tglbank);
        TableLaporanBank.getColumns().add(jumlahbank);
        TableLaporanBank.getColumns().add(ketbank);
        TableLaporanBank.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
        
        TreeItem<Transaksi> trxRoot2 = new RecursiveTreeItem<>(laporanListBank, RecursiveTreeObject::getChildren);
        TableLaporanBank.setRoot(trxRoot2);
//       // set root ke table
        TableLaporanBank.setShowRoot(false);  
        
        
        
        //daftar siswa
        TreeTableColumn<Nasabah, Integer> noreksiswa = new TreeTableColumn<>("No. Rek");
        TreeTableColumn<Nasabah, String> nissiswa = new TreeTableColumn<>("NIS");
        TreeTableColumn<Nasabah, String> namasiswa = new TreeTableColumn<>("Nama");
        TreeTableColumn<Nasabah, String> kelas = new TreeTableColumn<>("Kelas");
        TreeTableColumn<Nasabah, String> no_telp = new TreeTableColumn<>("No. Telp");
        TreeTableColumn<Nasabah, String> alamat = new TreeTableColumn<>("Alamat");
        //Setting data column
        noreksiswa.setCellValueFactory(param -> param.getValue().getValue().norekProperty());
        nissiswa.setCellValueFactory(param -> param.getValue().getValue().nisProperty());
        namasiswa.setCellValueFactory(param -> param.getValue().getValue().namaProperty());
        kelas.setCellValueFactory(param -> param.getValue().getValue().kelasProperty());
        no_telp.setCellValueFactory(param -> param.getValue().getValue().no_telpProperty());
        alamat.setCellValueFactory(param -> param.getValue().getValue().alamatProperty());
        
        TableDaftarSiswa.getColumns().add(noreksiswa);
        TableDaftarSiswa.getColumns().add(nissiswa);
        TableDaftarSiswa.getColumns().add(namasiswa);
        TableDaftarSiswa.getColumns().add(kelas);
        TableDaftarSiswa.getColumns().add(no_telp);
        TableDaftarSiswa.getColumns().add(alamat);
        //TableNasabah.setColumnResizePolicy(TableNasabah.CONSTRAINED_RESIZE_POLICY);
        noreksiswa.prefWidthProperty().bind(TableDaftarSiswa.prefWidthProperty().multiply(0.1));
        nissiswa.prefWidthProperty().bind(TableDaftarSiswa.prefWidthProperty().multiply(0.1));
        namasiswa.prefWidthProperty().bind(TableDaftarSiswa.prefWidthProperty().multiply(0.2));
        kelas.prefWidthProperty().bind(TableDaftarSiswa.prefWidthProperty().multiply(0.1));
        no_telp.prefWidthProperty().bind(TableDaftarSiswa.prefWidthProperty().multiply(0.2));
        alamat.prefWidthProperty().bind(TableDaftarSiswa.prefWidthProperty().multiply(0.3));
        
        //buat root table
 
        TreeItem<Nasabah> nasabahRoot = new RecursiveTreeItem<>(getNasabahList(), RecursiveTreeObject::getChildren);
        TableDaftarSiswa.setRoot(nasabahRoot);
       // set root ke table
        TableDaftarSiswa.setShowRoot(false);     
        
        
        
        //table Transaksi keuangan
        TreeTableColumn<Transaksi, Integer> notrx2 = new TreeTableColumn<>("No. Transaksi");
        TreeTableColumn<Transaksi, Integer> norek2 = new TreeTableColumn<>("No Rek");
        TreeTableColumn<Transaksi, String> nama2 = new TreeTableColumn<>("Nama");
        TreeTableColumn<Transaksi, String> jenis2 = new TreeTableColumn<>("Jenis Transaksi");
        TreeTableColumn<Transaksi, String> tgl2 = new TreeTableColumn<>("Tanggal");
        TreeTableColumn<Transaksi, String> jumlah2 = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<Transaksi, String> ket2 = new TreeTableColumn<>("Keterangan");
        //Setting data column
        notrx2.setCellValueFactory(param -> param.getValue().getValue().notrxProperty());
        norek2.setCellValueFactory(param -> param.getValue().getValue().norekProperty());
        nama2.setCellValueFactory(param -> {
            Nasabah nasabah = Nasabah.nasabah(param.getValue().getValue());
            return nasabah.namaProperty();
        });
        jenis2.setCellValueFactory(param -> param.getValue().getValue().jenisProperty());
        tgl2.setCellValueFactory(param -> param.getValue().getValue().tanggalProperty());
        jumlah2.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        ket2.setCellValueFactory(param -> param.getValue().getValue().ketProperty());
        
        Tablekeuangan.getColumns().add(notrx2);
        Tablekeuangan.getColumns().add(norek2);
        Tablekeuangan.getColumns().add(nama2);
        Tablekeuangan.getColumns().add(jenis2);
        Tablekeuangan.getColumns().add(tgl2);
        Tablekeuangan.getColumns().add(jumlah2);
        Tablekeuangan.getColumns().add(ket2);
        Tablekeuangan.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
//        norek.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nis.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nama.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        kelas.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        no_telp.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        alamat.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.3));
        
        //buat root table
 
        TreeItem<Transaksi> trxRoot1 = new RecursiveTreeItem<>(Transaksi.getTransaksiListbyket(), RecursiveTreeObject::getChildren);
        Tablekeuangan.setRoot(trxRoot1);
       // set root ke table
        Tablekeuangan.setShowRoot(false);  
       
       //table Transaksi pembayaran
        TreeTableColumn<Transaksi, Integer> notrx3 = new TreeTableColumn<>("No. Transaksi");
        TreeTableColumn<Transaksi, Integer> norek3 = new TreeTableColumn<>("No Rek");
        TreeTableColumn<Transaksi, String> nama3 = new TreeTableColumn<>("Nama");
        TreeTableColumn<Transaksi, String> jenis3 = new TreeTableColumn<>("Jenis Transaksi");
        TreeTableColumn<Transaksi, String> tgl3 = new TreeTableColumn<>("Tanggal");
        TreeTableColumn<Transaksi, String> jumlah3 = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<Transaksi, String> ket3 = new TreeTableColumn<>("Keterangan");
        //Setting data column
        notrx3.setCellValueFactory(param -> param.getValue().getValue().notrxProperty());
        norek3.setCellValueFactory(param -> param.getValue().getValue().norekProperty());
        nama3.setCellValueFactory(param -> {
            Nasabah nasabah = Nasabah.nasabah(param.getValue().getValue());
            return nasabah.namaProperty();
        });
        jenis3.setCellValueFactory(param -> param.getValue().getValue().jenisProperty());
        tgl3.setCellValueFactory(param -> param.getValue().getValue().tanggalProperty());
        jumlah3.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        ket3.setCellValueFactory(param -> param.getValue().getValue().ketProperty());
        
        Tablepembayaran.getColumns().add(notrx3);
        Tablepembayaran.getColumns().add(norek3);
        Tablepembayaran.getColumns().add(nama3);
        Tablepembayaran.getColumns().add(jenis3);
        Tablepembayaran.getColumns().add(tgl3);
        Tablepembayaran.getColumns().add(jumlah3);
        Tablepembayaran.getColumns().add(ket3);
        Tablepembayaran.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);
//        norek.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nis.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nama.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        kelas.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        no_telp.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        alamat.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.3));
        
        //buat root table
 
        TreeItem<Transaksi> trxRoot3 = new RecursiveTreeItem<>(Transaksi.getTransaksiListbyket_pem(), RecursiveTreeObject::getChildren);
        Tablepembayaran.setRoot(trxRoot3);
       // set root ke table
        Tablepembayaran.setShowRoot(false); 
    }
}