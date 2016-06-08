package jp.co.rjc.mypassword.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by m_doi on 2016/06/08.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // FIXME DB_NAME
    private static final String DB_NAME = "fixmeDB_NAME.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TODO onCreate
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO onUpgrade
    }
}
