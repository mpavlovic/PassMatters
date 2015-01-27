/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Milan
 */
public class SymmetricEncrypterTest {
    
    private static byte[] secretKey;
    
    public SymmetricEncrypterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
            throws NoSuchAlgorithmException, InvalidKeySpecException, 
            UnsupportedEncodingException 
    {
        secretKey = EncryptionKeyGenerator.generateSecretKey("dummy", null);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    

    /**
     * Test of encrypt method, of class SymmetricEncrypter.
     */
    /*
    @Test
    public void testEncrypt() throws Exception {
        System.out.println("encrypt");
        String plainText = "";
        byte[] secretKey = null;
        String initVector = "";
        SymmetricEncrypter instance = new SymmetricEncrypter();
        String expResult = "";
        String result = instance.encrypt(plainText, secretKey, initVector);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    
    /**
     * Test of decrypt method, of class SymmetricEncrypter.
     */
    @Test
    public void testDecrypt() throws Exception {
        System.out.println("decrypt");
        String base64EncodedEncryptedString = "M/gxslqWSNOomFjw+adZ1g==";
        String base64InitVector = "5NS38F0RrUU2ctBpbO2BfA==";
        SymmetricEncrypter instance = new SymmetricEncrypter();
        byte[] expResultBytes = "facebook123".getBytes();
        String expResult = new String(expResultBytes, "utf-8");
        String result = instance.decrypt(base64EncodedEncryptedString, secretKey, base64InitVector);
        assertEquals(expResult, result);
    }
    
}
