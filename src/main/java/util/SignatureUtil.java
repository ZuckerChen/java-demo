package util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by chenzhang on 2016/9/26.
 */
public class SignatureUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignatureUtil.class);

    private static final String SIGNATURE_SHA1 = "SHA1withRSA";
    private static final String SIGNATURE_MD5 = "MD5withRSA";

    public static String signatureWithSHA1(String content, PrivateKey privateKey, String charset) {
        String base64 = "";
        try {
            Signature signature = Signature.getInstance(SIGNATURE_SHA1);

            signature.initSign(privateKey);
            signature.update(content.getBytes(charset));
            base64 = Base64.encodeBase64String(signature.sign());

        } catch (Exception e) {
            logger.error("签名失败", e);
        }
        return base64;
    }

    public static boolean verifyWithSHA1(String origin, String signData, PublicKey publicKey) {
        boolean flag = false;
        try {
            Signature signature = Signature.getInstance(SIGNATURE_SHA1);
            signature.initVerify(publicKey);
            signature.update(origin.getBytes());
            flag = signature.verify(Base64.decodeBase64(signData));
        } catch (Exception e) {
            logger.error("验签失败", e);
        }

        return flag;
    }

    public static String signatureWithMD5(String content, PrivateKey privateKey, String charset) {
        String base64 = "";
        try {
            Signature signature = Signature.getInstance(SIGNATURE_MD5);

            signature.initSign(privateKey);
            signature.update(content.getBytes(charset));
            base64 = Base64.encodeBase64String(signature.sign());

        } catch (Exception e) {
            logger.error("签名失败", e);
        }
        return base64;
    }

    public static boolean verifyWithMD5(String plain, String signData, PublicKey publicKey) {
        boolean flag = false;
        try {
            Signature signature = Signature.getInstance(SIGNATURE_MD5);
            signature.initVerify(publicKey);
            signature.update(plain.getBytes());
            flag = signature.verify(Base64.decodeBase64(signData));
        } catch (Exception e) {
            logger.error("验签失败", e);
        }

        return flag;
    }

    /**
     * 签名--连连专用--这里估计有公私钥密钥对的归属问题
     *
     * @param privateKey：私钥
     * @param str：签名源内容
     * @return
     */
    public static String signatureWithMD5(String privateKey, String str) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64Util.decodeToByte(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey key = keyFactory.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_MD5);
            signature.initSign(key);
            signature.update(str.getBytes("UTF-8"));
            byte[] signed = signature.sign(); // 对信息的数字签名
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验签--连连专用
     *
     * @param publicKey：公钥
     * @param origin：源
     * @param str：待验证签名结果串
     * @return
     */
    public static boolean verifyWithMD5(String publicKey, String origin,
                                        String str) {
        try {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
                    Base64Util.decodeToByte(publicKey)); // 这里对公钥进行了一次base64解密
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64Util.decodeToByte(str);// 这里对签名进行了一次base64解密
            Signature signature = Signature.getInstance(SIGNATURE_MD5);
            signature.initVerify(pubKey);
            signature.update(origin.getBytes("UTF-8"));
            return signature.verify(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final String test = "test";

    public static void jdkRsa() {
        try {
            // 1.初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            // 2.进行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(test.getBytes());
            byte[] result = signature.sign();
            System.out.println("jdk rsa sign:" + Hex.encodeHexString(result));

            // 3.验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(test.getBytes());
            boolean bool = signature.verify(result);
            System.out.println("jdk rsa verify:" + bool);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
