package jp.co.rjc.mypassword.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.Timestamp;

import jp.co.rjc.mypassword.provider.MypasswordDatabase;
import jp.co.rjc.mypassword.util.ConvertUtils;

/**
 * ログイン情報のデータアクセスオブジェクトを提供します.
 */
public final class SiteDao implements MypasswordDatabase.Tables, MypasswordDatabase.SiteColumns, MypasswordDatabase.BaseColumns {

    private Context mContext;

    public SiteDao(final Context context) {
        mContext = context;
    }

    /**
     * サイト情報を登録します.
     *
     * @param accountId
     * @param siteName
     * @param loginId
     * @param loginPassword
     * @param siteUrl
     * @param notes
     * @return
     */
    public int insert(final int accountId,
                      final String siteName,
                      final String loginId,
                      final String loginPassword,
                      final String siteUrl,
                      final String notes) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        ContentValues values = new ContentValues();
        values.put(ACCOUNT_ID, accountId);
        values.put(SITE_NAME, siteName);
        values.put(LOGIN_ID, loginId);
        values.put(LOGIN_PASSWORD, ConvertUtils.encrypt(mContext, loginPassword));
        values.put(SITE_URL, siteUrl);
        values.put(NOTES, notes);
        values.put(LAST_ACCESS_DATETIME, new Timestamp(System.currentTimeMillis()).getTime());
        try {
            return (int) db.getWritableDatabase().insert(SITE_INFO, null, values);
        } finally {
            db.close();
        }
    }

    /**
     * サイト情報を更新します.
     *
     * @param id
     * @param siteName
     * @param loginId
     * @param loginPassword
     * @param siteUrl
     * @param notes
     * @return
     */
    public int update(final int id,
                      final String siteName,
                      final String loginId,
                      final String loginPassword,
                      final String siteUrl,
                      final String notes) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        ContentValues values = new ContentValues();
        values.put(SITE_NAME, siteName);
        values.put(LOGIN_ID, loginId);
        values.put(LOGIN_PASSWORD, ConvertUtils.encrypt(mContext, loginPassword));
        values.put(SITE_URL, siteUrl);
        values.put(NOTES, notes);
        values.put(LAST_ACCESS_DATETIME, new Timestamp(System.currentTimeMillis()).getTime());

        String whereClause = _ID + " = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);
        try {
            return db.getWritableDatabase().update(SITE_INFO, values, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }

    /**
     * 最終アクセス日時を更新します.
     */
    public int updateLastAccessDatetime(final int id) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        ContentValues values = new ContentValues();
        values.put(LAST_ACCESS_DATETIME, new Timestamp(System.currentTimeMillis()).getTime());

        String whereClause = _ID + " = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);
        try {
            return db.getWritableDatabase().update(SITE_INFO, values, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }

    /**
     * サイト情報を削除します.
     *
     * @param id
     * @return
     */
    public int delete(final int id) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        String whereClause = _ID + " = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);
        try {
            return db.getWritableDatabase().delete(SITE_INFO, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }

    /**
     * アカウントIDに紐づく全てのサイト情報を返却します.
     *
     * @param accountId
     * @return
     */
    public Cursor select(final int accountId) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        String[] cols = {_ID, SITE_NAME};
        String selection = ACCOUNT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(accountId)};
        String groupBy = null;
        String having = null;
        String orderBy = " " + LAST_ACCESS_DATETIME + " ";
        return db.getReadableDatabase().query(SITE_INFO, cols, selection, selectionArgs, groupBy, having, orderBy);
    }

    /**
     * IDに紐づくサイト情報を返却します.
     *
     * @param id
     * @return
     */
    public Cursor selectOne(final int id) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        String[] cols = {SITE_NAME, LOGIN_ID, LOGIN_PASSWORD, SITE_URL, NOTES, LAST_ACCESS_DATETIME};
        String selection = _ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        return db.getReadableDatabase().query(SITE_INFO, cols, selection, selectionArgs, groupBy, having, orderBy);
    }
}