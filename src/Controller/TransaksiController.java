/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.LaporanPDF;
import Model.Nasabah;
import static Model.Nasabah.getNasabahList;
import Model.Rupiah;
import Model.Transaksi;
import static Model.Transaksi.getTransaksiList;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

/**
 *
 * @author Aditya Maulana
 */
public class TransaksiController implements Initializable {
    private ObservableList<String> jenis_trx;
      @FXML
    private JFXButton cariButton;

    @FXML
    private JFXTreeTableView<Transaksi> TableTransaksi;
    
    @FXML
    private JFXTextField norekTextField;

    @FXML
    private JFXComboBox<String> jenisComboBox;

    @FXML
    private JFXTextField jumlahTextField;

    @FXML
    private JFXTextField ketTextField;

    @FXML
    private Label namaLabel;

    @FXML
    private Label nisLabel;

    @FXML
    private Label saldoLabel;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jenis_trx = FXCollections.observableArrayList("Setoran","Penarikan","Pemotongan");
        jenisComboBox.setItems(jenis_trx);
        Nasabah.updateNasabah();
        
        TreeTableColumn<Transaksi, Integer> notrx = new TreeTableColumn<>("No. Transaksi");
        TreeTableColumn<Transaksi, Integer> norek = new TreeTableColumn<>("No Rek");
        TreeTableColumn<Transaksi, String> jenis = new TreeTableColumn<>("Jenis Transaksi");
        TreeTableColumn<Transaksi, String> tgl = new TreeTableColumn<>("Tanggal");
        TreeTableColumn<Transaksi, String> jumlah = new TreeTableColumn<>("Jumlah");
        TreeTableColumn<Transaksi, String> ket = new TreeTableColumn<>("Keterangan");
        //Setting data column
        notrx.setCellValueFactory(param -> param.getValue().getValue().notrxProperty());
        norek.setCellValueFactory(param -> param.getValue().getValue().norekProperty());
        jenis.setCellValueFactory(param -> param.getValue().getValue().jenisProperty());
        tgl.setCellValueFactory(param -> param.getValue().getValue().tanggalProperty());
        jumlah.setCellValueFactory(param -> param.getValue().getValue().jumlahProperty());
        ket.setCellValueFactory(param -> param.getValue().getValue().ketProperty());
        
        TableTransaksi.getColumns().add(notrx);
        TableTransaksi.getColumns().add(norek);
        TableTransaksi.getColumns().add(jenis);
        TableTransaksi.getColumns().add(tgl);
        TableTransaksi.getColumns().add(jumlah);
        TableTransaksi.getColumns().add(ket);
        TableTransaksi.setColumnResizePolicy(TableTransaksi.CONSTRAINED_RESIZE_POLICY);
//        norek.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nis.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        nama.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        kelas.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
//        no_telp.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
//        alamat.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.3));
        
        //buat root table
 
        TreeItem<Transaksi> trxRoot = new RecursiveTreeItem<>(getTransaksiList(), RecursiveTreeObject::getChildren);
        TableTransaksi.setRoot(trxRoot);
       // set root ke table
        TableTransaksi.setShowRoot(false);  
    }
    
    private void Proses_trx() throws IOException {
        Transaksi transaksi = new Transaksi(Integer.valueOf(norekTextField.getText()),
                    jenisComboBox.getSelectionModel().getSelectedItem(),
                    
                    new Date(),
                    Integer.valueOf(jumlahTextField.getText()),
                    ketTextField.getText());
           if (transaksi.tambah()) {
               List <Transaksi> trxx = Transaksi.transaksi(Integer.parseInt(norekTextField.getText()));
               transaksi = trxx.get(trxx.size()-1);
               Proses_cari();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Berhasil");
               alert.setHeaderText("Transaksi");
               alert.setContentText("Transaksi Berhasil");
               alert.show();
               LaporanPDF.struk(transaksi);


           }else {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Transaksi");
               alert.setHeaderText("Transaksi");
               alert.setContentText("Transaksi Gagal");
               alert.show();
           }
    }
    
    private void Proses_cari() {
    if(norekTextField.getText().isEmpty()){
            namaLabel.setText("");
            nisLabel.setText("");
            saldoLabel.setText("");
        } else {
        Nasabah nasabah = Nasabah.nasabah(Integer.parseInt(norekTextField.getText()));
        if (nasabah != null){
            namaLabel.setText(nasabah.getNama());
            nisLabel.setText(nasabah.getNis());
            saldoLabel.setText(Rupiah.rupiah(nasabah.saldo()));
            getTransaksiList().setAll(Transaksi.transaksi(Integer.parseInt(norekTextField.getText())));
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Transaksi");
            alert.setHeaderText("Transaksi");
            alert.setContentText("No rekening tidak ditemukan");
            alert.show();
        }
        }
        
    }
    
     @FXML
    void cariAction(ActionEvent event) {
        Proses_cari();
    }
    
     @FXML
    void OnAction(ActionEvent event) throws IOException {
        Nasabah nasabah = Nasabah.nasabah(Integer.parseInt(norekTextField.getText()));
        if (jenisComboBox.getSelectionModel().getSelectedItem().equals("Setoran")){
            Proses_trx();
        } else if(jenisComboBox.getSelectionModel().getSelectedItem().equals("Semua Transaksi")) {
            
        } else {
            if (nasabah.saldo() >= Integer.valueOf(jumlahTextField.getText())){
                Proses_trx();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Transaksi");
                alert.setHeaderText("Transaksi");
                alert.setContentText("Saldo tidak mencukupi");
                alert.show();
            }
        }   
    }
    
}
