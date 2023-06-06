package com.mobileclient.domain;

import java.io.Serializable;

public class OrderState implements Serializable {
    /*¶©µ¥×´Ì¬id*/
    private int orderStateId;
    public int getOrderStateId() {
        return orderStateId;
    }
    public void setOrderStateId(int orderStateId) {
        this.orderStateId = orderStateId;
    }

    /*¶©µ¥×´Ì¬Ãû³Æ*/
    private String orderStateName;
    public String getOrderStateName() {
        return orderStateName;
    }
    public void setOrderStateName(String orderStateName) {
        this.orderStateName = orderStateName;
    }

}