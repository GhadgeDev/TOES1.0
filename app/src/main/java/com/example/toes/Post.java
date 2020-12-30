package com.example.toes;

import com.google.gson.annotations.SerializedName;

class Post {
    private String password;
    private String phone;

    private boolean is_superuser = false;
    private boolean is_admin = false;
    private String first_name;
    private String last_name;
    private String username;

    private String dob;
    private String gender;


    private String aadhar_no;
    private String profileImage;
    //private String email;
    private String address;
    private String re_pass;
    private String  profile_image = null;
    //private String re_password;

    public Post(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public Post() {

    }

    public Post(String password, boolean is_superuser, boolean is_admin, String first_name, String last_name, String username, String dob, String gender, String aadhar_no, String profileImage, String address,String profile_image) {
        this.password = password;
        this.is_superuser = is_superuser;
        this.is_admin = is_admin;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.dob = dob;
        this.gender = gender;
        this.aadhar_no = aadhar_no;
        this.profile_image = profile_image;
        this.profileImage = profileImage;
        this.address = address;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

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
        return first_name;
    }

    public void setfName(String fName) {
        this.first_name = fName;
    }

    public String getlName() {
        return last_name;
    }

    public void setlName(String lName) {
        this.last_name = lName;
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
        return aadhar_no;
    }

    public void setAdharNo(String adharNo) {
        this.aadhar_no = adharNo;
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
