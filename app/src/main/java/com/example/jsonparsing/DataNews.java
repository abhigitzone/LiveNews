package com.example.jsonparsing;

class DataNews {
    private String newsType;
    private String newsTitle;
    private String newsDesc;
    private String newsAuthor;
    private String newsDate;
    private String newsImg;
    private String newsUrl;

    DataNews(String newsType, String newsTitle, String newsDesc, String newsAuthor, String newsDate, String newsImg, String newsUrl) {
        this.newsType = newsType;
        this.newsTitle = newsTitle;
        this.newsDesc = newsDesc;
        this.newsAuthor = newsAuthor;
        this.newsDate = newsDate;
        this.newsImg = newsImg;
        this.newsUrl = newsUrl;
    }

    String getNewsType() {
        return newsType;
    }

    String getNewsTitle() {
        return newsTitle;
    }

    String getNewsDesc() {
        return newsDesc;
    }

    String getNewsAuthor() {
        return newsAuthor;
    }

    String getNewsDate() {
        return newsDate;
    }

    String getNewsImg() {
        return newsImg;
    }

    String getNewsUrl() {
        return newsUrl;
    }
}
