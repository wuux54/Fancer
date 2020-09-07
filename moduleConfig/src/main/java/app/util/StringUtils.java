package app.util;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/11/5
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class StringUtils {
    public static boolean hasText(CharSequence str)
    {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasLength(CharSequence str)
    {
        return (str != null) && (str.length() > 0);
    }
    public static boolean isEmpty(Object str)
    {
        return (str == null) || ("".equals(str));
    }
}
