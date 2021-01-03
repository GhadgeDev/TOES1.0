package com.example.toes;

class Worker {

    private String category_1;
    private String category_2;
    private String category_3;

    public Worker(String category_1, String category_2, String category_3) {
        this.category_1 = category_1;
        this.category_2 = category_2;
        this.category_3 = category_3;
    }

    public String getCategory_1() {
        return category_1;
    }

    public void setCategory_1(String category_1) {
        this.category_1 = category_1;
    }

    public String getCategory_2() {
        return category_2;
    }

    public void setCategory_2(String category_2) {
        this.category_2 = category_2;
    }

    public String getCategory_3() {
        return category_3;
    }

    public void setCategory_3(String category_3) {
        this.category_3 = category_3;
    }
}
