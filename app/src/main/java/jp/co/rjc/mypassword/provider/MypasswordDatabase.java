package jp.co.rjc.mypassword.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB接続のヘルパクラス.
 */
final public class MypasswordDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "mypassword_android.db";
    private static final int DB_VERSION = 1;

    /**
     * テーブル名定義.
     */
    public interface Tables {

        /**
         * ログイン情報のテーブル名です.
         */
        String LOGIN_INFO = "login_info";

        /**
         * サイト情報のテーブル名です.
         */
        String SITE_INFO = "site_info";
    }

    /**
     * 標準列名定義.
     */
    public interface BaseColumns {

        /**
         * 標準のユニークID.
         */
        String _ID = "_id";
    }

    /**
     * ログイン情報の列定義
     */
    public interface LoginColumns {

        /**
         * PINコードの列名です.
         */
        String PIN = "pin";
    }

    /**
     * サイト情報の列定義
     */
    public interface SiteColumns {

        /**
         * アカウントIDの列名.
         */
        String ACCOUNT_ID = "acount_id";

        /**
         * サイト名の列名.
         */
        String SITE_NAME = "site_name";

        /**
         * ログインIDの列名.
         */
        String LOGIN_ID = "login_id";

        /**
         * ログインパスワードの列名.
         */
        String LOGIN_PASSWORD = "login_password";

        /**
         * サイトURLの列名.
         */
        String SITE_URL = "site_url";

        /**
         * 備考の列名.
         */
        String NOTES = "notes";

        /**
         * サイト最終アクセス日時の列名.
         */
        String LAST_ACCESS_DATETIME = "last_access_datetime";
    }

    public MypasswordDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createLoginInfo(sqLiteDatabase);
        createSiteInfo(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    /**
     * ログイン情報テーブルを作成します.
     *
     * @param db
     */
    private void createLoginInfo(final SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + Tables.LOGIN_INFO
                        + "("
                        + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + LoginColumns.PIN + " TEXT NOT NULL UNIQUE"
                        + ");"
        );
    }

    /**
     * サイト情報テーブルを作成します.
     *
     * @param db
     */
    private void createSiteInfo(final SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + Tables.SITE_INFO
                        + " ("
                        + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                        + SiteColumns.ACCOUNT_ID + " INTEGER, "
                        + SiteColumns.SITE_NAME + " TEXT, "
                        + SiteColumns.LOGIN_ID + " TEXT, "
                        + SiteColumns.LOGIN_PASSWORD + " TEXT, "
                        + SiteColumns.SITE_URL + " TEXT, "
                        + SiteColumns.NOTES + " TEXT, "
                        + SiteColumns.LAST_ACCESS_DATETIME + " INTEGER"
                        + ");"
        );
    }
}