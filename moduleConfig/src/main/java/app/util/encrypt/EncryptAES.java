package app.util.encrypt;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/11/7
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptAES {

    /**
     * aes 加密
     *
     * @param data
     * @return
     */
    public static String encryptData(String data, String secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(EncryptAesKey.getIv().getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new String(Base64Utils.encode1(encrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * aes 解密
     *
     * @param data 密文
     * @return
     */
    public static String decryptData(String data, String secretKey, String iv) {
        try {
            byte[] encrypted1 = Base64Utils.decode1(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,"utf-8");
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * aes 加密
     *
     * @param data
     * @return
     */
    public static String encryptData(String data) {
        return encryptData(data, EncryptAesKey.getAesK());
    }

    /**
     * aes 解密
     *
     * @param data 密文
     * @return
     */
    public static String decryptData(String data) {
        return decryptData(data, EncryptAesKey.getAesK(), EncryptAesKey.getIv());
    }


//	    public static void main(String[] args) {
//	        String data="{\"data\":{\"token\":\"\",\"userId\":\"1003\",\"targetURI\":\"account/getRSAPublicKey\",\"appID\":\"ts002\"},\"targetURI\":\"account/getRSAPublicKey\",\"appID\":\"ts002\"}";
//	        System.out.println(data);
//	        String enStr= EncryptAES2.encryptData(data);
//	        System.out.println("加密:"+enStr);
//	        String deStr= EncryptAES2.decryptData(enStr);
//	        System.out.println("解密:"+deStr);
//	    }

//    public static void main(String[] args) {
//        String deStr=EncryptAES2.decryptData("4fv16EGEoEvKsds6Iahmfz1D88AfoIX4Nash6NZXK+mL/PsChdHHt9es8851pdlk");
//        System.out.println("解密:"+deStr);
//    }

}
