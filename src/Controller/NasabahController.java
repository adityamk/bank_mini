/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Aditya Maulana
 */
import Model.Nasabah;
import static Model.Nasabah.getNasabahList;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;

public class NasabahController implements Initializable  {

    @FXML
    private JFXTextField nisTextField;

    @FXML
    private JFXTextField namaTextField;

    @FXML
    private JFXTextField kelasTextField;

    @FXML
    private JFXTextField no_telpTextField;

    @FXML
    private JFXTextField alamatTextField;

    @FXML
    private JFXButton simpanButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton hapusButton;
    
    @FXML
    private JFXTreeTableView<Nasabah> TableNasabah;

    private Nasabah nasabah;
    
    @FXML
    void batalHandle(ActionEvent event) {
           resetForm();
    }

    @FXML
    void editHandle(ActionEvent event) {
           nasabah.setNis(nisTextField.getText());
           nasabah.setNama(namaTextField.getText());
           nasabah.setKelas(kelasTextField.getText());
           nasabah.setNo_telp(no_telpTextField.getText());
           nasabah.setAlamat(alamatTextField.getText());
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Nasabah");
           if (validasi() && nasabah.ubah()){
               alert.setHeaderText("Data Nasabah");
               alert.setContentText("Data berhasil diubah");
               alert.show();
           } else {
               alert.setAlertType(AlertType.ERROR);
               alert.setHeaderText("Data Nasabah");
               alert.setContentText("Data gagal diubah");
               alert.show();
           }
    }

    @FXML
    void hapusHandle(ActionEvent event) {
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Nasabah");
           if (nasabah.hapus()){
               alert.setHeaderText("Data Nasabah");
               alert.setContentText("Data berhasil dihapus");
               alert.show();
           } else {
               alert.setAlertType(AlertType.ERROR);
               alert.setHeaderText("Data Nasabah");
               alert.setContentText("Data gagal dihapus");
               alert.show();
           }
    }

    @FXML
    void simpanHandle(ActionEvent event) {
           Nasabah nasabah = new Nasabah(nisTextField.getText(),namaTextField.getText(),kelasTextField.getText(),no_telpTextField.getText(),alamatTextField.getText());
           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Nasabah");
           if (validasi() && nasabah.tambah()){
               alert.setHeaderText("Data Nasabah");
               alert.setContentText("Data berhasil disimpan");
               alert.show();
           } else {
               alert.setAlertType(AlertType.ERROR);
               alert.setHeaderText("Data Nasabah");
               alert.setContentText("Data gagal disimpan");
               alert.show();
           }
    }

     @FXML
    private void pilihHandle(MouseEvent event) {
        editButton.setDisable(false);
        hapusButton.setDisable(false);
        nasabah = TableNasabah.getSelectionModel().getSelectedItem().getValue();
        nisTextField.setText(nasabah.getNis());
        namaTextField.setText(nasabah.getNama());
        kelasTextField.setText(nasabah.getKelas());
        no_telpTextField.setText(nasabah.getNo_telp());
        alamatTextField.setText(nasabah.getAlamat());
        simpanButton.setDisable(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editButton.setDisable(true);
        hapusButton.setDisable(true);
        TreeTableColumn<Nasabah, Integer> norek = new TreeTableColumn<>("No. Rek");
        TreeTableColumn<Nasabah, String> nis = new TreeTableColumn<>("NIS");
        TreeTableColumn<Nasabah, String> nama = new TreeTableColumn<>("Nama");
        TreeTableColumn<Nasabah, String> kelas = new TreeTableColumn<>("Kelas");
        TreeTableColumn<Nasabah, String> no_telp = new TreeTableColumn<>("No. Telp");
        TreeTableColumn<Nasabah, String> alamat = new TreeTableColumn<>("Alamat");
        //Setting data column
        norek.setCellValueFactory(param -> param.getValue().getValue().norekProperty());
        nis.setCellValueFactory(param -> param.getValue().getValue().nisProperty());
        nama.setCellValueFactory(param -> param.getValue().getValue().namaProperty());
        kelas.setCellValueFactory(param -> param.getValue().getValue().kelasProperty());
        no_telp.setCellValueFactory(param -> param.getValue().getValue().no_telpProperty());
        alamat.setCellValueFactory(param -> param.getValue().getValue().alamatProperty());
        
        TableNasabah.getColumns().add(norek);
        TableNasabah.getColumns().add(nis);
        TableNasabah.getColumns().add(nama);
        TableNasabah.getColumns().add(kelas);
        TableNasabah.getColumns().add(no_telp);
        TableNasabah.getColumns().add(alamat);
        //TableNasabah.setColumnResizePolicy(TableNasabah.CONSTRAINED_RESIZE_POLICY);
        norek.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
        nis.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
        nama.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
        kelas.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.1));
        no_telp.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.2));
        alamat.prefWidthProperty().bind(TableNasabah.prefWidthProperty().multiply(0.3));
        
        //buat root table
 
        TreeItem<Nasabah> nasabahRoot = new RecursiveTreeItem<>(getNasabahList(), RecursiveTreeObject::getChildren);
        TableNasabah.setRoot(nasabahRoot);
       // set root ke table
        TableNasabah.setShowRoot(false);       
    }
    
    private boolean validasi(){
        return !nisTextField.getText().isEmpty() && !namaTextField.getText().isEmpty() && !kelasTextField.getText().isEmpty() && !no_telpTextField.getText().isEmpty() && !alamatTextField.getText().isEmpty();
    }
    
     
    private void resetForm() {
        simpanButton.setDisable(false);
        editButton.setDisable(true);
        hapusButton.setDisable(true);
        nisTextField.setText("");
        namaTextField.setText("");
        kelasTextField.setText("");
        no_telpTextField.setText("");
        alamatTextField.setText("");
    }
    


}
