package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderInfo {
    /*订单编号*/
    private String orderNo;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /*下单电影*/
    private Movie movieObj;
    public Movie getMovieObj() {
        return movieObj;
    }
    public void setMovieObj(Movie movieObj) {
        this.movieObj = movieObj;
    }

    /*电影价格*/
    private float moviePrice;
    public float getMoviePrice() {
        return moviePrice;
    }
    public void setMoviePrice(float moviePrice) {
        this.moviePrice = moviePrice;
    }

    /*购买数量*/
    private int orderNum;
    public int getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    /*订单总价*/
    private float orderPrice;
    public float getOrderPrice() {
        return orderPrice;
    }
    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    /*下单用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*下单时间*/
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*收货人*/
    private String receiveName;
    public String getReceiveName() {
        return receiveName;
    }
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /*收货人电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*收货人地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*订单状态*/
    private OrderState orderStateObj;
    public OrderState getOrderStateObj() {
        return orderStateObj;
    }
    public void setOrderStateObj(OrderState orderStateObj) {
        this.orderStateObj = orderStateObj;
    }

    /*订单备注*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

}