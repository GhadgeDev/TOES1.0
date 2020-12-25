package com.example.toes;

public class WorkerViewRequestListModel {

    private String vrName;
    private String vrRequirement;

    public WorkerViewRequestListModel() {
    }

    public WorkerViewRequestListModel(String vrName, String vrRequirement) {
        this.vrName = vrName;
        this.vrRequirement = vrRequirement;
    }

    //Getter
    public String getVrName() {
        return vrName;
    }
    public String getVrRequirement() {
        return vrRequirement;
    }

    //Setter
    public void setVrName(String vrName) {
        this.vrName = vrName;
    }

    public void setVrRequirement(String vrRequirement) {
        this.vrRequirement = vrRequirement;
    }
}
