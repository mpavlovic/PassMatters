/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.security;

import java.lang.reflect.Field;
import javax.crypto.Cipher;

/**
 * Abstract class for different encryption/decryption concrete classes.
 * @author Milan
 */
public abstract class Encrypter {
    
    protected Cipher cipher;
    protected String cipherAlgorithmName;
    protected final String charset = "UTF-8";

    /**
     * Constructor. Removes JCE restriction
     * @see removeJCRRestriction()
     */
    public Encrypter() {
        removeJCERestriction();
    }
    
    /**
     * Removes Java Cryptography Extension restriction to allow
     * symmetric encryption keys bigger than 128 bits.
     */
    private void removeJCERestriction() {
        try { 
            Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, java.lang.Boolean.FALSE); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
