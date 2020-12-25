package com.example.toes;

public class WorkerResponseList {
    private String rName;
    private String rRequirement;

    public WorkerResponseList() {
    }

    public WorkerResponseList(String rName, String rRequirement) {
        this.rName = rName;
        this.rRequirement = rRequirement;
    }

    //Getter
    public String getrName() {
        return rName;
    }
    public String getrRequirement() {
        return rRequirement;
    }

    //Setter
    public void setrName(String rName) {
        this.rName = rName;
    }
    public void setrRequirement(String rRequirement) {
        this.rRequirement = rRequirement;
    }
}
