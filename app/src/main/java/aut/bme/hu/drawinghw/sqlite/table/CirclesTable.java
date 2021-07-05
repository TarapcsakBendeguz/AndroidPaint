package aut.bme.hu.drawinghw.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CirclesTable {
    public static final String TABLE_CIRCLES = "circles";

    private static final String DATABASE_CREATE = "create table " + TABLE_CIRCLES + "("
            + Columns.rowid.name() + " integer primary key autoincrement, "
            + Columns.center_x.name() + " real not null, "
            + Columns.center_y.name() + " real not null, "
            + Columns.rim_x.name() + " real not null, "
            + Columns.rim_y.name() + " real not null, "
            + Columns.color.name() + " real not null, "
            + Columns.ordernum.name() + " real not null, "
            + Columns.project.name() + " VARCHAR(50), "
            + Columns.type.name() + " VARCHAR(20) not null" + ");";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(LinesTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CIRCLES);
        onCreate(database);
    }

    public enum Columns {
        rowid,
        center_x,
        center_y,
        rim_x,
        rim_y,
        color,
        ordernum,
        project,
        type
    }
}
