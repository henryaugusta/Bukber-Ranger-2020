package com.senjapagi.BukberRanger.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BukberModel {
    @Expose
    @SerializedName("nama")
    private String nama_peserta;
    @Expose
    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("password")
    private String jurusan;
    @Expose
    @SerializedName("id_bukber")
    private String id_bukber;
    @Expose
    @SerializedName("tanggal_bukber")
    private String tanggal_bukber;

    public BukberModel(String nama_peserta, String jenis_kelamin, String username, String jurusan, String id_bukber, String tanggal_bukber) {
        this.nama_peserta = nama_peserta;
        this.jenis_kelamin = jenis_kelamin;
        this.username = username;
        this.jurusan = jurusan;
        this.id_bukber = id_bukber;
        this.tanggal_bukber = tanggal_bukber;
    }

    public String getNama_peserta() {
        return nama_peserta;
    }

    public void setNama_peserta(String nama_peserta) {
        this.nama_peserta = nama_peserta;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getId_bukber() {
        return id_bukber;
    }

    public void setId_bukber(String id_bukber) {
        this.id_bukber = id_bukber;
    }

    public String getTanggal_bukber() {
        return tanggal_bukber;
    }

    public void setTanggal_bukber(String tanggal_bukber) {
        this.tanggal_bukber = tanggal_bukber;
    }
}
