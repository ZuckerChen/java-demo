package util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by user on 16/10/25.
 */
public class Md5DigestUtil {
    private static Logger logger = LoggerFactory.getLogger(Md5DigestUtil.class);

    public static void main(String[] args) {
        jdkMD5("test");
        ccMD5("test");
    }

    // 用jdk实现:MD5
    public static String jdkMD5(String str) {
        try {
            logger.info("Raw data :{}", str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(str.getBytes());
            logger.info("JDK MD5 :{}", Hex.encodeHexString(md5Bytes));
            return Hex.encodeHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    // 用common codes实现实现:MD5
    public static String ccMD5(String str) {
        logger.info("common codes MD5: {}", DigestUtils.md5Hex(str.getBytes()));
        return DigestUtils.md5Hex(str.getBytes());
    }

    // 用common codes实现实现:MD5, 指定编码
    public static String ccMD5(String str, String encoding) {
//        logger.info("md5src:{}", str);
        //md5签名
        byte[] srcBytes = null;
        try {
            srcBytes = str.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error("转换[{}]编码失败", encoding);
            return null;
        }
        return DigestUtils.md5Hex(srcBytes);

//        return DigestUtils.md5Hex(str);
    }

    public static String getFileMd5(String fullFilePath) {
        try {
            File file = new File(fullFilePath);
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            return Hex.encodeHexString(md.digest());
        } catch (Exception e) {
            logger.error(LoggerUtil.formatMsg() + "文件md5生成失败，{}", e.getMessage(), e);
            return null;
        }
    }

}
