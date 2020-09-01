package com.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testyoutube1")
public class Youtuber {



    public Youtuber() {
    }

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "name")
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "time")
    private String time;





    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Youtuber(int id, String title, String name, String comment, String time) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.comment = comment;
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Youtuber{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
