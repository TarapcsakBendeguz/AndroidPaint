package aut.bme.hu.drawinghw.sqlite.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ImagesTable {
    public static final String TABLE_IMAGES = "images";

    private static final String DATABASE_CREATE = "create table " + TABLE_IMAGES + "("
            + Columns.rowid.name() + " integer primary key autoincrement, "
            + Columns.aCorner_x.name() + " real not null, "
            + Columns.aCorner_y.name() + " real not null, "
            + Columns.bCorner_x.name() + " real not null, "
            + Columns.bCorner_y.name() + " real not null, "
            + Columns.pictureInBytes.name() + " real not null, "
            + Columns.ordernum.name() + " real not null, "
            + Columns.strokeWidth.name() + " real not null, "
            + Columns.project.name() + " VARCHAR(50), "
            + Columns.type.name() + " VARCHAR(20) not null " + ");";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(ImagesTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(database);
    }

    public enum Columns {
        rowid,
        aCorner_x,
        aCorner_y,
        bCorner_x,
        bCorner_y,
        pictureInBytes,
        ordernum,
        strokeWidth,
        project,
        type
    }
}
