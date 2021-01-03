package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetAcpRejClickRecruiter {
    @SerializedName("message")

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
