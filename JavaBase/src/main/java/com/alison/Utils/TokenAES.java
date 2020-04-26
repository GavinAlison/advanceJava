package com.alison.Utils;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * aes加密解密
 */
public class TokenAES {

    public static final String DES = "AES";
    public static final String CIPHER_ALGORITHM = "AES";
    private static final String DEFAULT_KEY = "encrypt_@!1029";

    public static String encrypt(String content){
        return encrypt(content,DEFAULT_KEY);
    }

    public static String encrypt(String content, String secretkey) {
        try {
            SecureRandom sr = new SecureRandom();
            Key secureKey = getKey(secretkey);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
            byte[] bt = cipher.doFinal(content.getBytes());
            String strS = Base64.getEncoder().encodeToString(parseByte2HexStr(bt).getBytes("UTF-8"));
            return  strS.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content){
        return decrypt(content,DEFAULT_KEY);
    }

    public static String decrypt(String content, String secretkey) {
        try {
            SecureRandom sr = new SecureRandom();
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            Key secureKey = getKey(secretkey);
            cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
            byte[] res = Base64.getDecoder().decode(content);
            byte[] str = parseHexStr2Byte(new String(res));
            res = cipher.doFinal(str);
            return new String(res);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Key getKey(String strKey) {
        try {
            if (strKey == null) {
                strKey = "";
            }
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(128, secureRandom);
            return _generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(" 初始化密钥出现异常 ");
        }
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


}
