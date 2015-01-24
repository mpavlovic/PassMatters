/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.asyncro.passmatters.security;

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
    
    /**
     * Generates secret key from provided password and salt.
     * Uses some of internal functions for key generation. 
     * @param password password to generate the secret key from
     * @param salt parameter for key generation function
     * @return return value (byte array) from private function used for key generation
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException 
     */
    public static byte[] generateSecretKey(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            UnsupportedEncodingException 
    {
        return generatePBKDF2Key(password, salt);
    }

    /**
     * Generates secret key for symmetric encryption/decryption using 
     * PBKDF2 (Password Based Key Derivation Function 2) function.
     * @param password password to generate the secret key from
     * @param salt parameter for PBKDF2 key generation function
     * @return generated secret key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] generatePBKDF2Key(String password, String salt) 
            throws NoSuchAlgorithmException, InvalidKeySpecException, 
                UnsupportedEncodingException 
    {
        int keyLength = 256;
        int numberOfIterations = 120000;
        
        byte[] saltBytes = bytesToHex(getSalt()).getBytes(); // salt.getBytes();
        char[] passwordChars = password.toCharArray();
        
        PBEKeySpec keySpecifications = new PBEKeySpec(passwordChars, saltBytes, numberOfIterations, keyLength);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = secretKeyFactory.generateSecret(keySpecifications).getEncoded();
        
        System.out.println(bytesToHex(key)); // TODO remove
        
        return key;
    }

    /**
     * Returns salt for key generation if needed. This function should be used
     * if salt needs to be generated inside of this class.
     * @return generated salt
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException, 
            UnsupportedEncodingException 
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        byte[] digest = messageDigest.digest("passmatters.eu".getBytes("utf-8"));
        System.out.println("Hash: " + bytesToHex(digest)); // TODO remove
        return digest;
    }
    
    // 
    /**
     * Converts given byte array to hexadecimal string.
     * @param bytes byte array that wants to be converted to hex string
     * @return hexadecimal string representing given byte array in byte parameter
     * @see http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    /**
     * Converts given hexadecimal string to byte array.
     * @param hexString hex string that wants to be converted to byte array
     * @return byte array of hex string provided in hexString argument;
     *         each byte represents to appropriate char in hexString
     */
    public static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    
    
}
