package com.example.toes;

public class User {
    private String auth_token;
    private int id;
    public String getAuth_token() {
        return auth_token;
    }

    public User(String auth_token, int id) {
        this.auth_token = auth_token;
        this.id = id;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
