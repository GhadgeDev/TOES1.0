package com.example.toes;

public class RecruiterResponseList {

    private String Name;

    public RecruiterResponseList() {

    }

    public RecruiterResponseList(String name) {
        Name = name;
    }

    //Getter
    public String getName() {
        return Name;
    }

    //Setter
    public void setName(String name) {
        Name = name;
    }
}
