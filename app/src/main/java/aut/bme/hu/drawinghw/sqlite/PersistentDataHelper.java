package aut.bme.hu.drawinghw.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aut.bme.hu.drawinghw.model.Circle;
import aut.bme.hu.drawinghw.model.Image;
import aut.bme.hu.drawinghw.model.Line;
import aut.bme.hu.drawinghw.model.Point;
import aut.bme.hu.drawinghw.model.Square;
import aut.bme.hu.drawinghw.model.Text;
import aut.bme.hu.drawinghw.sqlite.table.CirclesTable;
import aut.bme.hu.drawinghw.sqlite.table.ImagesTable;
import aut.bme.hu.drawinghw.sqlite.table.LinesTable;
import aut.bme.hu.drawinghw.sqlite.table.PointsTable;
import aut.bme.hu.drawinghw.sqlite.table.ProjectsTable;
import aut.bme.hu.drawinghw.sqlite.table.SquaresTable;
import aut.bme.hu.drawinghw.sqlite.table.TextsTable;

public class PersistentDataHelper {
    private SQLiteDatabase database;

    private DBHelper dbHelper;

    private String[] pointColumns = {
            PointsTable.Columns.rowid.name(),
            PointsTable.Columns.coord_x.name(),
            PointsTable.Columns.coord_y.name(),
            PointsTable.Columns.color.name(),
            PointsTable.Columns.ordernum.name(),
            PointsTable.Columns.strokeWidth.name(),
            PointsTable.Columns.project.name(),
            PointsTable.Columns.type.name()

    };

    private String[] lineColumns = {
            LinesTable.Columns.rowid.name(),
            LinesTable.Columns.start_x.name(),
            LinesTable.Columns.start_y.name(),
            LinesTable.Columns.end_x.name(),
            LinesTable.Columns.end_y.name(),
            LinesTable.Columns.color.name(),
            LinesTable.Columns.ordernum.name(),
            LinesTable.Columns.strokeWidth.name(),
            LinesTable.Columns.project.name(),
            LinesTable.Columns.type.name()
    };

    private String[] circleColumns = {
            CirclesTable.Columns.rowid.name(),
            CirclesTable.Columns.center_x.name(),
            CirclesTable.Columns.center_y.name(),
            CirclesTable.Columns.rim_x.name(),
            CirclesTable.Columns.rim_y.name(),
            CirclesTable.Columns.color.name(),
            CirclesTable.Columns.ordernum.name(),
            CirclesTable.Columns.strokeWidth.name(),
            CirclesTable.Columns.project.name(),
            CirclesTable.Columns.type.name()
    };

    private String[] squareColumns = {
            SquaresTable.Columns.rowid.name(),
            SquaresTable.Columns.start_x.name(),
            SquaresTable.Columns.start_y.name(),
            SquaresTable.Columns.end_x.name(),
            SquaresTable.Columns.end_y.name(),
            SquaresTable.Columns.color.name(),
            SquaresTable.Columns.ordernum.name(),
            SquaresTable.Columns.strokeWidth.name(),
            SquaresTable.Columns.project.name(),
            SquaresTable.Columns.type.name()
    };
    private String[] imageColumns = {
            ImagesTable.Columns.rowid.name(),
            ImagesTable.Columns.aCorner_x.name(),
            ImagesTable.Columns.aCorner_y.name(),
            ImagesTable.Columns.bCorner_x.name(),
            ImagesTable.Columns.bCorner_y.name(),
            ImagesTable.Columns.pictureInBytes.name(),
            ImagesTable.Columns.ordernum.name(),
            ImagesTable.Columns.strokeWidth.name(),
            ImagesTable.Columns.project.name(),
            ImagesTable.Columns.type.name()
    };

    private String[] textColumns = {
            TextsTable.Columns.rowid.name(),
            TextsTable.Columns.coord_x.name(),
            TextsTable.Columns.coord_y.name(),
            TextsTable.Columns.color.name(),
            TextsTable.Columns.text.name(),
            TextsTable.Columns.text_type.name(),
            TextsTable.Columns.text_size.name(),
            TextsTable.Columns.B.name(),
            TextsTable.Columns.I.name(),
            TextsTable.Columns.ordernum.name(),
            TextsTable.Columns.strokeWidth.name(),
            TextsTable.Columns.project.name(),
            TextsTable.Columns.type.name()
    };

