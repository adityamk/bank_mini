/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.DB;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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


public class Transaksi extends RecursiveTreeObject<Transaksi> {
    private int no_transaksi;
    private int no_rekening;
    private String jenis_transaksi;
    private Date tanggal_transaksi;
    private int jumlah_transaksi;
    private String keterangan;
    
    private static final ObservableList<Transaksi> transaksiList = FXCollections.observableArrayList();
    private static final ObservableList<Transaksi> transaksiListbyket = FXCollections.observableArrayList();
    private static final ObservableList<Transaksi> transaksiListbyket_pem = FXCollections.observableArrayList();

    public static ObservableList<Transaksi> getTransaksiListbyket_pem() {
        return transaksiListbyket_pem;
    }

    public static ObservableList<Transaksi> getTransaksiListbyket() {
        return transaksiListbyket;
    }
   
    public ObjectProperty notrxProperty() {
        return new SimpleObjectProperty<>(no_transaksi);
    }
     public ObjectProperty norekProperty() {
        return new SimpleObjectProperty<>(no_rekening);
    }
    public StringProperty jenisProperty() {
        return new SimpleStringProperty(jenis_transaksi);
    }   
     public ObjectProperty tanggalProperty() {
        return new SimpleObjectProperty<>(tanggal_transaksi);
    }
    public StringProperty jumlahProperty() {
        return new SimpleStringProperty(Rupiah.rupiah(jumlah_transaksi));
    } 
    public StringProperty ketProperty() {
        return new SimpleStringProperty(keterangan);
    }
    public Transaksi(int no_rekening, String jenis_transaksi, Date tanggal_transaksi, int jumlah_transaksi, String keterangan) {
        this.no_rekening = no_rekening;
        this.jenis_transaksi = jenis_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
        this.jumlah_transaksi = jumlah_transaksi;
        this.keterangan = keterangan;
    }
    
    public static List <Transaksi> transaksi (int norek){
        updateTransaksi();
        return transaksiList
                .stream()
                .filter((t) -> t.getNo_rekening()==norek)
                .collect(Collectors.toList());
    }
    
   
    public static List <Transaksi> transaksi (Nasabah nasabah, String jenis_transaksi, LocalDate dari, LocalDate sampai){
        System.out.println("jalan gak"); 
        if (jenis_transaksi.equals("Semua Transaksi")){
        return transaksi(nasabah)
                .stream()
                .filter((t) -> {
                    Transaksi transaksi = (Transaksi) t;
                    Date date = transaksi.getTanggal_transaksi();
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    LocalDate localDate = localDateTime.toLocalDate();
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList());
        }else{
        return transaksi(nasabah)
                .stream()
                .filter((t) -> t.getJenis_transaksi().equals(jenis_transaksi))
                .collect(Collectors.toList())
                .stream()
                .filter((t) -> {
                    Transaksi transaksi = (Transaksi) t;
                    Date date = transaksi.getTanggal_transaksi();
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    LocalDate localDate = localDateTime.toLocalDate();
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList());
        }
    }

    public static List <Transaksi> transaksiBank (LocalDate dari, LocalDate sampai){
        return getTransaksi()
                .stream()
                .filter((t) -> {
                    Transaksi transaksi = (Transaksi) t;
                    Date date = transaksi.getTanggal_transaksi();
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    LocalDate localDate = localDateTime.toLocalDate();
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList());
    }
    
    public static List <Transaksi> transaksiBank (LocalDate dari, LocalDate sampai, String jenis){
        return getTransaksi()
                .stream()
                .filter((t) -> {
                    Transaksi transaksi = (Transaksi) t;
                    Date date = transaksi.getTanggal_transaksi();
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    LocalDate localDate = localDateTime.toLocalDate();
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList())
                .stream()
                .filter(t -> t.getJenis_transaksi().equals(jenis))
                .collect(Collectors.toList());
    }
    
    public static List <Transaksi> transaksi_keuangan (LocalDate dari, LocalDate sampai){
        return getTransaksi()
                .stream()
                .filter((t) -> {
                    Transaksi transaksi = (Transaksi) t;
                    Date date = transaksi.getTanggal_transaksi();
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    LocalDate localDate = localDateTime.toLocalDate();
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList())
                .stream()
                .filter((t1) -> {
                    return t1.getKeterangan().equals("BIAYA ADMIN");
                })
                .collect(Collectors.toList());
    }
    
    public static List <Transaksi> transaksi_pembayaran (LocalDate dari, LocalDate sampai){
        return getTransaksi()
                .stream()
                .filter((t) -> {
                    Transaksi transaksi = (Transaksi) t;
                    Date date = transaksi.getTanggal_transaksi();
                    Instant instant = Instant.ofEpochMilli(date.getTime());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    LocalDate localDate = localDateTime.toLocalDate();
                    return localDate.isAfter(dari) && localDate.isBefore(sampai) || localDate.equals(sampai);
                }).collect(Collectors.toList())
                .stream()
                .filter((t1) -> {
                    return t1.getKeterangan().contains("BAYAR");
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public String toString() {
        return "Transaksi{" + "no_transaksi=" + no_transaksi + ", no_rekening=" + no_rekening + ", jenis_transaksi=" + jenis_transaksi + ", tanggal_transaksi=" + tanggal_transaksi + ", jumlah_transaksi=" + jumlah_transaksi + ", keterangan=" + keterangan + '}';
    }
    
    public int Total() {
        if(jenis_transaksi.equals("Setoran")){
        return jumlah_transaksi;
        } return -jumlah_transaksi;
    }
    
    public boolean tambah() {
        try (Connection connection = DB.sql2o.open()){
            final String query = "INSERT INTO `transaksi`(`no_rekening`, `jenis_transaksi`, `tanggal_transaksi`, `jumlah_transaksi`, `keterangan`) VALUES (:no_rekening, :jenis_transaksi, :tanggal_transaksi, :jumlah_transaksi, :keterangan)";
            connection.createQuery(query).bind(this).executeUpdate();
            if (connection.getResult() > 0) {
               updateTransaksi();
                return true;
            }
            return false;
        }
    }

    public static List<Transaksi> transaksi(Nasabah nasabah){
        return getTransaksi().stream().filter((t) -> 
        t.getNo_rekening()==nasabah.getNo_rekening()
        ).collect(Collectors.toList());
    
    }
    
    public static List<Transaksi> getTransaksi() {
        try (Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM transaksi";
            return connection.createQuery(query).executeAndFetch(Transaksi.class);
        }
    }
    
   
    private static void updateTransaksi() {
        try (Connection connection = DB.sql2o.open()) {
            final String query = "SELECT * FROM transaksi";
            transaksiList.setAll(connection.createQuery(query).executeAndFetch(Transaksi.class));
        }
    }

    public void setNo_transaksi(int no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    public void setNo_rekening(int no_rekening) {
        this.no_rekening = no_rekening;
    }

    public void setJenis_transaksi(String jenis_transaksi) {
        this.jenis_transaksi = jenis_transaksi;
    }

    public void setTanggal_transaksi(Date tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public void setJumlah_transaksi(int jumlah_transaksi) {
        this.jumlah_transaksi = jumlah_transaksi;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getNo_transaksi() {
        return no_transaksi;
    }

    public int getNo_rekening() {
        return no_rekening;
    }

    public String getJenis_transaksi() {
        return jenis_transaksi;
    }

    public Date getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public int getJumlah_transaksi() {
        return jumlah_transaksi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public static ObservableList<Transaksi> getTransaksiList() {
        return transaksiList;
    }
}