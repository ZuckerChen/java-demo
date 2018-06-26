package util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pay.biz.gateway.cert.CertificateLoder;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by chenzhang on 2016/9/8.
 */
public class RsaUtil {

    private static final Logger logger = LoggerFactory.getLogger(RsaUtil.class);

    private static int RSA_ENCRYPT_MAX_LENGTH = 117;

    private static int RSA_DECRYPT_MAX_LENGTH = 128;

    /**
     * RSA算法实现
     *
     * @param src  源字节
     * @param key  公钥/私钥
     * @param mode 加密/解密
     * @return
     */
    private static byte[] algorithm4RSA(byte[] src, Key key, int mode) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(mode, key);
            // 分段加密 RSA加密明文最大长度 117,解密最大密文长度128
            int blockSize = (mode == Cipher.ENCRYPT_MODE) ? RSA_ENCRYPT_MAX_LENGTH : RSA_DECRYPT_MAX_LENGTH;
            byte[] result = null;
            for (int i = 0; i < src.length; i += blockSize) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(subarray(src, i, i + blockSize));
                result = addAll(result, doFinal);
            }
            return result;
        } catch (Exception e) {
            logger.error("解密失败", e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param plain 明文
     * @param key   公钥/私钥
     * @return
     */
    public static String encrypt4RSA(String plain, Key key) {
        String cipherText = null;
        //转BASE64
        byte[] plain2base64 = Base64.encodeBase64(plain.getBytes());
        //加密
        byte[] result = algorithm4RSA(plain2base64, key, Cipher.ENCRYPT_MODE);
        if (result == null) return null;

        cipherText = byte2Hex(result);
        return cipherText;
    }

    /**
     * 加密
     *
     * @param plain 明文
     * @param key   公钥/私钥
     * @return
     */
    public static String encrypt4RSA(String plain, Key key, String charset) {
        //转BASE64
        byte[] plain2base64 = new byte[0];
        try {
            plain2base64 = Base64.encodeBase64(plain.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            logger.error("base64编码失败", e);
            return null;
        }
        //加密
        byte[] result =  algorithm4RSA(plain2base64, key, Cipher.ENCRYPT_MODE);
        if (result == null) return null;

        String cipherText = byte2Hex(result);
        return cipherText;
    }

    /**
     * 解密
     *
     * @param cipher 密文
     * @param key    公钥/私钥
     * @return
     */
    public static String decrypt4RSA(String cipher, Key key) {
        String plainText = null;
        byte[] cipher2Byte = hex2Bytes(cipher);

        byte[] result = algorithm4RSA(cipher2Byte, key, Cipher.DECRYPT_MODE);
        if (result == null) return null;
        plainText = new String(Base64.decodeBase64(result));
        return plainText;
    }


    private static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return new byte[0];
        }

        byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    private static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    private static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    private static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

    private static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    public static void main(String[] args) throws Exception {
        String key = "d:/home/localadmin/config/bf/bfkey_100000276@@100000994.pfx";
        String password = "123456";
        PrivateKey privateKey = CertificateLoder.getPrivateKey(key, password);
        String key1 = "d:/home/localadmin/config/bf/bfkey_100000276@@100000994.cer";
        PublicKey publicKey = CertificateLoder.getPublicKey(key1);

        String plain = "123456|1232313|123123";
        System.out.printf("plain:" + plain);
        String cipher = encrypt4RSA("123456|1232313|123123", privateKey);
        System.out.printf("cipher:" + cipher);
        String result = decrypt4RSA(cipher, privateKey);
        System.out.printf("result:" + result);

        jdkRsa();

    }

    public static final String test = "test";

    public static void jdkRsa() throws Exception {

        //初始化密钥
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println(Base64.encodeBase64String(rsaPublicKey.getEncoded()));
        System.out.println(Base64.encodeBase64String(rsaPrivateKey.getEncoded()));

        // 2.私钥加密、公钥解密 ---- 加密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(test.getBytes());
        System.out.println("私钥加密、公钥解密 ---- 加密:" + Base64.encodeBase64String(result));

        // 3.私钥加密、公钥解密 ---- 解密
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        result = cipher.doFinal(result);
        System.out.println("私钥加密、公钥解密 ---- 解密:" + new String(result));


        // 4.公钥加密、私钥解密 ---- 加密
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
        PublicKey publicKey2 = keyFactory2.generatePublic(x509EncodedKeySpec2);
        Cipher cipher2 = Cipher.getInstance("RSA");
        cipher2.init(Cipher.ENCRYPT_MODE, publicKey2);
        byte[] result2 = cipher2.doFinal(test.getBytes());
        System.out.println("公钥加密、私钥解密 ---- 加密:" + Base64.encodeBase64String(result2));

        // 5.私钥解密、公钥加密 ---- 解密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory5 = KeyFactory.getInstance("RSA");
        PrivateKey privateKey5 = keyFactory5.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher5 = Cipher.getInstance("RSA");
        cipher5.init(Cipher.DECRYPT_MODE, privateKey5);
        byte[] result5 = cipher5.doFinal(result2);
        System.out.println("公钥加密、私钥解密 ---- 解密:" + new String(result5));

    }

}
