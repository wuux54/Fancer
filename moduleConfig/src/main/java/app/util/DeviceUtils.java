package app.util;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/11/11
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

public class DeviceUtils {
//
//    public static String getDeviceId(Context context) {
//        String deviceId = "";
//        if (deviceId != null && !"".equals(deviceId)) {
//            return deviceId;
//        }
//        if (deviceId == null || "".equals(deviceId)) {
//            try {
//                deviceId = getLocalMac(context).replace(":", "");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (deviceId == null || "".equals(deviceId)) {
//            try {
//                deviceId = getAndroidId(context);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (deviceId == null || "".equals(deviceId)) {
//
//            if (deviceId == null || "".equals(deviceId)) {
//                UUID uuid = UUID.randomUUID();
//                deviceId = uuid.toString().replace("-", "");
//                writeDeviceID(deviceId);
//            }
//        }
//        return deviceId;
//    }
//    // IMEI码
//    private static String getIMIEStatus(Context context) {
//        TelephonyManager tm = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceId = tm.getDeviceId();
//        return deviceId;
//    }

    // Mac地址
    public static String getLocalMac(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    // Android Id
    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

//    public static void saveDeviceID(String str) {
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            Writer out = new OutputStreamWriter(fos, "UTF-8");
//            out.write(str);
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String readDeviceID() {
//        StringBuffer buffer = new StringBuffer();
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//            Reader in = new BufferedReader(isr);
//            int i;
//            while ((i = in.read()) > -1) {
//                buffer.append((char) i);
//            }
//            in.close();
//            return buffer.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


}
