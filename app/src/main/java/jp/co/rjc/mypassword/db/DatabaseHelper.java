package jp.co.rjc.mypassword.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Timestamp;

/**
 * Created by m_doi on 2016/06/08.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // FIXME DB_NAME
    private static final String DB_NAME = "fixmeDB_NAME.db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_1 = "create table login_info (id integer primary key not null, pin text);";
    private static final String CREATE_TABLE_2 = "create table site_info (id integer primary key autoincrement, site_name text, account_id text, account_password text, site_url text, notes text, last_access_datetime integer);";

    public DatabaseHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_1);
        sqLiteDatabase.execSQL(CREATE_TABLE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    // 新規登録
    public static int insert(SQLiteDatabase sqLiteDatabase,
                             String siteName,
                             String accountId,
                             String accountPassword,
                             String siteUrl,
                             String notes) {
        ContentValues values = new ContentValues();
        values.put("site_name", siteName);
        values.put("account_id", accountId);
        values.put("account_password", accountPassword);
        values.put("site_url", siteUrl);
        values.put("notes", notes);
        values.put("last_access_datetime", new Timestamp(System.currentTimeMillis()).getTime());

        long ret;
        try {
            ret = sqLiteDatabase.insert("site_info", null, values);
        } finally {
            sqLiteDatabase.close();
        }
        return (int)ret;
    }

    // 更新
    public static int update(SQLiteDatabase sqLiteDatabase,
                             int id,
                             String siteName,
                             String accountId,
                             String accountPassword,
                             String siteUrl,
                             String notes) {
        ContentValues values = new ContentValues();
        values.put("site_name", siteName);
        values.put("account_id", accountId);
        values.put("account_password", accountPassword);
        values.put("site_url", siteUrl);
        values.put("notes", notes);
        values.put("last_access_datetime", new Timestamp(System.currentTimeMillis()).getTime());

        String whereClause = "id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);

        int ret;
        try {
            ret = sqLiteDatabase.update("site_info", values, whereClause, whereArgs);
        } finally {
            sqLiteDatabase.close();
        }
        return ret;
    }

    // 削除
    public static int delete(SQLiteDatabase sqLiteDatabase, int id) {
        String whereClause = "id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);

        int ret;
        try {
            ret = sqLiteDatabase.delete("site_info", whereClause, whereArgs);
        } finally {
            sqLiteDatabase.close();
        }
        return ret;
    }

    // 検索（１件） TODO 戻り値としてCursorを返すのはどうなのか…
    public static Cursor selectOne(SQLiteDatabase sqLiteDatabase, int id){
        String[] cols = {"id","site_name","account_id","account_password","site_url","notes","last_access_datetime"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        try{
            return sqLiteDatabase.query("site_info", cols, selection, selectionArgs, groupBy, having, orderBy);
        }finally{
            sqLiteDatabase.close();
        }
    }

    // 検索
    public static Cursor select(SQLiteDatabase sqLiteDatabase, int id){
        String[] cols = {"id","site_name","account_id","account_password","site_url","notes","last_access_datetime"};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        try{
            return sqLiteDatabase.query("site_info", cols, selection, selectionArgs, groupBy, having, orderBy);
        }finally{
            sqLiteDatabase.close();
        }
    }
}