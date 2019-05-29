/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.sql2o.Sql2o;

/**
 *
 * @author Aditya Maulana
 */
public class DB {
    @FXML
    private JFXTextField urldbTextField;

    @FXML
    private JFXTextField usernamedbTextField;

    @FXML
    private JFXTextField passowrddbTextField;

    @FXML
    void SimpanAction(ActionEvent event) {
    tulis();
    }
    
    
    public static Sql2o sql2o;
    static {
        sql2o = new Sql2o("jdbc:mysql://localhost/bank_mini","root","root");
    }
    
    public void tulis() {
        String teks1,teks2,teks3;
        teks1 = urldbTextField.getText();
        teks2 = usernamedbTextField.getText();
        teks3 = passowrddbTextField.getText();
        
        String path = "C:\\Users\\Aditya Maulana\\Documents\\NetBeansProjects\\duhan\\db.txt";
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(teks1);
            bw.write(teks2);
            bw.write(teks3);
            bw.close();
        } catch (Exception e) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
