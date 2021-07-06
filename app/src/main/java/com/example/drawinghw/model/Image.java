package com.example.drawinghw.model;

import android.graphics.Bitmap;
import android.service.dreams.DreamService;

import java.io.ByteArrayOutputStream;

public class Image extends DrawObject {
    private Bitmap picture;
    private byte[] pictureInBytes;

    private Point aPoint;

    private Point bPoint;

    public Image() {
        super();
    }

    public Image(final Point aPoint, final Point bPoint, final Bitmap bm, final int order, final int width) {
        super(order, ObjectTypes.IMAGE, width);
        this.aPoint = aPoint;
        this.bPoint = bPoint;
        this.picture = bm;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, bos);
        pictureInBytes = bos.toByteArray();
    }

    public Point getaPoint() {
        return aPoint;
    }

    public void setaPoint(final Point aPoint) {
        this.aPoint = aPoint;
    }

    public Point getbPoint() {
        return bPoint;
    }

    public void setbPoint(final Point bPoint) {
        this.bPoint = bPoint;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(final Bitmap picture) {
        this.picture = picture;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, bos);
        pictureInBytes = bos.toByteArray();
    }

    public byte[] getPictureInBytes() {
        return pictureInBytes;
    }

}
