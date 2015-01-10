/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.asycro.passmatters.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This class contains methods for generating encryption keys for symmetric and
 * asymmetric encryption.
 *
 * @author Milan
 */
public class EncryptionKeyGenerator {
    
    public static byte[] generateSecretKey(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            UnsupportedEncodingException 
    {
        return generatePBKDF2Key(password, salt);
    }

    private static byte[] generatePBKDF2Key(String password, String salt) 
            throws NoSuchAlgorithmException, InvalidKeySpecException, 
                UnsupportedEncodingException 
    {
        int keyLength = 20*8;
        int numberOfIterations = 4096;
        
        byte[] saltBytes = salt.getBytes();
        char[] passwordChars = password.toCharArray();
        
        PBEKeySpec keySpecifications = new PBEKeySpec(passwordChars, saltBytes, numberOfIterations, keyLength);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = secretKeyFactory.generateSecret(keySpecifications).getEncoded();
        
        System.out.println(bytesToHex(key)); // TODO remove
        
        return key;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException, 
            UnsupportedEncodingException 
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        byte[] digest = messageDigest.digest("passmatters.eu".getBytes("utf-8"));
        return digest;
    }
    
    // http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
    private static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    private static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    
    
}
