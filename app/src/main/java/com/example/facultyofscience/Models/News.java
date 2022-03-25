package com.example.facultyofscience.Models;

public class News {
    private String title,imgUrl,Date,detailsUrl;
    public News(String title, String imgUrl, String date, String detailsUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
        Date = date;
        this.detailsUrl = detailsUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
}
