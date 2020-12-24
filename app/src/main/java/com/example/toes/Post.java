package com.example.toes;

import com.google.gson.annotations.SerializedName;

class Post {
    private  String password;
    private String phone;

    private boolean is_superuser = false;
    private boolean is_admin = false;
    private String fName;
    private String lName;
    private String username;

    private String dob;
    private String gender;


    private String adharNo;
    private String profileImage;
    //private String email;
    private String address;
    private String re_pass;
    //private String re_password;


    public Post(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public Post(String password, String phone, boolean is_superuser, boolean is_admin, String fName, String lName, String username, String dob, String gender, String adharNo, String profileImage, String address,String re_pass) {
        this.password = password;
        this.phone = phone;
        this.is_superuser = is_superuser;
        this.is_admin = is_admin;
        this.fName = fName;
        this.lName = lName;
        this.username = username;
        this.dob = dob;
        this.gender = gender;
        this.adharNo = adharNo;
        this.profileImage = profileImage;
        this.address = address;
        this.re_pass = re_pass;
    }
/*  public Post(boolean is_superuser,boolean is_admin,String fName, String lName, String phone, String address, String dob, String gender) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIs_superuser() {
        return is_superuser;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdharNo() {
        return adharNo;
    }

    public void setAdharNo(String adharNo) {
        this.adharNo = adharNo;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRe_pass() {
        return re_pass;
    }

    public void setRe_pass(String re_pass) {
        this.re_pass = re_pass;
    }
}
