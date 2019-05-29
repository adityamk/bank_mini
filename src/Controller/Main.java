package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;

public class Main implements Initializable {
    @FXML
    private BorderPane borderpane;
    @FXML
    private VBox sidebar;
    @FXML
    private JFXButton tombol1;
    @FXML
    private JFXButton tombol2;
    @FXML
    private JFXButton tombol3;
	@FXML
    private JFXButton tombol4;
    @FXML
    private JFXButton tombol5;
    @FXML
    private JFXButton tombol6;
    @FXML
    private JFXButton tombola;

    @FXML
    private JFXButton tombolb;

    @FXML
    private JFXButton tombolc;

    @FXML
    private JFXButton tombold;

    @FXML
    private JFXButton tombole;

    @FXML
    private JFXButton tombolf;
    
    Pane beranda;
    Pane transaksi;
    Pane nasabah;
    Pane laporan;
    Pane about;
    Pane login;
    
    public void initialize(URL location, ResourceBundle resources){
        try {
            beranda = FXMLLoader.load(getClass().getResource("/View/Beranda.fxml"));
            borderpane.setCenter(beranda);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            transaksi = FXMLLoader.load(getClass().getResource("/View/Transaksi.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            nasabah = FXMLLoader.load(getClass().getResource("/View/Nasabah.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            laporan = FXMLLoader.load(getClass().getResource("/View/Laporan.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            about = FXMLLoader.load(getClass().getResource("/View/About.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            login = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     @FXML
    void handleMenu(ActionEvent event) throws IOException {
        //Buat array button dari children vbox
        Object[] objects = sidebar.getChildren().toArray();

        //Lakukan perulangan untuk merubah semua warna tombol menjadi warna default
        for (Object object : objects) {
            ((Node) object).setStyle("fx-background-color: white");
        }

        //Ubah warna yang diselect
        ((Node) event.getSource()).setStyle("-fx-background-color: lightblue");
        
        
        if (event.getSource() == tombol1) {
            borderpane.setCenter(beranda);
        } else if (event.getSource() == tombol2){
            
            borderpane.setCenter(transaksi);
        } else if (event.getSource() == tombol3){
            
            borderpane.setCenter(nasabah);
        } else if (event.getSource() == tombol4){
            
            borderpane.setCenter(laporan);
        } else if (event.getSource() == tombol5){
            borderpane.setCenter(about);
        } else if (event.getSource() == tombol6){
            Scene scene = new Scene(login);
            Stage stage = (Stage) tombol6.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            //borderpane.setCenter(root); 
            //System.exit(0);            
        }
    }
}
