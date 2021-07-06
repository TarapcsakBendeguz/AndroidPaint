package com.example.drawinghw.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LinesTable {
    public static final String TABLE_LINES = "lines";

    private static final String DATABASE_CREATE = "create table " + TABLE_LINES + "("
            + Columns.rowid.name() + " integer primary key autoincrement, "
            + Columns.start_x.name() + " real not null, "
            + Columns.start_y.name() + " real not null, "
            + Columns.end_x.name() + " real not null, "
            + Columns.end_y.name() + " real not null, "
            + Columns.color.name() + " real not null, "
            + Columns.ordernum.name() + " real not null, "
            + Columns.strokeWidth.name() + " real not null, "
            + Columns.project.name() + " VARCHAR(50), "
            + Columns.type.name() + " VARCHAR(20) not null " + ");";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(LinesTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LINES);
        onCreate(database);
    }

    public enum Columns {
        rowid,
        start_x,
        start_y,
        end_x,
        end_y,
        color,
        ordernum,
        strokeWidth,
        project,
        type
    }
}
