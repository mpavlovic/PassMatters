/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.asycro.passmatters.security;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Milan
 */
public class SymmetricEncrypter extends Encrypter {

    public SymmetricEncrypter()
            throws NoSuchAlgorithmException, NoSuchPaddingException 
    {
        super();
        this.cipherAlgorithmName = "AES/CBC/PKCS5Padding";
        this.cipher = Cipher.getInstance(cipherAlgorithmName);
    }

    public String encrypt(String plainText, byte[] secretKey, String initVector) // init v je string
            throws Exception 
    {
        byte[] plainTextBytes = plainText.getBytes(charset);
        byte[] initVectorBytes = initVector.getBytes(charset);
        byte[] encryptedBytes = encrypt(plainTextBytes, secretKey, initVectorBytes);
        return Base64.encode(encryptedBytes);
    }

    private byte[] encrypt(byte[] plainTextBytes, byte[] secretKey, byte[] initVector)
            throws Exception 
    {
        Key key = getSecretKeySpec(secretKey);
        IvParameterSpec ivParamSpec = getIvParameterSpec(initVector);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
        return encryptedBytes;
    }

    public String decrypt(String base64EncryptedString, byte[] secretKey, String initVector)
            throws Exception 
    {
        byte[] decodedEncryptedBytes = Base64.decode(base64EncryptedString);
        byte[] initVectorBytes = initVector.getBytes(charset);
        byte[] decryptedBytes = decrypt(decodedEncryptedBytes, secretKey, initVectorBytes);
        return new String(decryptedBytes, charset);
    }

    private byte[] decrypt(byte[] encryptedBytes, byte[] secretKey, byte[] initVector)
            throws Exception 
    {
        Key key = getSecretKeySpec(secretKey);
        IvParameterSpec ivParamSpec = getIvParameterSpec(initVector);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
        byte[] plainTextBytes = cipher.doFinal(encryptedBytes);
        return plainTextBytes;
    }

    private Key getSecretKeySpec(byte[] secretKey) {
        return new SecretKeySpec(secretKey, "AES");
    }

    private IvParameterSpec getIvParameterSpec(byte[] initVector) {
        return new IvParameterSpec(initVector);
    }
    
}
