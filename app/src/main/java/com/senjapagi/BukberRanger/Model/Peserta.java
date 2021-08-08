package com.senjapagi.BukberRanger.Model;

public class Peserta {
    String username, nama, id, jenisKelamin;

    public Peserta(String username, String nama, String id, String jenisKelamin) {
        this.username = username;
        this.nama = nama;
        this.id = id;
        this.jenisKelamin = jenisKelamin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
}

