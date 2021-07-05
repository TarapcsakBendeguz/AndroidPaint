package aut.bme.hu.drawinghw.model;

public class Point extends DrawObject {
    private float x;

    private float y;

    private int color;

    public Point() {
        super();
    }

    public Point(final float x, final float y, final int col, final int order) {
        super(order, ObjectTypes.POINT);
        this.x = x;
        this.y = y;
        color = col;
    }

    public float getX() {
        return x;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(final float y) {
        this.y = y;
    }

    public int getColor(){
        return color;
    }

    public void setColor(int color1){
        color = color1;
    }
}
