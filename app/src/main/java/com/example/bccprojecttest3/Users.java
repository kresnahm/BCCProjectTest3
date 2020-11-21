package com.example.bccprojecttest3;

public class Users {
    private String nama, thnLahir,kotaAsal;

    public Users(String nama, String thnLahir,String kotaAsal){
        this.nama = nama;
        this.thnLahir = thnLahir;
        this.kotaAsal = kotaAsal;
    }

    public String getNama() {
        return nama;
    }

    public String getThnLahir() {
        return thnLahir;
    }

    public String getKotaAsal() {
        return kotaAsal;
    }
}
