package aut.bme.hu.drawinghw.model;

public class Line extends DrawObject {
    private Point start;

    private Point end;

    public Line() {
        super();
    }

    public Line(final Point start, final Point end, final int order) {
        super(order, ObjectTypes.LINE);
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(final Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(final Point end) {
        this.end = end;
    }

    public int getColor(){
        return start.getColor();
    }

    public void setColor(int col){
        start.setColor(col);
        end.setColor(col);
    }
}
