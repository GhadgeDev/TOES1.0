package com.example.toes;

public class RecruiterResponseList {

    private String Name;
    private String Profession;

    public RecruiterResponseList() {

    }

    public RecruiterResponseList(String name, String profession) {
        Name = name;
        Profession = profession;
    }

    //Getter
    public String getName() {
        return Name;
    }
    public String getProfession() {
        return Profession;
    }

    //Setter
    public void setName(String name) {
        Name = name;
    }
    public void setProfession(String profession) {
        Profession = profession;
    }
}
