/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

public class LaporanPDF {

    private static final String biasa = "/Asset/times.ttf";
    private static final String bold = "/Asset/timesbold.ttf";
    
    private static Cell cellNoBorder(Image image) {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .add(image);
    }
    
    private static List<String> hari() {
        return Arrays.asList(
                "",
                "Senin",
                "Selasa",
                "Rabu",
                "Kamis",
                "Jumat",
                "Sabtu",
                "Minggu"
        );
    }

    private static List<String> bulan() {
        return Arrays.asList(
                "",
                "Januari",
                "Februari",
                "Maret",
                "April",
                "Mei",
                "Juni",
                "Juli",
                "Agustus",
                "September",
                "Oktober",
                "November",
                "Desember"
        );
    }
    
     private static Table kop_surat(String judul) throws MalformedURLException, IOException {
        BufferedImage img = ImageIO.read(
                Laporan.class.getResourceAsStream("/Asset/logo.PNG"));
        byte[] imageInByte;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(img, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
        }
        Image image = new Image(ImageDataFactory.create(imageInByte));

        return new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 10),
                new UnitValue(UnitValue.PERCENT, 90)}, true)
                .setFontSize(12)
                .addCell(cellNoBorder(image.setAutoScale(true)))
                .addCell(
                        cellNoBorder("YAYASAN PENDIDIKAN  ISLAM TERPADU AL-HAMIDIYYAH\n Perum Btn Bambu Kuning Blok F Rt.05/13 Bojonggede Bogor 16320 \n Telepon : 087770888679\n \n \n" +judul+ "\n \n")
                                .setTextAlignment(TextAlignment.CENTER)
                                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                .setVerticalAlignment(VerticalAlignment.MIDDLE));
                
    }

    private static Table signature(LocalDate tgl) {
        return new Table(
                1)
                .setFontSize(10)
                .setWidth(200)
                .setHeight(100)
                .setMarginTop(50)//antara table dan ttd
                .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .addCell(
                        cellNoBorder("Bogor" + ", " +
                                //hari().get(tgl.getDayOfWeek()) + ", " +
                                tgl.getDayOfMonth() + " " +
                                bulan().get(tgl.getMonthOfYear()) + " " +
                                tgl.getYear())
                                .setTextAlignment(TextAlignment.CENTER))
                .addCell(
                        cellNoBorder("Kepala Sekolah\nHj. Mina Kurniasih")
                                .setTextAlignment(TextAlignment.CENTER)
                                .setVerticalAlignment(VerticalAlignment.BOTTOM));
    }

    public static void daftar_siswa() throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-semua-nasabah-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat("Laporan Nasabah"));
            
            Table transaksiTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16),
                new UnitValue(UnitValue.PERCENT, 16)}, false);
            transaksiTable.setWidth(520);
            transaksiTable.addHeaderCell(cell("No Rekening"));
            transaksiTable.addHeaderCell(cell("NIS"));
            transaksiTable.addHeaderCell(cell("Nama"));
            transaksiTable.addHeaderCell(cell("Kelas"));
            transaksiTable.addHeaderCell(cell("No Telp"));
            transaksiTable.addHeaderCell(cell("Alamat"));
            
            Nasabah.getNasabahList().forEach(nasabah -> {
                transaksiTable.addCell(cell(String.valueOf(nasabah.getNo_rekening())));
                transaksiTable.addCell(cell(nasabah.getNis()));
                transaksiTable.addCell(cell(nasabah.getNama()));
                transaksiTable.addCell(cell(nasabah.getKelas()));
                transaksiTable.addCell(cell(nasabah.getNo_telp()));
                transaksiTable.addCell(cell(nasabah.getAlamat()));
            });
            
            document.add(transaksiTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }
    
    public static void nasabah(Nasabah nasabah, java.util.List<Transaksi> laporan_nasabah_trx) throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-nasabah-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat("Laporan Nasabah"));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true);
            
            detailTable.addCell(cellNoBorder("Nama Nasabah:"));
            detailTable.addCell(cellNoBorder(nasabah.getNama()));
            detailTable.addCell(cellNoBorder("Nomor Rekening:"));
            detailTable.addCell(cellNoBorder(String.valueOf(nasabah.getNo_rekening())));
            
            detailTable.addCell(cellNoBorder("Jenis Transaksi:"));
            detailTable.addCell(cellNoBorder(laporan_nasabah_trx.get(0).getJenis_transaksi()));
            
            document.add(detailTable);
            
            Table transaksiTable = new Table(3);
            transaksiTable.setWidth(520);
            transaksiTable.addHeaderCell(cell("Tanggal Transaksi"));
            transaksiTable.addHeaderCell(cell("Jumlah Transaksi"));
            transaksiTable.addHeaderCell(cell("Keterangan"));
            
            //transaksiTable.addHeaderCell(cell("Jenis Transaksi"));
            
            laporan_nasabah_trx.forEach(transaksi -> {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                transaksiTable.addCell(cell(dateFormat.format(transaksi.getTanggal_transaksi())));
                //transaksiTable.addCell(cell(new LocalDate(transaksi.getTanggal_transaksi()).toString()));
                transaksiTable.addCell(cell(Rupiah.rupiah(transaksi.getJumlah_transaksi())));
                transaksiTable.addCell(cell(transaksi.getKeterangan()));
                
            });
            
            document.add(transaksiTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }
    
    
    public static void nasabah_semuatrx(Nasabah nasabah, java.util.List<Transaksi> laporan_nasabah_semua_trx) throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-nasabah-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat(""));
            
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true);
            
            detailTable.addCell(cellNoBorder("Nama Nasabah:"));
            detailTable.addCell(cellNoBorder(nasabah.getNama()));
            detailTable.addCell(cellNoBorder("Nomor Rekening:"));
            detailTable.addCell(cellNoBorder(String.valueOf(nasabah.getNo_rekening())));
            document.add(detailTable);
            
            Table transaksiTable = new Table(4);
            transaksiTable.setWidth(520);
            transaksiTable.addHeaderCell(cell("Tanggal Transaksi"));
            transaksiTable.addHeaderCell(cell("Jenis Transaksi"));
            transaksiTable.addHeaderCell(cell("Jumlah Transaksi"));
            transaksiTable.addHeaderCell(cell("Keterangan"));
            
            laporan_nasabah_semua_trx.forEach(transaksi -> {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                transaksiTable.addCell(cell(dateFormat.format(transaksi.getTanggal_transaksi())));
                transaksiTable.addCell(cell(transaksi.getJenis_transaksi()));
                transaksiTable.addCell(cell(Rupiah.rupiah(transaksi.getJumlah_transaksi())));
                transaksiTable.addCell(cell(transaksi.getKeterangan()));
            });
            
            document.add(transaksiTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }
    
    //CETAK LAPORAN BANK SEMUA TRX
    public static void bank_semuatrx(java.util.List<Transaksi> laporan_bank_semua_trx) throws IOException {
        LocalDate localDate = new LocalDate(new Date());
        String fileName = String.format("laporan-nasabah-%s.pdf", localDate.toString());

        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf)) {
            document.add(kop_surat("Laporan Bank"));
            Table detailTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 50),
                new UnitValue(UnitValue.PERCENT, 50)}, true);
            Table transaksiTable = new Table(6);
            transaksiTable.setWidth(520);
            transaksiTable.addHeaderCell(cell("No Rekening"));
            transaksiTable.addHeaderCell(cell("Nama"));
            transaksiTable.addHeaderCell(cell("Tanggal Transaksi"));
            transaksiTable.addHeaderCell(cell("Jenis Transaksi"));
            transaksiTable.addHeaderCell(cell("Jumlah Transaksi"));
            transaksiTable.addHeaderCell(cell("Keterangan"));
            laporan_bank_semua_trx.forEach(transaksi -> {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                transaksiTable.addCell(cell(String.valueOf(Nasabah.nasabah(transaksi).getNo_rekening())));
                transaksiTable.addCell(cell(Nasabah.nasabah(transaksi).getNama()));
                transaksiTable.addCell(cell(dateFormat.format(transaksi.getTanggal_transaksi())));
                transaksiTable.addCell(cell(transaksi.getJenis_transaksi()));
                transaksiTable.addCell(cell(Rupiah.rupiah(transaksi.getJumlah_transaksi())));
                transaksiTable.addCell(cell(transaksi.getKeterangan()));
            });
            
            document.add(transaksiTable.setMarginTop(10));
            document.add(signature(localDate));
        }
        showReport(fileName);
    }
    
    //STRUK
    public static void struk(Transaksi transaksi) throws IOException {
        LocalDate localDate = new LocalDate(new Date());  
        String fileName = "Receipt"+transaksi.getNo_transaksi()+".pdf";
          PdfFont boldFont = PdfFontFactory.createFont(bold, true);
          PdfWriter writer = new PdfWriter(fileName);
          PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf, new PageSize(new Rectangle(226.8f, 600f)))) {
            document.add(
                    new Paragraph()
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontSize(5)
                            .add(new Text("BANK MINI AL-HAMIDIYYAH").setFont(boldFont))
                            .add("\n Perum Bambu Kuning Blok F7 Rt05/13 Bojonggede Timur Kec.Bojonggede")
                            .add("\n-----------------------------------------------------------------------------------------\n")
                            .add("")
                            //.add(items.get(0).getNo_meja())
                            .add("\tTanggal:")
                            .add(localDate+" "+new LocalTime().toString().substring(0, 8))
                            .add("\n-----------------------------------------------------------------------------------------\n")
                            .add(new Text(transaksi.getJenis_transaksi()).setFont(boldFont))
                    
            );
            
            Table itemsTable = new Table(new UnitValue[]{
                new UnitValue(UnitValue.PERCENT, 25),
                new UnitValue(UnitValue.PERCENT, 10),
                new UnitValue(UnitValue.PERCENT, 30),}, false);
            
            itemsTable.setFontSize(6);
            itemsTable.setTextAlignment(TextAlignment.LEFT);
            itemsTable.addCell(cellNoBorder("No Rekening").setFontSize(6));
            itemsTable.addCell(cellNoBorder(":").setFontSize(6));
            itemsTable.addCell(cellNoBorder(String.valueOf(transaksi.getNo_rekening())).setFontSize(6));
            itemsTable.addCell(cellNoBorder("Jumlah").setFontSize(6));
            itemsTable.addCell(cellNoBorder(":").setFontSize(6));
            itemsTable.addCell(cellNoBorder(Rupiah.rupiah(transaksi.getJumlah_transaksi())).setFontSize(6));
            itemsTable.addCell(cellNoBorder("Saldo").setFontSize(6));
            itemsTable.addCell(cellNoBorder(":").setFontSize(6));
            itemsTable.addCell(cellNoBorder(Rupiah.rupiah(Nasabah.nasabah(transaksi).saldo())).setFontSize(6));
            itemsTable.addCell(cellNoBorder("Keterangan").setFontSize(6));
            itemsTable.addCell(cellNoBorder(":").setFontSize(6));
            itemsTable.addCell(cellNoBorder(transaksi.getKeterangan()).setFontSize(6));
            document.add(itemsTable);
            
            document.add(
                    new Paragraph()
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontSize(5)
                            .add("Terima Kasih Telah Melakukan Transaksi")
            );
        }
        showReport(fileName);
    }
        
    private static Cell cellNoBorder(String text) {
        return new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(text));
    }

    private static Cell cell(String text) {
        return new Cell().add(new Paragraph(text));
    }

    private static void showReport(String fileName) {
        File file = new File(fileName);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
        }
    }
}