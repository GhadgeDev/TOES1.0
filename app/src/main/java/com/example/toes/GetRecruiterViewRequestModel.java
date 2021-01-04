package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetRecruiterViewRequestModel {
    @SerializedName("job_title")
    private String jobTitle;

    @SerializedName("job_description")
    private String jobDescription;

    @SerializedName("amount")
    private Integer amount;

    @SerializedName("woker_fname")
    private String wokerFname;

    @SerializedName("woker_lname")
    private String wokerLname;

    @SerializedName("woker_address")
    private String wokerAddress;

    @SerializedName("job_id")
    private int jbid;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getWokerFname() {
        return wokerFname;
    }

    public void setWokerFname(String wokerFname) {
        this.wokerFname = wokerFname;
    }

    public String getWokerLname() {
        return wokerLname;
    }

    public void setWokerLname(String wokerLname) {
        this.wokerLname = wokerLname;
    }

    public String getWokerAddress() {
        return wokerAddress;
    }

    public void setWokerAddress(String wokerAddress) {
        this.wokerAddress = wokerAddress;
    }

    public int getJbid() {
        return jbid;
    }

    public void setJbid(int jbid) {
        this.jbid = jbid;
    }
}
