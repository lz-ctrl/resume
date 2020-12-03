package com.resume.api.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @author XiaoWeiBiao
 */
public final class CyptoUtils {


    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_DES = "AES/CBC/PKCS5Padding";

    private CyptoUtils() {

    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws
     * @throws Exception
     */
    public static String encode(String data, String key) {
        if (data == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("1102030405060708".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key), paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return byte2hex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }


    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String data, String key) {
        if (data == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("1102030405060708".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key), paramSpec);
            return new String(cipher.doFinal(hex2byte(data.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }


    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //AES 要求密钥长度为 128
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes(StandardCharsets.UTF_8));
            kg.init(128, secureRandom);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            //转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //-------------------------------------private----------------------------------------------------------------------------------------

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException();
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }


}
