package com.example.toes;

import com.google.gson.annotations.SerializedName;

public class GetSpecificWorkerModel {

    @SerializedName("worker_name")
    private String workerName;

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


    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
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
}