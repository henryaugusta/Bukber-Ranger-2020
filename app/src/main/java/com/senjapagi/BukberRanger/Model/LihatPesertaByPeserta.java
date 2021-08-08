package com.senjapagi.BukberRanger.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LihatPesertaByPeserta {
    @Expose
    @SerializedName("nama")
    private String nama_peserta;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("status_ambil")
    private String status_ambil;
    @Expose
    @SerializedName("departure")
    private String departure;

    public LihatPesertaByPeserta(String nama_peserta, String username, String jenis_kelamin, String password, String status_ambil, String departure) {
        this.nama_peserta = nama_peserta;
        this.username = username;
        this.jenis_kelamin = jenis_kelamin;
        this.password = password;
        this.status_ambil = status_ambil;
        this.departure = departure;
    }

    public String getNama_peserta() {
        return nama_peserta;
    }

    public void setNama_peserta(String nama_peserta) {
        this.nama_peserta = nama_peserta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus_ambil() {
        return status_ambil;
    }

    public void setStatus_ambil(String status_ambil) {
        this.status_ambil = status_ambil;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }
}