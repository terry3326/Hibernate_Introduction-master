package com.bean;

import javax.persistence.Column;

public class Pojo {
    @Column(name = "item")
    private String item;

    @Column(name = "sum")
    private int sum;


    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getSum() {
        return this.sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "item='" + item + '\'' +
                ", sum='" + sum + '\'' +
                '}';
    }
}
