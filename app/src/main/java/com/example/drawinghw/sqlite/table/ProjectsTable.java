package com.example.drawinghw.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ProjectsTable {
    public static final String TABLE_PROJECTS = "projects";

    private static final String DATABASE_CREATE = "create table " + TABLE_PROJECTS + "("
            + Columns.rowid.name() + " integer primary key autoincrement, "
            + Columns.project.name() + " VARCHAR(50) not null " + ");";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(ProjectsTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(database);
    }

    public enum Columns {
        rowid,
        project
    }
}
