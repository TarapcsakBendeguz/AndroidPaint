package aut.bme.hu.drawinghw.model;

public class Text extends DrawObject{
    private Point point;
    private int text_color;
    private String text;
    private String text_type;
    private int text_size;
    private int B;
    private int I;


    public Text() {
        super();
    }

    public Text(final Point p, final int col, final String text, final String text_type, final int text_size, final int b, final int i, final int order, final int width) {
        super(order, ObjectTypes.TEXT, width);
        this.point = p;
        text_color = col;
        this.text = text;
        this.text_type = text_type;
        this.text_size = text_size;
        this.B = b;
        this.I = i;
    }

    public Point getPoint() {return point;}

    public void setPoint(final Point p) {
        this.point = p;
    }

    public int getText_color(){
        return text_color;
    }

    public void setText_color(int tc){text_color = tc;}

    public int getText_size(){
        return text_size;
    }

    public void setText_size(int ts){text_size = ts;}

    public String getText(){
        return text;
    }

    public void setText(String t){text = t;}

    public String getText_type(){
        return text_type;
    }

    public void setText_type(String tt){text_type = tt;}

    public int getB(){
        return B;
    }

    public void setB(int b){B = b;}

    public int getI(){
        return I;
    }

    public void setI(int i){I = i;}
}
