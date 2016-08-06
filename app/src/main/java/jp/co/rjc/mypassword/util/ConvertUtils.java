package jp.co.rjc.mypassword.util;


import android.content.Context;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jp.co.rjc.mypassword.R;

/**
 * 変換系共通機能を提供するクラスです.
 */
final public class ConvertUtils {

    private final static String ENC_METHOD = "AES";

    /**
     * インスタンス化できないことを強制します.
     */
    private ConvertUtils() {
    }

    /**
     * プレーンテキストを暗号化します.
     *
     * @param context
     * @param plain
     * @return
     * @throws Exception
     */
    public static String encrypt(final Context context, final String plain) {
        try {
            final String seed = context.getResources().getString(R.string.secret_key);
            byte[] rawKey = seed.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, ENC_METHOD);
            Cipher cipher = Cipher.getInstance(ENC_METHOD);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(plain.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 暗号化されたテキストを複合化します.
     *
     * @param context
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static String decrypt(final Context context, final String encrypted) {
        try {
            final String seed = context.getResources().getString(R.string.secret_key);
            byte[] rawKey = seed.getBytes();
            byte[] enc = Base64.decode(encrypted.getBytes(), Base64.DEFAULT);
            SecretKeySpec keySpec = new SecretKeySpec(rawKey, ENC_METHOD);
            Cipher cipher = Cipher.getInstance(ENC_METHOD);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
