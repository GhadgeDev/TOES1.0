package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetRecruiterResponses {
    @SerializedName("worker_fname")
    private String workerFname;

    @SerializedName("worker_lname")
    private String workerLname;

    @SerializedName("status")
    private String status;

    @SerializedName("contact_no")
    private String contactNo;

    @SerializedName("Job_title")
    private String jobTitle;


    public String getWorkerFname() {
        return workerFname;
    }

    public void setWorkerFname(String workerFname) {
        this.workerFname = workerFname;
    }

    public String getWorkerLname() {
        return workerLname;
    }

    public void setWorkerLname(String workerLname) {
        this.workerLname = workerLname;
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
