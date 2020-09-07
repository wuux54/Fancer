package app.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/10/21
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class TextRegexUtils {
    public static boolean isMobileNum(String mobileNum) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */

        /*   "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，
         *  "[5,7,9]"表示可以是5,7,9中的任意一位,
         *  [^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
         */
//        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        String telRegex = "^(1[3-9])\\d{9}$";
        if (TextUtils.isEmpty(mobileNum)) {
            return false;
        } else {
            return mobileNum.matches(telRegex);
        }
    }


    /**
     * 数字+字母，数字+特殊字符，字母+特殊字符，数字+字母+特殊字符组合，而且不能是纯数字，纯字母，纯特殊字符
     * ^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$).{6,20}$
     * <p>
     * 上面的正则里所说的特殊字符是除了数字，字母之外的所有字符，
     * 如果要限定特殊字符，例如，特殊字符的范围为 !#$%^&* ，那么可以这么改
     * <p>
     * ^(?![\d]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[\da-zA-Z!#$%^&*]{6,20}$
     * <p>
     * (?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{8,20}$
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
//        String regex = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[\\da-zA-Z!#$%^&*]{6,20}$";
        String regex = "(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{8,32}$";
        String regex2 = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
//        return true;
    }

    public static boolean isPassword(String password, String rePassword) {
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword) || !password.equals(rePassword)) {
            return false;
        }

        return isPassword(password);
//        return true;
    }

    public static boolean isIscAccount(String iscAccount) {
        if (TextUtils.isEmpty(iscAccount)) {
            return false;
        }
//        String regex = "^(?![0-9])(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(iscAccount);
//        return m.matches();
        return true;
    }


    public static boolean isInvoiceNum(String isInvoiceNum) {
        if (TextUtils.isEmpty(isInvoiceNum)) {
            return false;
        }
        return isInvoiceNum.length() > 7 && isInvoiceNum.length() < 11;
    }

    /**
     * 是否是网络图片
     *
     * @param path
     * @return
     */
    public static boolean isNetUrl(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("http")
                    || path.startsWith("https")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为身份证
     *
     * @param idStr
     * @return
     */
    public static boolean isId(String idStr) {
        String regex = "^[0-9]{17}[0-9|xX]{1}$";
        if (idStr == null || "".equals(idStr.trim())) {
            return false;
        } else if (idStr.matches(regex)) {
            return true;
        }

        return false;
    }

    /**
     * @param num
     * @return
     */
    public static boolean inRangeNum(double num, double min, double max) {
        return num > min && num <= max;
    }

    /**
     * 禁止EditText输入空格和换行符
     *
     * @param editText EditText输入框
     */
//    public static void setEditTextInputSpace(EditText editText) {
//        InputFilter filter = new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                if (source.equals(" ") || source.toString().contentEquals("\n")) {
//                    String s = source.toString();
//                    s.replace("\n","");
//                    s.replace(" ","");
//                    return s;
//                }
//                return source;
//            }
//        };
//        editText.setFilters(new InputFilter[]{filter});
//    }
    public static void setEditTextInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return source.toString().replace("\n", " ");
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText EditText输入框
     */
    public static void setEditTextInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return source;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
}
