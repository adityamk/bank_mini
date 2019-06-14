/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Aditya Maulana
 */
public class SettingDBController implements Initializable {
    
    @FXML
    private JFXTextField urldbTextField;
    
    @FXML
    private JFXTextField usernamedbTextField;
    
    @FXML
    private JFXTextField passowrddbTextField;
    
    @FXML
    private void SimpanAction(ActionEvent event) throws IOException {
        DB db = new DB(urldbTextField.getText(), usernamedbTextField.getText(), passowrddbTextField.getText());
        db.simpan();
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DB db = DB.getDBConfig();
            urldbTextField.setText(db.getUrl());
            usernamedbTextField.setText(db.getUsername());
            passowrddbTextField.setText(db.getPassword());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SettingDBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}