package com.senjapagi.BukberRanger.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateStatusAmbil {
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("id_bukber")
    private String id_bukber;
    @Expose
    @SerializedName("message")
    private String message;
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
    @SerializedName("status_ambil")
    private String status_ambil;

    public UpdateStatusAmbil(String token, String id_bukber, String message, String nama_peserta, String username, String jenis_kelamin, String status_ambil) {
        this.token = token;
        this.id_bukber = id_bukber;
        this.message = message;
        this.nama_peserta = nama_peserta;
        this.username = username;
        this.jenis_kelamin = jenis_kelamin;
        this.status_ambil = status_ambil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId_bukber() {
        return id_bukber;
    }

    public void setId_bukber(String id_bukber) {
        this.id_bukber = id_bukber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getStatus_ambil() {
        return status_ambil;
    }

    public void setStatus_ambil(String status_ambil) {
        this.status_ambil = status_ambil;
    }
}
