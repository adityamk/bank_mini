/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sql2o.Sql2o;

/**
 *
 * @author Aditya Maulana
 */
public class DB {
    public static Sql2o sql2o;
    private String url;
    private String username;
    private String password;
    private static final String fileName = "db.json";

    public DB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    static {
        try {
            DB db = getDBConfig();
            sql2o = new Sql2o(db.url,db.username,db.password);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DB getDBConfig() throws FileNotFoundException {
        return new Gson().fromJson(new JsonReader(new FileReader(new File(fileName))), DB.class);
    }
    
    public void simpan() throws IOException {
        String db = new Gson().toJson(this);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(db);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    
}