package jp.co.rjc.mypassword.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.sql.Timestamp;

import jp.co.rjc.mypassword.common.Globals;

/**
 * Created by m_doi on 2016/06/08.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

	// FIXME DB_NAME
	private static final String DB_NAME = "fixmeDB_NAME.db";
	private static final int DB_VERSION = 1;
	private static final String CREATE_TABLE_1 = "create table login_info (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, pin TEXT NOT NULL UNIQUE);";
	private static final String CREATE_TABLE_2 = "create table site_info (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, account_id INTEGER, site_name TEXT, login_id TEXT, login_password TEXT, site_url TEXT, notes TEXT, last_access_datetime INTER);";

	public DatabaseHelper(Context context) {
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

	// 挿入
	public static int insertPin(SQLiteDatabase sqLiteDatabase,
	                            String pin) {
		ContentValues values = new ContentValues();
		values.put("pin", pin);

		long ret;
		try {
			ret = sqLiteDatabase.insert("login_info", null, values);
		} finally {
			sqLiteDatabase.close();
		}
		return (int) ret;
	}

	// 更新
	public static int updatePin(SQLiteDatabase sqLiteDatabase,
	                            int id,
	                            String pin) {
		ContentValues values = new ContentValues();
		values.put("pin", pin);

		String whereClause = "_id = ?";
		String whereArgs[] = new String[1];
		whereArgs[0] = Integer.toString(id);

		int ret;
		try {
			ret = sqLiteDatabase.update("login_info", values, whereClause, whereArgs);
		} finally {
			sqLiteDatabase.close();
		}
		return ret;
	}

	// 削除
	public static int deletePin(SQLiteDatabase sqLiteDatabase) {
		int ret;
		try {
			ret = sqLiteDatabase.delete("login_info", null, null);
		} finally {
			sqLiteDatabase.close();
		}
		return ret;
	}

	/**
	 * 入力されたPINコードに合致するIDを返却します.
	 *
	 * @param sqLiteDatabase
	 * @param pin
	 * @return ID(あるいは入力間違えコード、あるいは未登録コード)
	 */
	public static Cursor getExistMatchPinId(final SQLiteDatabase sqLiteDatabase, final String pin) {
		String[] cols = {"_id", "pin"};
		String selection = "pin = ?";
		String[] selectionArgs = {String.valueOf(pin)};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		return sqLiteDatabase.query("login_info", cols, selection, selectionArgs, groupBy, having, orderBy);
	}

	/**
	 * @param sqLiteDatabase
	 * @param accountId
	 * @param siteName
	 * @param loginId
	 * @param loginPassword
	 * @param siteUrl
	 * @param notes
	 * @return
	 */
	public static int insert(SQLiteDatabase sqLiteDatabase,
	                         int accountId,
	                         String siteName,
	                         String loginId,
	                         String loginPassword,
	                         String siteUrl,
	                         String notes) {
		ContentValues values = new ContentValues();
		values.put("account_id", accountId);
		values.put("site_name", siteName);
		values.put("login_id", loginId);
		values.put("login_password", loginPassword);
		values.put("site_url", siteUrl);
		values.put("notes", notes);
		values.put("last_access_datetime", new Timestamp(System.currentTimeMillis()).getTime());

		long ret;
		try {
			ret = sqLiteDatabase.insert("site_info", null, values);
		} finally {
			sqLiteDatabase.close();
		}
		return (int) ret;
	}

	// 更新
	public static int update(SQLiteDatabase sqLiteDatabase,
	                         int id,
	                         String siteName,
	                         String loginId,
	                         String loginPassword,
	                         String siteUrl,
	                         String notes) {
		ContentValues values = new ContentValues();
		values.put("site_name", siteName);
		values.put("login_id", loginId);
		values.put("login_password", loginPassword);
		values.put("site_url", siteUrl);
		values.put("notes", notes);
		values.put("last_access_datetime", new Timestamp(System.currentTimeMillis()).getTime());

		String whereClause = "_id = ?";
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
		String whereClause = "_id = ?";
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

	// 検索（全件）
	public static Cursor select(final SQLiteDatabase sqLiteDatabase, final int accountId) {
		String[] cols = {"_id", "site_name"};
		String selection = "account_id = ?";
		String[] selectionArgs = {String.valueOf(accountId)};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		return sqLiteDatabase.query("site_info", cols, selection, selectionArgs, groupBy, having, orderBy);
	}

	// 検索（１件）
	public static Cursor selectOne(final SQLiteDatabase sqLiteDatabase, final int id) {
		String[] cols = {"_id", "site_name", "login_id", "login_password", "site_url", "notes", "last_access_datetime"};
		String selection = "_id = ?";
		String[] selectionArgs = {String.valueOf(id)};
		String groupBy = null;
		String having = null;
		String orderBy = null;
		return sqLiteDatabase.query("site_info", cols, selection, selectionArgs, groupBy, having, orderBy);
	}
}