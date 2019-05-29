/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import org.sql2o.Connection;

/**
 *
 * @author Aditya Maulana
 */
public class Login extends RecursiveTreeObject<Login> {
    private String id;
    private String username;
    private String password;
   
//    public static Login getLogin(String username){
//    try(Connection connection = Controller.DB.sql2o.open()) {
//            final String query = "SELECT * FROM user where username = :username";
//            return connection.createQuery(query).addParameter("username",username).executeAndFetchFirst(Login.class);
//            
//        }
//    }
    public static Login getLogin(String username){
        try(Connection connection = Controller.DB.sql2o.open()) {
                final String query = "SELECT * FROM user where username = :username";
                return connection.createQuery(query).addParameter("username", username).executeAndFetchFirst(Login.class);
        }
    
    
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }  

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Login{" + "username=" + username + ", password=" + password + '}';
    }
    
    
}
