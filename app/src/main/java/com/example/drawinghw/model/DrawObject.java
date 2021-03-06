package com.example.drawinghw.model;

public class DrawObject implements Comparable<DrawObject> {
    protected int orderNum;

    protected int strokeWidth;

    public enum ObjectTypes {
        LINE,
        POINT,
        CIRCLE,
        SQUARE,
        IMAGE,
        TEXT
    }

    protected ObjectTypes objectType;

    public DrawObject() {
    }

    public DrawObject(final int orderNum, final ObjectTypes objectType, final int width) {
        this.orderNum = orderNum;
        this.objectType = objectType;
        this.strokeWidth = width;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(final int orderNum) {
        this.orderNum = orderNum;
    }

    public ObjectTypes getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        switch (objectType){
            case "POINT":
                this.objectType = ObjectTypes.POINT;
                break;
            case "LINE":
                this.objectType = ObjectTypes.LINE;
                break;
            case "CIRCLE":
                this.objectType = ObjectTypes.CIRCLE;
                break;
            case "SQUARE":
                this.objectType = ObjectTypes.SQUARE;
                break;
            case "IMAGE":
                this.objectType = ObjectTypes.IMAGE;
                break;
            case "TEXT":
                this.objectType = ObjectTypes.TEXT;
                break;
        }
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    public int compareTo(DrawObject object2) {
        return this.orderNum - object2.orderNum;
    }
}
