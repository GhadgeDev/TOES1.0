package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetRecruiterJobDetails {

    @SerializedName("fname")
    private String fname;

    @SerializedName("lname")
    private String lname;

    @SerializedName("address")
    private String address;

    @SerializedName("job_Description")
    private String job_Description;

    public GetRecruiterJobDetails(String fname, String lname, String address, String job_Description) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
        this.job_Description = job_Description;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobDescription() {
        return job_Description;
    }

    public void setJobDescription(String jobDescription) {
        this.job_Description = jobDescription;
    }

}
