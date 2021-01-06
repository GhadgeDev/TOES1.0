package com.example.toes;

class Worker {

    private  String id;
    private String category_1;
    private String category_1_vc;
    private String category_1_exp;
    private String category_2;
    private String category_2_vc;
    private String category_2_exp;
    private String category_3;
    private String category_3_vc;
    private String category_3_exp;

    public Worker(String id, String category_1, String category_1_vc, String category_1_exp, String category_2, String category_2_vc, String category_2_exp, String category_3, String category_3_vc, String category_3_exp) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
