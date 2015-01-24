/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.asyncro.passmatters.security;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is used for symmetric encryption and decryption logic.
 * @author Milan
 */
public class SymmetricEncrypter extends Encrypter {

    /**
     * Creates new instance of this class.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException 
     */
    public SymmetricEncrypter()
            throws NoSuchAlgorithmException, NoSuchPaddingException 
    {
        super();
        this.cipherAlgorithmName = "AES/CBC/NoPadding";
        this.cipher = Cipher.getInstance(cipherAlgorithmName);
    }

    /**
     * Encrypts the given plaintext.
     * @param plainText plain text to be encrypted
     * @param secretKey encryption secret key
     * @param initVector encryption initialization vector
     * @return Base64 encoded string which was previously encrypted
     * @throws Exception 
     */
    public String encrypt(String plainText, byte[] secretKey, String initVector) // init v je string
            throws Exception 
    {
        byte[] plainTextBytes = plainText.getBytes(charset);
        byte[] initVectorBytes = initVector.getBytes(charset);
        byte[] encryptedBytes = encrypt(plainTextBytes, secretKey, initVectorBytes);
        return Base64.encode(encryptedBytes);
    }

    /**
     * Encrypts the given plaintext bytes.
     * @param plainTextBytes plaintext bytes to be encrypted
     * @param secretKey encryption secret key 
     * @param initVector bytes of initialization vector
     * @return encrypted bytes from plaintext (ciphertext)
     * @throws Exception 
     */
    private byte[] encrypt(byte[] plainTextBytes, byte[] secretKey, byte[] initVector)
            throws Exception 
    {
        Key key = getSecretKeySpec(secretKey);
        IvParameterSpec ivParamSpec = getIvParameterSpec(initVector);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
        byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
        return encryptedBytes;
    }

    /**
     * Decrypts the given Base64 string which was previously encrypted.
     * @param base64EncodedEncryptedString string to be decrypted
     * @param secretKey decryption secret key
     * @param base64InitVector Base64 encoded initialization vector
     * @return decrypted plaintext string 
     * @throws Exception 
     */
    public String decrypt(String base64EncodedEncryptedString, byte[] secretKey, String base64InitVector)
            throws Exception 
    {
        byte[] decodedEncryptedBytes = Base64.decode(base64EncodedEncryptedString);
        byte[] initVectorBytes = Base64.decode(base64InitVector);
        byte[] decryptedBytes = decrypt(decodedEncryptedBytes, secretKey, initVectorBytes);
        return new String(decryptedBytes, charset);
    }

    /**
     * Decrypts the given encrypted ciphertext bytes.
     * @param encryptedBytes ciphertext bytes that wants to be decrypted
     * @param secretKey decryption secret key
     * @param initVector bytes of initialization vector
     * @return plaintext bytes
     * @throws Exception 
     */
    private byte[] decrypt(byte[] encryptedBytes, byte[] secretKey, byte[] initVector)
            throws Exception 
    {
        Key key = getSecretKeySpec(secretKey);
        IvParameterSpec ivParamSpec = getIvParameterSpec(initVector);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
        byte[] plainTextBytes = cipher.doFinal(encryptedBytes);
        return plainTextBytes;
    }

    /**
     * Returns secret key specifications
     * @param secretKey bytes for specification
     * @return new instance of SecretKeySpec for given secretKey
     * @see javax.crypto.spec.SecretKeySpec
     */
    private Key getSecretKeySpec(byte[] secretKey) {
        return new SecretKeySpec(secretKey, "AES");
    }

    /**
     * Returns initialization vector parameter specifications 
     * @param initVector bytes for parameter specs
     * @return new instance of IVParameterSpec for given initVector
     * @see javax.crypto.spec.IvParameterSpec
     */
    private IvParameterSpec getIvParameterSpec(byte[] initVector) {
        return new IvParameterSpec(initVector);
    }
    
}
