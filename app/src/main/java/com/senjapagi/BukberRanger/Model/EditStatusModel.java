package com.senjapagi.BukberRanger.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditStatusModel {
    @Expose
    @SerializedName("id")
    private String id;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private String success;

    public EditStatusModel(String id, String status, String message, String success) {
        this.id = id;
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
