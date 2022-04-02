package com.example.facultyofscience.Models;

public class Announcements {
    private String title, imgUrl, detailsUrl;

    public Announcements(String title, String imgUrl, String detailsUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
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

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
}
