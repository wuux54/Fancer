package app.util.encrypt;

import android.text.TextUtils;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/11/11
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class EncryptAesKey {

    private static String aesK = null;

    public static String getAesK() {
        if (TextUtils.isEmpty(aesK)) {
            aesK = createKey();
        }
        return aesK;
    }

    private synchronized static String createKey() {
        synchronized (EncryptAesKey.class) {
            int[] arr = {110, 98, 87, 111, 110, 100, 101, 114, 115, 71, 114, 111, 117, 112};
            StringBuilder builder = new StringBuilder();
            for (int i1 : arr) {
                builder.append((char) i1);
            }
            char x = 'X';
            builder.append((int) x);
            return builder.toString();
        }
    }

    public static String getIv() {
        if (TextUtils.isEmpty(aesK)) {
            aesK = createKey();
        }
        return aesK;
    }
}
