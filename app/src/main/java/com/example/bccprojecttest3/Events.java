package com.example.bccprojecttest3;

public class Events {
    String nama,tanggal,tempat,status,deskripsi,hari,ijinMasuk;

    public Events(){

    }
    public Events(String nama, String tanggal, String tempat, String status, String deskripsi, String hari, String ijinMasuk) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.tempat = tempat;
        this.status = status;
        this.deskripsi = deskripsi;
        this.hari = hari;
        this.ijinMasuk = ijinMasuk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getIjinMasuk() {
        return ijinMasuk;
    }

    public void setIjinMasuk(String ijinMasuk) {
        this.ijinMasuk = ijinMasuk;
    }
}
