package com.example.toes;

public class RecruiterListModel {
    private String name;
    private String requirement;

    public RecruiterListModel() {

    }
    public RecruiterListModel(String name, String requirement) {
        this.name = name;
        this.requirement = requirement;
    }
    //Getter
    public String getName() {
        return name;
    }
    public String getRequirement() {
        return requirement;
    }
    //Setter
    public void setName(String name) {
        this.name = name;
    }
    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}
