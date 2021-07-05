package aut.bme.hu.drawinghw.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TextsTable {
    public static final String TABLE_TEXTS = "texts";

    private static final String DATABASE_CREATE = "create table " + TABLE_TEXTS + "("
            + Columns.rowid.name() + " integer primary key autoincrement, "
            + Columns.coord_x.name() + " real not null, "
            + Columns.coord_y.name() + " real not null, "
            + Columns.color.name() + " real not null, "
            + Columns.text.name() + " VARCHAR(50) not null, "
            + Columns.text_type.name() + " VARCHAR(20) not null, "
            + Columns.text_size.name() + " real not null, "
            + Columns.B.name() + " real not null, "
            + Columns.I.name() + " real not null, "
            + Columns.ordernum.name() + " real not null, "
            + Columns.project.name() + " VARCHAR(50), "
            + Columns.type.name() + " VARCHAR(20) not null " + ");";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(TextsTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TEXTS);
        onCreate(database);
    }

    public enum Columns {
        rowid,
        coord_x,
        coord_y,
        color,
        text,
        text_type,
        text_size,
        B,
        I,
        ordernum,
        project,
        type
    }
}
