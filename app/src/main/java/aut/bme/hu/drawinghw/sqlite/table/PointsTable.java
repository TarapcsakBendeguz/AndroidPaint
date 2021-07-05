package aut.bme.hu.drawinghw.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PointsTable {
    public static final String TABLE_POINTS = "pontok";

    private static final String DATABASE_CREATE = "create table " + TABLE_POINTS + "("
            + Columns.rowid.name() + " integer primary key autoincrement, "
            + Columns.coord_x.name() + " real not null, "
            + Columns.coord_y.name() + " real not null, "
            + Columns.color.name() + " real not null, "
            + Columns.ordernum.name() + " real not null, "
            + Columns.project.name() + " VARCHAR(50), "
            + Columns.type.name() + " VARCHAR(20) not null " + ");";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(PointsTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_POINTS);
        onCreate(database);
    }

    public enum Columns {
        rowid,
        coord_x,
        coord_y,
        color,
        ordernum,
        project,
        type
    }
}
