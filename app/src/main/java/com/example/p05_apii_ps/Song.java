package com.example.p05_apii_ps;

import java.io.Serializable;

public class Song implements Serializable {

    private int id;
    private String title;
    private String singer;
    private String year;
    private int stars;

    public Song(int id, String title, String singer, String year, int stars) {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.year = year;
        this.stars = stars;
    }

    public int getId() {
        return id;
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

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return year;
    }
}
