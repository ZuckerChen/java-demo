package util;

//import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by user on 16/10/24.
 */
public class Base64Util {

    public static String encodeString(String s) {
        if (s == null)
            return null;
        try {
            return (new BASE64Encoder()).encode(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeByte(byte[] b) {
        return (new BASE64Encoder()).encode(b);
    }

    // BASE64 编码的字符串 s 进行解码
    public static String decodeToString(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    // BASE64 编码的字符串 s 进行解码
    public static byte[] decodeToByte(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static final String test = "test";

    public static void jdkBase64() throws IOException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode(test.getBytes());
        System.out.println(encode);

        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] decode = base64Decoder.decodeBuffer(encode);
        System.out.println(new String(decode));

    }
//
//    public static void commonsCodesBase64() throws IOException {
//        Base64 base64 = new Base64();
//        byte[] encode = base64.encode(test.getBytes());
//        System.out.println(new String(encode));
//
//        byte[] decode = base64.decode(encode);
//        System.out.println(new String(decode));
//
//    }

    public static void main(String args[]) throws IOException {

        jdkBase64();
//        commonsCodesBase64();

        System.out.println(encodeByte("test".getBytes()));

    }
}
