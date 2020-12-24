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

    public String getPhone() {
        return phone;
    }

    public boolean isIs_superuser() {
        return is_superuser;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getAdharNo() {
        return adharNo;
    }

    public String getProfileImage() {
        return profileImage;
    }

   /* public String getEmail() {
        return email;
    }
*/
    public String getAddress() {
        return address;
    }

   /* public String getRe_password() {
        return re_password;
    }*/

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAdharNo(String adharNo) {
        this.adharNo = adharNo;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

 /*   public void setEmail(String email) {
        this.email = email;
    }*/

    public void setAddress(String address) {
        this.address = address;
    }

   /* public void setRe_password(String re_password) {
        this.re_password = re_password;
    }*/
}
