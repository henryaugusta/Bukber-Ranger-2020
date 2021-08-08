package com.senjapagi.BukberRanger.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PesertaBukberModel {
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
    @Expose
    @SerializedName("success")
    private String success;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("entitas")
    private String entitas;
    @Expose
    @SerializedName("kontak")
    private String kontak;

    public PesertaBukberModel(String nama_peserta, String jenis_kelamin, String username, String jurusan, String id_bukber, String tanggal_bukber, String success, String message, String token, String entitas, String kontak) {
        this.nama_peserta = nama_peserta;
        this.jenis_kelamin = jenis_kelamin;
        this.username = username;
        this.jurusan = jurusan;
        this.id_bukber = id_bukber;
        this.tanggal_bukber = tanggal_bukber;
        this.success = success;
        this.message = message;
        this.token = token;
        this.entitas = entitas;
        this.kontak = kontak;
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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEntitas() {
        return entitas;
    }

    public void setEntitas(String entitas) {
        this.entitas = entitas;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
}