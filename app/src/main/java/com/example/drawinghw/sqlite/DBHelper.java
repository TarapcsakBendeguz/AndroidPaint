package com.example.drawinghw.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.drawinghw.sqlite.table.CirclesTable;
import com.example.drawinghw.sqlite.table.ImagesTable;
import com.example.drawinghw.sqlite.table.LinesTable;
import com.example.drawinghw.sqlite.table.PointsTable;
import com.example.drawinghw.sqlite.table.ProjectsTable;
import com.example.drawinghw.sqlite.table.SquaresTable;
import com.example.drawinghw.sqlite.table.TextsTable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "simpledrawer.db";

    private static final int DATABASE_VERSION = 2;

    public DBHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        LinesTable.onCreate(sqLiteDatabase);
        PointsTable.onCreate(sqLiteDatabase);
        CirclesTable.onCreate(sqLiteDatabase);
        SquaresTable.onCreate(sqLiteDatabase);
        ImagesTable.onCreate(sqLiteDatabase);
        TextsTable.onCreate(sqLiteDatabase);
        ProjectsTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int oldVersion, final int newVersion) {
        LinesTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        PointsTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        CirclesTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        SquaresTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        ImagesTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        TextsTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        ProjectsTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public void simpleUpgrade(final SQLiteDatabase sqLiteDatabase){
        onUpgrade(sqLiteDatabase, DATABASE_VERSION, DATABASE_VERSION+1);
    }

    public void resetTables(final SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE " + CirclesTable.TABLE_CIRCLES);
        sqLiteDatabase.execSQL("DROP TABLE " + ImagesTable.TABLE_IMAGES);
        sqLiteDatabase.execSQL("DROP TABLE " + LinesTable.TABLE_LINES);
        sqLiteDatabase.execSQL("DROP TABLE " + PointsTable.TABLE_POINTS);
        sqLiteDatabase.execSQL("DROP TABLE " + SquaresTable.TABLE_SQUARES);
        sqLiteDatabase.execSQL("DROP TABLE " + TextsTable.TABLE_TEXTS);
        sqLiteDatabase.execSQL("DROP TABLE " + ProjectsTable.TABLE_PROJECTS);

        LinesTable.onCreate(sqLiteDatabase);
        PointsTable.onCreate(sqLiteDatabase);
        CirclesTable.onCreate(sqLiteDatabase);
        SquaresTable.onCreate(sqLiteDatabase);
        ImagesTable.onCreate(sqLiteDatabase);
        TextsTable.onCreate(sqLiteDatabase);
        ProjectsTable.onCreate(sqLiteDatabase);
    }
}
