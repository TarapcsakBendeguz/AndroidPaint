package com.example.drawinghw.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.drawinghw.model.Circle;
import com.example.drawinghw.model.DrawObject;
import com.example.drawinghw.model.Image;
import com.example.drawinghw.model.Line;
import com.example.drawinghw.model.Point;
import com.example.drawinghw.model.Square;
import com.example.drawinghw.model.Text;

import static java.lang.Math.sqrt;

public class DrawingView extends View {

    public enum DrawingStyle {
        LINE,
        POINT,
        CIRCLE,
        SQUARE,
        IMAGE,
        TEXT
    }

    private Paint paint;
    private Paint bufferPaint;
    private Bitmap tempImg;

    private Point APoint;
    private Point BPoint;

    private List<DrawObject> drawObjects;
    private List<String> textData;

    private int penSize;



    public DrawingView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initLists();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        bufferPaint = new Paint(Paint.DITHER_FLAG);
    }

    private void initLists() {
        drawObjects = new ArrayList<>();
        textData = new ArrayList<>();
    }

    private DrawingStyle currentDrawingStyle = DrawingStyle.LINE;

    public void setPaint(int color){
        if(paint == null)
            return;

        paint.setColor(color);
        APoint = null;
        BPoint = null;
    }

    public void setDrawingStyle(final DrawingStyle drawingStyle) {
        this.currentDrawingStyle = drawingStyle;
        setTextData(null);
        setTempImg(null);
    }

    public void clearCanvas(){
        drawObjects.clear();
        APoint = null;
        BPoint = null;
        textData.clear();
        tempImg = null;
        penSize = 5;
        paint.setStrokeWidth(penSize);
        invalidate();
    }

    public void setTextData(List<String> td){
        textData.clear();
        if (td == null)
            return;
        textData.addAll(td);
    }

    public void setTempImg(Bitmap tempImg){
        this.tempImg = tempImg;
    }

    public void setPenSize(int size){
        penSize = size;
        paint.setStrokeWidth(penSize);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        BPoint = new Point(event.getX(), event.getY(), paint.getColor(), drawObjects.size(), penSize);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("onTouchEvent", "ACTION_DOWN: type="+currentDrawingStyle);
                APoint = new Point(event.getX(), event.getY(), paint.getColor(), drawObjects.size(), penSize);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Log.d("onTouchEvent", "ACTION_UP: type="+currentDrawingStyle);
                switch (currentDrawingStyle) {
                    case POINT:
                        addPointToTheList(BPoint);
                        break;
                    case LINE:
                        addLineToTheList(APoint, BPoint);
                        break;
                    case CIRCLE:
                        addCircleToTheList(APoint, BPoint);
                        break;
                    case SQUARE:
                        addSquareToTheList(APoint, BPoint);
                        break;
                    case IMAGE:
                        addImageToTheList(APoint, BPoint, tempImg);
                        break;
                    case TEXT:
                        addTextToTheList(BPoint);
                        break;
                }
                APoint = null;
                BPoint = null;
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    private void addPointToTheList(final Point APoint) {
        Log.d("addToTheList", " object=Point");
        drawObjects.add(APoint);
    }
    private void addLineToTheList(final Point APoint, final Point BPoint) {
        Log.d("addToTheList", " object=Line");
        drawObjects.add(new Line(APoint, BPoint, APoint.getOrderNum(), penSize));
    }
    private void addCircleToTheList(final Point APoint, final Point BPoint) {
        Log.d("addToTheList", " object=Circle");
        drawObjects.add(new Circle(APoint, BPoint, APoint.getOrderNum(), penSize));
    }
    private void addSquareToTheList(final Point APoint, final Point BPoint) {
        Log.d("addToTheList", " object=Square");
        drawObjects.add(new Square(APoint, BPoint, APoint.getOrderNum(), penSize));
    }
    private void addImageToTheList(final Point APoint, final Point BPoint, final Bitmap img) {
        if(tempImg != null) {
            Log.d("addToTheList", " object=Image");
            drawObjects.add(new Image(APoint, BPoint, img, APoint.getOrderNum(), penSize));
        }
        else{
            Log.w("addToTheList", " object not added, image not found");
        }
    }
    private void addTextToTheList(final Point BPoint) {
        if (textData != null && textData.size()==5) {
            Log.d("addToTheList", " object=Text");
            drawObjects.add(new Text(BPoint, BPoint.getColor(), textData.get(0), textData.get(1), Integer.parseInt(textData.get(2)), Integer.parseInt(textData.get(3)), Integer.parseInt(textData.get(4)), BPoint.getOrderNum(), penSize));
        }
        else{
            Log.w("addToTheList", " object not added, Text data not found");
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (canvas == null) {
            Log.e("onDraw", " canvas not found");
            return;
        }

        super.onDraw(canvas);

        for (final DrawObject drawObject : drawObjects) {
            if (drawObject.getObjectType() == DrawObject.ObjectTypes.POINT) {
                Point p = (Point) drawObject;
                drawPoint(canvas, p, p.getStrokeWidth());
            } else if (drawObject.getObjectType() == DrawObject.ObjectTypes.LINE) {
                Line l = (Line) drawObject;
                drawLine(canvas, l.getStart(), l.getEnd(), l.getStrokeWidth());
            } else if (drawObject.getObjectType() == DrawObject.ObjectTypes.CIRCLE) {
                Circle c = (Circle) drawObject;
                drawCircle(canvas, c.getCenter(), c.getRim(), c.getStrokeWidth());
            } else if (drawObject.getObjectType() == DrawObject.ObjectTypes.SQUARE) {
                Square s = (Square) drawObject;
                drawSquare(canvas, s.getStart(), s.getEnd(), s.getStrokeWidth());
            } else if (drawObject.getObjectType() == DrawObject.ObjectTypes.IMAGE) {
                Image i = (Image) drawObject;
                drawPicture(canvas, i.getaPoint(), i.getbPoint(), i.getPicture(), i.getStrokeWidth());
            } else if (drawObject.getObjectType() == DrawObject.ObjectTypes.TEXT) {
                Text t = (Text) drawObject;
                drawText(canvas, t.getPoint(), t.getText(), t.getText_type(), t.getText_size(), t.getB(), t.getI(), t.getStrokeWidth());
            }
        }

        if (APoint == null || BPoint == null)
            return;
        Log.d("onDraw", " type="+currentDrawingStyle);
        switch (currentDrawingStyle) {
            case POINT:
                drawPoint(canvas, BPoint, penSize);
                break;
            case LINE:
                drawLine(canvas, APoint, BPoint, penSize);
                break;
            case CIRCLE:
                drawCircle(canvas, APoint, BPoint, penSize);
                break;
            case SQUARE:
                drawSquare(canvas, APoint, BPoint, penSize);
                break;
            case IMAGE:
                drawPicture(canvas, APoint, BPoint, tempImg, penSize);
                break;
            case TEXT:
                if (textData != null && textData.size()==5) {
                    drawText(canvas, BPoint, textData.get(0), textData.get(1), Integer.parseInt(textData.get(2)), Integer.parseInt(textData.get(3)), Integer.parseInt(textData.get(4)), penSize);
                }
                break;
        }
    }

    private void drawPoint(final Canvas canvas, final Point point, final int width) {
        Log.d("drawPoint", "");
        if (point == null) {
            return;
        }
        int oldcolor = paint.getColor();
        paint.setStrokeWidth(width);
        paint.setColor(point.getColor());
        canvas.drawPoint(point.getX(), point.getY(), paint);
        paint.setStrokeWidth(penSize);
        paint.setColor(oldcolor);
    }
    private void drawLine(final Canvas canvas, final Point APoint, final Point BPoint, final int width) {
        Log.d("drawLine", "");
        if (APoint == null || BPoint == null) {
            return;
        }
        int oldcolor = paint.getColor();
        paint.setStrokeWidth(width);
        paint.setColor(APoint.getColor());
        canvas.drawLine(APoint.getX(), APoint.getY(), BPoint.getX(), BPoint.getY(), paint);
        paint.setStrokeWidth(penSize);
        paint.setColor(oldcolor);
    }
    private void drawCircle(final Canvas canvas, final Point APoint, final Point BPoint, final int width) {
        Log.d("drawCircle", "");
        if (APoint == null || BPoint == null) {
            return;
        }
        int oldcolor = paint.getColor();
        paint.setStrokeWidth(width);
        paint.setColor(APoint.getColor());
        canvas.drawCircle(APoint.getX(), APoint.getY(), calcRadius(APoint, BPoint), paint);
        paint.setStrokeWidth(penSize);
        paint.setColor(oldcolor);
    }
    private void drawSquare(final Canvas canvas, final Point APoint, final Point BPoint, final int width) {
        Log.d("drawSquare", "");
        if (APoint == null || BPoint == null) {
            return;
        }
        int oldcolor = paint.getColor();
        paint.setStrokeWidth(width);
        paint.setColor(APoint.getColor());
        float[] cornerFloats = {
        APoint.getX(), APoint.getY(),
        APoint.getX(), BPoint.getY(),
        APoint.getX(), BPoint.getY(),
        BPoint.getX(), BPoint.getY(),
        BPoint.getX(), BPoint.getY(),
        BPoint.getX(), APoint.getY(),
        BPoint.getX(), APoint.getY(),
        APoint.getX(), APoint.getY()};
        canvas.drawLines(cornerFloats, paint);
        paint.setStrokeWidth(penSize);
        paint.setColor(oldcolor);
    }
    private float calcRadius(Point A, Point B){
        float R = 0;
        float r2 = ((B.getX() - A.getX())* (B.getX() - A.getX())) + ((B.getY() - A.getY()) * (B.getY() - A.getY()));
        double r = sqrt(r2);
        R = Float.parseFloat(Double.toString(r));
        return R;
    }
    private void drawPicture(final Canvas canvas, final Point APoint, final Point BPoint, final Bitmap bitmap, final int stwidth) {
        if (APoint == null || BPoint == null || bitmap == null) {
            return;
        }
        Log.d("drawPicture", "");
        float f_height = APoint.getY() > BPoint.getY() ? APoint.getY() - BPoint.getY() : BPoint.getY() - APoint.getY();
        float f_width = APoint.getX() > BPoint.getX() ? APoint.getX() - BPoint.getX() : BPoint.getX() - APoint.getX();
        float f_left = APoint.getX() > BPoint.getX() ? BPoint.getX() : APoint.getX();
        float f_top = APoint.getY() > BPoint.getY() ? BPoint.getY() : APoint.getY();
        int left = Math.round(f_left);
        int top = Math.round(f_top);
        int height = Math.round(f_height);
        int width = Math.round(f_width);

        if (width < 10 || height < 10)
            return;

        //bitmap.setHeight(height);
        //bitmap.setWidth(width);

        //stwidth   majd ha lesz kerete a képnek akkor kell ez

        Bitmap scaled = Bitmap.createScaledBitmap(bitmap,width,height,false);
        canvas.drawBitmap(scaled, left, top, paint);
    }
    private void drawText(final Canvas canvas, final Point point, String text, String text_t, int text_s, int b, int i, final int width) {
        Log.d("drawText", "");
        if (point == null || text == null || text_t == null || text_s < 0 || b < 0 || i < 0 || text.isEmpty() || text_t.isEmpty()) {
            return;
        }
        Paint oldpaint = paint;
        Paint.Style ps = paint.getStyle();
        paint.setColor(point.getColor());
        paint.setTextSize(text_s*3);
        switch (text_t){
            case "DEFAULT":
                paint.setTypeface(Typeface.DEFAULT);
                break;
            case "SANS_SERIF":
                paint.setTypeface(Typeface.SANS_SERIF);
                break;
            case "SERIF":
                paint.setTypeface(Typeface.SERIF);
                break;
            case "MONOSPACE":
                paint.setTypeface(Typeface.MONOSPACE);
                break;
        }
        if (b == 1 && i == 1){
            paint.setTypeface(Typeface.create(paint.getTypeface(),Typeface.BOLD_ITALIC));
        }
        else if(b == 1){
            paint.setTypeface(Typeface.create(paint.getTypeface(),Typeface.BOLD));
        }
        else if(i == 1){
            paint.setTypeface(Typeface.create(paint.getTypeface(),Typeface.ITALIC));
        }
        paint.setStyle(Paint.Style.FILL);
        //paint.setStrokeWidth(width);      majd ha lesz kerete a szövegnek akkor kellhet ez
        canvas.drawText(text, point.getX(), point.getY(), paint);
        paint = oldpaint;
        paint.setStyle(ps);
    }


    public void restoreObjects(final List<DrawObject> drawObjects) {
        this.drawObjects = drawObjects;
        Collections.sort(this.drawObjects);
        invalidate();
    }

    public List<Point> getPoints() {
        List<Point> points = new ArrayList();
        for (DrawObject drawObject : drawObjects){
            if(drawObject.getObjectType() == DrawObject.ObjectTypes.POINT)
                points.add((Point)drawObject);
        }
        return points;
    }
    public List<Line> getLines() {
        List<Line> lines = new ArrayList();
        for (DrawObject drawObject : drawObjects){
            if(drawObject.getObjectType() == DrawObject.ObjectTypes.LINE)
                lines.add((Line)drawObject);
        }
        return lines;
    }
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList();
        for (DrawObject drawObject : drawObjects){
            if(drawObject.getObjectType() == DrawObject.ObjectTypes.CIRCLE)
                circles.add((Circle)drawObject);
        }
        return circles;
    }
    public List<Square> getSquares() {
        List<Square> squares = new ArrayList();
        for (DrawObject drawObject : drawObjects){
            if(drawObject.getObjectType() == DrawObject.ObjectTypes.SQUARE)
                squares.add((Square)drawObject);
        }
        return squares;
    }
    public List<Image> getImages() {
        List<Image> images = new ArrayList();
        for (DrawObject drawObject : drawObjects){
            if(drawObject.getObjectType() == DrawObject.ObjectTypes.IMAGE)
                images.add((Image)drawObject);
        }
        return images;
    }
    public List<Text> getText() {
        List<Text> texts = new ArrayList();
        for (DrawObject drawObject : drawObjects){
            if(drawObject.getObjectType() == DrawObject.ObjectTypes.TEXT)
                texts.add((Text) drawObject);
        }
        return texts;
    }
}
