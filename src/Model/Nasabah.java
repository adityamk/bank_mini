/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.DB;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sql2o.Connection;

/**
 *
 * @author Aditya Maulana
 */
public class Nasabah extends RecursiveTreeObject<Nasabah> {
private int no_rekening;
private String nis;
private String nama;
private String kelas;
private String no_telp;
private String alamat;

 public static Nasabah nasabah(int no_rekening) {
        return nasabahList.stream().filter(nasabah-> nasabah.getNo_rekening()==no_rekening).findFirst().orElse(null);
    }
 
 public static Nasabah nasabah() {
        return (Nasabah) nasabahList;
    }
 
 public static Nasabah nasabah(Transaksi transaksi) {
        return nasabahList.stream().filter(nasabah-> nasabah.getNo_rekening()==transaksi.getNo_rekening()).findFirst().orElse(null);
    }
 
 public int saldo() {
     return Transaksi.transaksi(this).stream().mapToInt(Transaksi::Total).sum();
    
 } 

private static final ObservableList<Nasabah> nasabahList = FXCollections.observableArrayList();
    public ObjectProperty norekProperty() {
        return new SimpleObjectProperty<Integer>(no_rekening);
    }
    public StringProperty nisProperty() {
        return new SimpleStringProperty(nis);
    }   
     public StringProperty namaProperty() {
        return new SimpleStringProperty(nama);
    }

    public String getNis() {
        return nis;
    }

    public String getNama() {
        return nama;
    }

    public String getKelas() {
        return kelas;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public String getAlamat() {
        return alamat;
    }
    public StringProperty kelasProperty() {
        return new SimpleStringProperty(kelas);
    }
    public StringProperty no_telpProperty() {
        return new SimpleStringProperty(no_telp);
    }
    public StringProperty alamatProperty() {
        return new SimpleStringProperty(alamat);
    }

    public static ObservableList<Nasabah> getNasabahList() {
        updateNasabah();
        return nasabahList;
    }
 
    public static void updateNasabah(){
        try(Connection connection = DB.sql2o.open()) {
                final String query = "SELECT * FROM nasabah";
                nasabahList.setAll(connection.createQuery(query).executeAndFetch(Nasabah.class));
            }

    }
    
    static {
        updateNasabah();
    }

    public Nasabah(String nis, String nama, String kelas, String no_telp, String alamat) {
        this.nis = nis;
        this.nama = nama;
        this.kelas = kelas;
        this.no_telp = no_telp;
        this.alamat = alamat;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
   

    
    public int getNo_rekening() {
        return no_rekening;
    }
 
    public boolean tambah() {
        try (Connection connection = DB.sql2o.open()){
            final String query = "INSERT INTO `nasabah`(`nis`, `nama`, `kelas`, `no_telp`, `alamat`) VALUES (:nis, :nama, :kelas, :no_telp, :alamat)";
            connection.createQuery(query).bind(this).executeUpdate();
            if (connection.getResult() > 0) {
                updateNasabah();
                return true;
            }
            return false;
        }
    }

    public boolean ubah() {
        try (Connection connection = DB.sql2o.open()){
            final String query = "UPDATE `nasabah` SET `nis` = :nis, `nama` = :nama, `kelas` = :kelas, `no_telp` = :no_telp, `alamat` = :alamat WHERE `nasabah`.`no_rekening` = :no_rekening";
            connection.createQuery(query).bind(this).executeUpdate();
            
            if (connection.getResult() > 0) {
                
                updateNasabah();
                return true;
            }
            return false;
        }
    }
    
    public boolean hapus() {
        try (Connection connection = DB.sql2o.open()){
            final String query = "DELETE FROM `nasabah` WHERE no_rekening=:no_rekening";
            connection.createQuery(query).bind(this).executeUpdate();
            if (connection.getResult() > 0) {
                updateNasabah();
                return true;
            }
            return false;
        }
    }

}