    private String[] projectColumns = {
            ProjectsTable.Columns.rowid.name(),
            ProjectsTable.Columns.project.name()
    };

    public PersistentDataHelper(final Context context) {
        dbHelper = new DBHelper(context);
    }

    public DBHelper getDbHelper(){
        return dbHelper;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void persistPoints(final List<Point> points, String proj) {
        Log.d("persistPoints", ""+points.size());
        clearPoints(proj);
        for (final Point point : points) {
            final ContentValues values = new ContentValues();
            values.put(PointsTable.Columns.coord_x.name(), point.getX());
            values.put(PointsTable.Columns.coord_y.name(), point.getY());
            values.put(PointsTable.Columns.color.name(), point.getColor());
            values.put(PointsTable.Columns.ordernum.name(), point.getOrderNum());
            values.put(PointsTable.Columns.strokeWidth.name(), point.getStrokeWidth());
            values.put(PointsTable.Columns.type.name(), point.getObjectType().toString());
            values.put(PointsTable.Columns.project.name(), proj);
            database.insertOrThrow(PointsTable.TABLE_POINTS, null, values);
        }
    }
    public void persistLines(final List<Line> lines, String proj) {
        Log.d("persistLines", ""+lines.size());
        clearLines(proj);
        for (final Line line : lines) {
            final ContentValues values = new ContentValues();
            values.put(LinesTable.Columns.start_x.name(), line.getStart().getX());
            values.put(LinesTable.Columns.start_y.name(), line.getStart().getY());
            values.put(LinesTable.Columns.end_x.name(), line.getEnd().getX());
            values.put(LinesTable.Columns.end_y.name(), line.getEnd().getY());
            values.put(LinesTable.Columns.color.name(), line.getColor());
            values.put(LinesTable.Columns.ordernum.name(), line.getOrderNum());
            values.put(LinesTable.Columns.strokeWidth.name(), line.getStrokeWidth());
            values.put(LinesTable.Columns.project.name(), proj);
            values.put(LinesTable.Columns.type.name(), line.getObjectType().toString());
            database.insertOrThrow(LinesTable.TABLE_LINES, null, values);
        }
    }
    public void persistCircles(final List<Circle> circles, String proj) {
        Log.d("persistCircles", ""+circles.size());
        clearCircles(proj);
        for (final Circle circle : circles) {
            final ContentValues values = new ContentValues();
            values.put(CirclesTable.Columns.center_x.name(), circle.getCenter().getX());
            values.put(CirclesTable.Columns.center_y.name(), circle.getCenter().getY());
            values.put(CirclesTable.Columns.rim_x.name(), circle.getRim().getX());
            values.put(CirclesTable.Columns.rim_y.name(), circle.getRim().getY());
            values.put(CirclesTable.Columns.color.name(), circle.getColor());
            values.put(CirclesTable.Columns.ordernum.name(), circle.getOrderNum());
            values.put(CirclesTable.Columns.strokeWidth.name(), circle.getStrokeWidth());
            values.put(CirclesTable.Columns.project.name(), proj);
            values.put(CirclesTable.Columns.type.name(), circle.getObjectType().toString());
            database.insertOrThrow(CirclesTable.TABLE_CIRCLES, null, values);
        }
    }
    public void persistSquares(final List<Square> squares, String proj) {
        Log.d("persistSquares", ""+squares.size());
        clearSquares(proj);
        for (final Square square : squares) {
            final ContentValues values = new ContentValues();
            values.put(SquaresTable.Columns.start_x.name(), square.getStart().getX());
            values.put(SquaresTable.Columns.start_y.name(), square.getStart().getY());
            values.put(SquaresTable.Columns.end_x.name(), square.getEnd().getX());
            values.put(SquaresTable.Columns.end_y.name(), square.getEnd().getY());
            values.put(SquaresTable.Columns.color.name(), square.getColor());
            values.put(SquaresTable.Columns.ordernum.name(), square.getOrderNum());
            values.put(SquaresTable.Columns.strokeWidth.name(), square.getStrokeWidth());
            values.put(SquaresTable.Columns.project.name(), proj);
            values.put(SquaresTable.Columns.type.name(), square.getObjectType().toString());
            database.insertOrThrow(SquaresTable.TABLE_SQUARES, null, values);
        }
    }
    public void persistImages(final List<Image> images, String proj) {
        Log.d("persistImages", ""+images.size());
        clearImages(proj);
        for (final Image image : images) {
            final ContentValues values = new ContentValues();
            values.put(ImagesTable.Columns.aCorner_x.name(), image.getaPoint().getX());
            values.put(ImagesTable.Columns.aCorner_y.name(), image.getaPoint().getY());
            values.put(ImagesTable.Columns.bCorner_x.name(), image.getbPoint().getX());
            values.put(ImagesTable.Columns.bCorner_y.name(), image.getbPoint().getY());
            values.put(ImagesTable.Columns.pictureInBytes.name(), image.getPictureInBytes());
            values.put(ImagesTable.Columns.ordernum.name(), image.getOrderNum());
            values.put(ImagesTable.Columns.strokeWidth.name(), image.getStrokeWidth());
            values.put(ImagesTable.Columns.project.name(), proj);
            values.put(ImagesTable.Columns.type.name(), image.getObjectType().toString());
            database.insertOrThrow(ImagesTable.TABLE_IMAGES, null, values);
        }
    }
    public void persistTexts(final List<Text> texts, String proj) {
        Log.d("persistPoints", ""+texts.size());
        clearTexts(proj);
        for (final Text text : texts) {
            final ContentValues values = new ContentValues();
            values.put(TextsTable.Columns.coord_x.name(), text.getPoint().getX());
            values.put(TextsTable.Columns.coord_y.name(), text.getPoint().getY());
            values.put(TextsTable.Columns.color.name(), text.getPoint().getColor());
            values.put(TextsTable.Columns.text.name(), text.getText());
            values.put(TextsTable.Columns.text_type.name(), text.getText_type());
            values.put(TextsTable.Columns.text_size.name(), text.getText_size());
            values.put(TextsTable.Columns.B.name(), text.getB());
            values.put(TextsTable.Columns.I.name(), text.getI());
            values.put(TextsTable.Columns.ordernum.name(), text.getOrderNum());
            values.put(TextsTable.Columns.strokeWidth.name(), text.getStrokeWidth());
            values.put(TextsTable.Columns.project.name(), proj);
            values.put(TextsTable.Columns.type.name(), text.getObjectType().toString());
            database.insertOrThrow(TextsTable.TABLE_TEXTS, null, values);
        }
    }
    public void persistProjects(final List<String> projects) {
        Log.d("persistProjects", ""+projects.size());
        clearProjects();
        for (final String project : projects) {
            final ContentValues values = new ContentValues();
            values.put(ProjectsTable.Columns.project.name(), project);
            database.insertOrThrow(ProjectsTable.TABLE_PROJECTS, null, values);
        }
    }

    public List<Point> restorePoints(String proj) {
        final List<Point> points = new ArrayList<>();
        final Cursor cursor = database.query(PointsTable.TABLE_POINTS, pointColumns, "project = '" + proj + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Point point = cursorToPoint(cursor);
            points.add(point);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("restorePoints", ""+points.size());
        return points;
    }
    public List<Line> restoreLines(String proj) {
        final List<Line> lines = new ArrayList<>();
        final Cursor cursor = database.query(LinesTable.TABLE_LINES, lineColumns, "project = '" + proj + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Line line = cursorToLine(cursor);
            lines.add(line);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("restoreLines", ""+lines.size());
        return lines;
    }
    public List<Circle> restoreCircles(String proj) {
        final List<Circle> circles = new ArrayList<>();
        final Cursor cursor = database.query(CirclesTable.TABLE_CIRCLES, circleColumns, "project = '" + proj + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Circle circle = cursorToCircle(cursor);
            circles.add(circle);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("restoreCircles", ""+circles.size());
        return circles;
    }
    public List<Square> restoreSquares(String proj) {
        final List<Square> lines = new ArrayList<>();
        final Cursor cursor = database.query(SquaresTable.TABLE_SQUARES, squareColumns, "project = '" + proj + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Square square = cursorToSquare(cursor);
            lines.add(square);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("restoreSquares", ""+lines.size());
        return lines;
    }
    public List<Image> restoreImages(String proj) {
        final List<Image> images = new ArrayList<>();
        final Cursor cursor = database.query(ImagesTable.TABLE_IMAGES, imageColumns, "project = '" + proj + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Image image = cursorToImage(cursor);
            images.add(image);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("restoreImages", ""+images.size());
        return images;
    }
    public List<Text> restoreTexts(String proj) {
        final List<Text> texts = new ArrayList<>();
        final Cursor cursor = database.query(TextsTable.TABLE_TEXTS, textColumns, "project = '" + proj + "'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Text text = cursorToText(cursor);
            texts.add(text);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("restoreTexts", ""+texts.size());
        return texts;
    }
    public List<String> restoreProjects() {
        final List<String> projects = new ArrayList<>();
        final Cursor cursor = database.query(ProjectsTable.TABLE_PROJECTS, projectColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final String project = cursorToProject(cursor);
            projects.add(project);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("restoreTexts", ""+projects.size());
        return projects;
    }

    public void clearPoints(String proj) {database.delete(PointsTable.TABLE_POINTS, "project = '" + proj + "'", null);}
    public void clearLines(String proj) {database.delete(LinesTable.TABLE_LINES, "project = '" + proj + "'", null);}
    public void clearCircles(String proj) {database.delete(CirclesTable.TABLE_CIRCLES, "project = '" + proj + "'", null);}
    public void clearSquares(String proj) {database.delete(SquaresTable.TABLE_SQUARES, "project = '" + proj + "'", null);}
    public void clearImages(String proj) { database.delete(ImagesTable.TABLE_IMAGES, "project = '" + proj + "'", null);}
    public void clearTexts(String proj) { database.delete(TextsTable.TABLE_TEXTS, "project = '" + proj + "'", null);}
    public void clearProjects() { database.delete(ProjectsTable.TABLE_PROJECTS, null, null);}

    private Point cursorToPoint(final Cursor cursor) {
        Log.d("cursorToPoint", "");
        final Point point = new Point();
        point.setX(cursor.getFloat(PointsTable.Columns.coord_x.ordinal()));
        point.setY(cursor.getFloat(PointsTable.Columns.coord_y.ordinal()));
        point.setColor((int)cursor.getFloat(PointsTable.Columns.color.ordinal()));
        point.setOrderNum((int)cursor.getFloat(PointsTable.Columns.ordernum.ordinal()));
        point.setStrokeWidth((int)cursor.getFloat(PointsTable.Columns.strokeWidth.ordinal()));
        point.setObjectType("POINT");
        return point;
    }
    private Line cursorToLine(final Cursor cursor) {
        Log.d("cursorToLine", "");
        final Line line = new Line();
        final Point startPoint = new Point();
        startPoint.setX(cursor.getFloat(LinesTable.Columns.start_x.ordinal()));
        startPoint.setY(cursor.getFloat(LinesTable.Columns.start_y.ordinal()));
        line.setStart(startPoint);
        final Point endPoint = new Point();
        endPoint.setX(cursor.getFloat(LinesTable.Columns.end_x.ordinal()));
        endPoint.setY(cursor.getFloat(LinesTable.Columns.end_y.ordinal()));
        line.setEnd(endPoint);
        line.setColor((int)cursor.getFloat(LinesTable.Columns.color.ordinal()));
        line.setOrderNum((int)cursor.getFloat(LinesTable.Columns.ordernum.ordinal()));
        line.setStrokeWidth((int)cursor.getFloat(LinesTable.Columns.strokeWidth.ordinal()));
        line.setObjectType("LINE");
        return line;
    }
    private Circle cursorToCircle(final Cursor cursor) {
        Log.d("cursorToCircle", "");
        final Circle circle = new Circle();
        final Point centerPoint = new Point();
        centerPoint.setX(cursor.getFloat(CirclesTable.Columns.center_x.ordinal()));
        centerPoint.setY(cursor.getFloat(CirclesTable.Columns.center_y.ordinal()));
        circle.setCenter(centerPoint);
        final Point rimPoint = new Point();
        rimPoint.setX(cursor.getFloat(CirclesTable.Columns.rim_x.ordinal()));
        rimPoint.setY(cursor.getFloat(CirclesTable.Columns.rim_y.ordinal()));
        circle.setRim(rimPoint);
        circle.setColor((int)cursor.getFloat(CirclesTable.Columns.color.ordinal()));
        circle.setOrderNum((int)cursor.getFloat(CirclesTable.Columns.ordernum.ordinal()));
        circle.setStrokeWidth((int)cursor.getFloat(CirclesTable.Columns.strokeWidth.ordinal()));
        circle.setObjectType("CIRCLE");
        return circle;
    }
    private Square cursorToSquare(final Cursor cursor) {
        Log.d("cursorToSquare", "");
        final Square line = new Square();
        final Point startPoint = new Point();
        startPoint.setX(cursor.getFloat(SquaresTable.Columns.start_x.ordinal()));
        startPoint.setY(cursor.getFloat(SquaresTable.Columns.start_y.ordinal()));
        line.setStart(startPoint);
        final Point endPoint = new Point();
        endPoint.setX(cursor.getFloat(SquaresTable.Columns.end_x.ordinal()));
        endPoint.setY(cursor.getFloat(SquaresTable.Columns.end_y.ordinal()));
        line.setEnd(endPoint);
        line.setColor((int)cursor.getFloat(SquaresTable.Columns.color.ordinal()));
        line.setOrderNum((int)cursor.getFloat(SquaresTable.Columns.ordernum.ordinal()));
        line.setStrokeWidth((int)cursor.getFloat(SquaresTable.Columns.strokeWidth.ordinal()));
        line.setObjectType("SQUARE");
        return line;
    }
    private Image cursorToImage(final Cursor cursor) {
        Log.d("cursorToImage", "");
        final Image image = new Image();
        final Point APoint = new Point();
        APoint.setX(cursor.getFloat(ImagesTable.Columns.aCorner_x.ordinal()));
        APoint.setY(cursor.getFloat(ImagesTable.Columns.aCorner_y.ordinal()));
        image.setaPoint(APoint);
        final Point BPoint = new Point();
        BPoint.setX(cursor.getFloat(ImagesTable.Columns.bCorner_x.ordinal()));
        BPoint.setY(cursor.getFloat(ImagesTable.Columns.bCorner_y.ordinal()));
        image.setbPoint(BPoint);

        byte[] imgByte = cursor.getBlob(cursor.getColumnIndex(ImagesTable.Columns.pictureInBytes.toString()));
        Bitmap bmp = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        Bitmap mutableBitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
        image.setPicture(mutableBitmap);
        image.setOrderNum((int)cursor.getFloat(ImagesTable.Columns.ordernum.ordinal()));
        image.setStrokeWidth((int)cursor.getFloat(ImagesTable.Columns.strokeWidth.ordinal()));
        image.setObjectType("IMAGE");
        return image;
    }
    private Text cursorToText(final Cursor cursor) {
        Log.d("cursorToText", "");
        final Text text = new Text();
        final Point point = new Point();
        point.setX(cursor.getFloat(TextsTable.Columns.coord_x.ordinal()));
        point.setY(cursor.getFloat(TextsTable.Columns.coord_y.ordinal()));
        point.setColor((int)cursor.getFloat(TextsTable.Columns.color.ordinal()));
        text.setPoint(point);
        text.setText(cursor.getString(TextsTable.Columns.text.ordinal()));
        text.setText_type(cursor.getString(TextsTable.Columns.text_type.ordinal()));
        text.setText_size((int)cursor.getFloat(TextsTable.Columns.text_size.ordinal()));
        text.setI((int)cursor.getFloat(TextsTable.Columns.I.ordinal()));
        text.setB((int)cursor.getFloat(TextsTable.Columns.B.ordinal()));
        text.setOrderNum((int)cursor.getFloat(TextsTable.Columns.ordernum.ordinal()));
        text.setStrokeWidth((int)cursor.getFloat(TextsTable.Columns.strokeWidth.ordinal()));
        text.setObjectType("TEXT");
        return text;
    }
    private String cursorToProject(final Cursor cursor) {
        Log.d("cursorToProject", "");
        String project;
        project = cursor.getString(ProjectsTable.Columns.project.ordinal());
        return project;
    }
}

