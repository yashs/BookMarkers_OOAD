package pkg.database;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;
 
public final class Encrypt {
    public static synchronized String encrypt(String plaintext) throws Exception {
        MessageDigest msgDigest = null;
        String hashValue = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update(plaintext.getBytes("UTF-16"));
            byte rawByte[] = msgDigest.digest();
            hashValue = (new BASE64Encoder()).encode(rawByte);
 
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm Exists");
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Encoding Is Not Supported");
        }
        return hashValue;
    }
 
    /*public static void main(String args[]) throws Exception {
        String plainPassword = "SecretPassword";
 
        System.out.println("PlainText\tAlgo\tEncoding\tEncrypted Password");
        System.out.println(plainPassword + "\tSHA\tUTF-8\t"
                + encrypt("MySecretPassword", "SHA", "UTF-8"));
        System.out.println(plainPassword + "\tSHA-1\tUTF-16\t"
                + encrypt("MySecretPassword", "SHA-1", "UTF-16"));
        System.out.println(plainPassword + "\tMD5\tUTF-8\t"
                + encrypt("MySecretPassword", "MD5", "UTF-8"));
        System.out.println(plainPassword + "\tMD5\tUTF-16\t"
                + encrypt("MySecretPassword", "MD5", "UTF-16"));
 
    }*/
}
