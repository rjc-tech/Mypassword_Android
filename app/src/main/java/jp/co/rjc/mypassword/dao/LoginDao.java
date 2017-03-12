package jp.co.rjc.mypassword.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import jp.co.rjc.mypassword.common.Globals;
import jp.co.rjc.mypassword.provider.MypasswordDatabase;
import jp.co.rjc.mypassword.util.ConvertUtils;

/**
 * ログイン情報のデータアクセスオブジェクトを提供します.
 */
public final class LoginDao implements MypasswordDatabase.Tables, MypasswordDatabase.LoginColumns, MypasswordDatabase.BaseColumns {

    private Context mContext;
    private ContentResolver mContentResolver;

    public LoginDao(final Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    /**
     * PINコードを登録します.
     *
     * @param pin
     * @return
     */
    public int insertPin(final String pin) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        ContentValues values = new ContentValues();
        values.put("pin", ConvertUtils.encrypt(mContext, pin));
        try {
            return (int) db.getWritableDatabase().insert(LOGIN_INFO, null, values);
        } finally {
            db.close();
        }
    }

    /**
     * PINコードを更新します.
     *
     * @param id
     * @param pin
     * @return
     */
    public int updatePin(final int id, final String pin) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        ContentValues values = new ContentValues();
        values.put("pin", ConvertUtils.encrypt(mContext, pin));

        String whereClause = "_id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);
        try {
            return db.getWritableDatabase().update(LOGIN_INFO, values, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }

    /**
     * 指定したIDのPINコードを削除します.
     *
     * @param id
     * @return
     */
    public int deletePin(final int id) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        String whereClause = "_id = ?";
        String whereArgs[] = new String[1];
        whereArgs[0] = Integer.toString(id);
        try {
            return db.getWritableDatabase().delete(LOGIN_INFO, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }

    /**
     * 入力されたPINコードに合致するIDを返却します.
     *
     * @param pin
     * @return 入力されたPINコードに合致するID
     */
    public int getExistMatchPinId(final String pin) {
        MypasswordDatabase db = new MypasswordDatabase(mContext);

        String[] cols = {_ID, PIN};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = null;
        try {
            cursor = db.getReadableDatabase().query(LOGIN_INFO, cols, selection, selectionArgs, groupBy, having, orderBy);
            while (cursor.moveToNext()) {
                if (pin.equals(ConvertUtils.decrypt(mContext, cursor.getString(cursor.getColumnIndex(PIN))))) {
                    return cursor.getInt(cursor.getColumnIndex(_ID));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return Globals.INVALID_PIN;
    }
}
