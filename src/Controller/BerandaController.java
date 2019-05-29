/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Nasabah;
import Model.Rupiah;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Aditya Maulana
 */
public class BerandaController implements Initializable {
    @FXML
    private Label saldoBankLabel;
    
    @FXML
    private Label tglLabel;

    @FXML
    private Label jamLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Nasabah.updateNasabah();
        Thread thread = new Thread(()->{
           while(!Thread.interrupted()){
               try {
                   Platform.runLater(()-> saldoBankLabel.setText(Rupiah.rupiah(Nasabah.getNasabahList().stream().mapToInt(Nasabah::saldo).sum())));
                   Thread.sleep(5000);
               } catch (InterruptedException ex) {
                   Logger.getLogger(BerandaController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        });
        thread.start();
        
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        tglLabel.setText(simpleDateFormat.format(date));
        Thread threadjam = new Thread(()->{
           while(!Thread.interrupted()){
               try {
                   Date dt = new Date();
                   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                   Platform.runLater(()-> jamLabel.setText(sdf.format(dt)));
                   Thread.sleep(1000);
               } catch (InterruptedException ex) {
                   Logger.getLogger(BerandaController.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        });
        threadjam.start();
    }
}
