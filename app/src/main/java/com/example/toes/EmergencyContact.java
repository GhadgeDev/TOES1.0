package com.example.toes;

class EmergencyContact {

    private String id;
    private String emergency_contact;
    private String user ;

    public EmergencyContact(String id, String contact_no, String user) {
        this.id = id;
        this.emergency_contact = contact_no;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact_no() {
        return emergency_contact;
    }

    public void setContact_no(String contact_no) {
        this.emergency_contact = contact_no;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
