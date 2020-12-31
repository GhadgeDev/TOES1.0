package com.example.toes;

class WorkerJobDetails {
    private int id;
    private String city;
    private String category_1;
    private String category_1_vc;
    private String category_1_exp;

    private String category_2;
    private String category_2_vc;
    private String category_2_exp;

    private String category_3;
    private String category_3_vc;
    private String category_3_exp;

    public WorkerJobDetails(int id, String city, String category_1, String category_1_vc, String category_1_exp, String category_2, String category_2_vc, String category_2_exp, String category_3, String category_3_vc, String category_3_exp) {
        this.id = id;
        this.city = city;
        this.category_1 = category_1;
        this.category_1_vc = category_1_vc;
        this.category_1_exp = category_1_exp;
        this.category_2 = category_2;
        this.category_2_vc = category_2_vc;
        this.category_2_exp = category_2_exp;
        this.category_3 = category_3;
        this.category_3_vc = category_3_vc;
        this.category_3_exp = category_3_exp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory_1() {
        return category_1;
    }

    public void setCategory_1(String category_1) {
        this.category_1 = category_1;
    }

    public String getCategory_1_vc() {
        return category_1_vc;
    }

    public void setCategory_1_vc(String category_1_vc) {
        this.category_1_vc = category_1_vc;
    }

    public String getCategory_1_exp() {
        return category_1_exp;
    }

    public void setCategory_1_exp(String category_1_exp) {
        this.category_1_exp = category_1_exp;
    }

    public String getCategory_2() {
        return category_2;
    }

    public void setCategory_2(String category_2) {
        this.category_2 = category_2;
    }

    public String getCategory_2_vc() {
        return category_2_vc;
    }

    public void setCategory_2_vc(String category_2_vc) {
        this.category_2_vc = category_2_vc;
    }

    public String getCategory_2_exp() {
        return category_2_exp;
    }

    public void setCategory_2_exp(String category_2_exp) {
        this.category_2_exp = category_2_exp;
    }

    public String getCategory_3() {
        return category_3;
    }

    public void setCategory_3(String category_3) {
        this.category_3 = category_3;
    }

    public String getCategory_3_vc() {
        return category_3_vc;
    }

    public void setCategory_3_vc(String category_3_vc) {
        this.category_3_vc = category_3_vc;
    }

    public String getCategory_3_exp() {
        return category_3_exp;
    }

    public void setCategory_3_exp(String category_3_exp) {
        this.category_3_exp = category_3_exp;
    }
}
