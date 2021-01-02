package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetWorkerResponses {
    @SerializedName("recruiter_fname")
    private String recruiterFname;

    @SerializedName("recruiter_lname")
    private String recruiterLname;

    @SerializedName("status")
    private String status;

    @SerializedName("contact_no")
    private String contactNo;

    @SerializedName("Job_title")
    private String jobTitle;

    public String getRecruiterFname() {
        return recruiterFname;
    }

    public void setRecruiterFname(String recruiterFname) {
        this.recruiterFname = recruiterFname;
    }

    public String getRecruiterLname() {
        return recruiterLname;
    }

    public void setRecruiterLname(String recruiterLname) {
        this.recruiterLname = recruiterLname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
