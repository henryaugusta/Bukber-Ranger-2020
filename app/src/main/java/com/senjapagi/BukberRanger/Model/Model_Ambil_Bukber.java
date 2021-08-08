package com.senjapagi.BukberRanger.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_Ambil_Bukber {
    @Expose
    @SerializedName("id_peserta")
    private String id_peserta;
    @Expose
    @SerializedName("nama")
    private String nama;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("status_ambil")
    private String status_ambil;

    public Model_Ambil_Bukber(String id_peserta, String nama, String username, String jenis_kelamin, String token, String status_ambil) {
        this.id_peserta = id_peserta;
        this.nama = nama;
        this.username = username;
        this.jenis_kelamin = jenis_kelamin;
        this.token = token;
        this.status_ambil = status_ambil;
    }

    public String getId_peserta() {
        return id_peserta;
    }

    public void setId_peserta(String id_peserta) {
        this.id_peserta = id_peserta;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus_ambil() {
        return status_ambil;
    }

    public void setStatus_ambil(String status_ambil) {
        this.status_ambil = status_ambil;
    }

}