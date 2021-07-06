package aut.bme.hu.drawinghw.model;

public class Circle extends DrawObject {
    private Point center;

    private Point rim;

    public Circle() {
        super();
    }

    public Circle(final Point center, final Point rim, final int order, final int width) {
        super(order, ObjectTypes.CIRCLE, width);
        this.center = center;
        this.rim = rim;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(final Point center) {
        this.center = center;
    }

    public Point getRim() {
        return rim;
    }

    public void setRim(final Point rim) {
        this.rim = rim;
    }

    public int getColor(){
        return center.getColor();
    }

    public void setColor(int col){
        center.setColor(col);
        rim.setColor(col);
    }
}
