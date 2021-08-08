package com.senjapagi.BukberRanger.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateJadwalModel {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("kategori")
    private String kategori;
    @Expose
    @SerializedName("tanggal")
    private String tanggal;
    @Expose
    @SerializedName("kuota_ikhwan")
    private String kuota_ikhwan;
    @Expose
    @SerializedName("kuota_akhwat")
    private String kuota_akhwat;
    @Expose
    @SerializedName("lauk")
    private String lauk;
    @SerializedName("catatan")
    private String catatan;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private String success;

    public UpdateJadwalModel(String id, String kategori, String tanggal, String kuota_ikhwan, String kuota_akhwat, String lauk, String catatan, String status, String message, String success) {
        this.id = id;
        this.kategori = kategori;
        this.tanggal = tanggal;
        this.kuota_ikhwan = kuota_ikhwan;
        this.kuota_akhwat = kuota_akhwat;
        this.lauk = lauk;
        this.catatan = catatan;
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKuota_ikhwan() {
        return kuota_ikhwan;
    }

    public void setKuota_ikhwan(String kuota_ikhwan) {
        this.kuota_ikhwan = kuota_ikhwan;
    }

    public String getKuota_akhwat() {
        return kuota_akhwat;
    }

    public void setKuota_akhwat(String kuota_akhwat) {
        this.kuota_akhwat = kuota_akhwat;
    }

    public String getLauk() {
        return lauk;
    }

    public void setLauk(String lauk) {
        this.lauk = lauk;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
