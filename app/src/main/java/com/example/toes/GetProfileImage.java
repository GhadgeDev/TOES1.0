package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetProfileImage {
    @SerializedName("id")
    private Integer id;

    @SerializedName("profile_image")
    private String profileImage;

    @SerializedName("user")
    private Integer user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
