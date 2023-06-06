package com.mobileclient.domain;

import java.io.Serializable;

public class Movie implements Serializable {
    /*电影id*/
    private int movieId;
    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /*影片类型*/
    private int movieTypeObj;
    public int getMovieTypeObj() {
        return movieTypeObj;
    }
    public void setMovieTypeObj(int movieTypeObj) {
        this.movieTypeObj = movieTypeObj;
    }

    /*影片名称*/
    private String movieName;
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    /*影片图片*/
    private String moviePhoto;
    public String getMoviePhoto() {
        return moviePhoto;
    }
    public void setMoviePhoto(String moviePhoto) {
        this.moviePhoto = moviePhoto;
    }

    /*导演*/
    private String director;
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    /*主演*/
    private String mainPerformer;
    public String getMainPerformer() {
        return mainPerformer;
    }
    public void setMainPerformer(String mainPerformer) {
        this.mainPerformer = mainPerformer;
    }

    /*时长*/
    private String duration;
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /*地区*/
    private int areaObj;
    public int getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(int areaObj) {
        this.areaObj = areaObj;
    }

    /*放映时间*/
    private String playTime;
    public String getPlayTime() {
        return playTime;
    }
    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    /*票价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*剧情简介*/
    private String opera;
    public String getOpera() {
        return opera;
    }
    public void setOpera(String opera) {
        this.opera = opera;
    }

    /*是否推荐*/
    private String recommendFlag;
    public String getRecommendFlag() {
        return recommendFlag;
    }
    public void setRecommendFlag(String recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    /*点击率*/
    private int hitNum;
    public int getHitNum() {
        return hitNum;
    }
    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

}