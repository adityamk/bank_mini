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
import Model.Login;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aditya Maulana
 */
public class LoginController implements Initializable {
    @FXML
    private JFXTextField usernameTextField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton settingButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXPasswordField passwordTextField;
    
    @FXML
    private AnchorPane anchor;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }    
    
    @FXML
    private void actionHandle(javafx.event.ActionEvent event) throws IOException {
        if (event.getSource() == cancelButton) {
            System.exit(0);
        } else if (event.getSource() == loginButton){
            cekLogin();
        } else if (event.getSource() == settingButton){
            settingDb();
        }
    }
    
    
    private void cekLogin(){
      Login login = Login.getLogin(usernameTextField.getText());
      //System.out.println(login);
      
      if(login !=null){
            if(login.getPassword().equals(passwordTextField.getText())){
                Stage stage=(Stage) anchor.getScene().getWindow();
                System.out.println("Berhasil");
                anchor.getChildren().clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main.fxml"));
                
                try {
                    Parent parent = loader.load();
                    stage.setScene(new Scene(parent));
                    stage.centerOnScreen();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                   
                
              } else {
                System.out.println("Password Salah");
                passwordTextField.requestFocus();
              }   
      }else {
          System.out.println("username tidak di temukan");
          usernameTextField.requestFocus();
      }
      
    }
    
    private void settingDb() throws IOException{
        Stage stage=(Stage) anchor.getScene().getWindow();
        System.out.println("Berhasil");
        anchor.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SettingDB.fxml"));
        Parent parent = loader.load();
                    stage.setScene(new Scene(parent));
                    stage.centerOnScreen();
    }
    
}
