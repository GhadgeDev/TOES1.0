package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetSpecificWorkerModel {


    @SerializedName("fname")
    private String fname;

    @SerializedName("lname")
    private String lname;

    @SerializedName("contact_no")
    private String contactNo;

    @SerializedName("category")
    private String category;

    @SerializedName("visiting_charges")
    private String visitingCharges;

    @SerializedName("experience")
    private Integer experience;

    @SerializedName("Worker_id")
    private Integer workerId;

    @SerializedName("address")
    private String address;

    @SerializedName("smartphone")
    private Integer smartphone;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVisitingCharges() {
        return visitingCharges;
    }

    public void setVisitingCharges(String visitingCharges) {
        this.visitingCharges = visitingCharges;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSmartphone() {
        return smartphone;
    }

    public void setSmartphone(Integer smartphone) {
        this.smartphone = smartphone;
    }
}