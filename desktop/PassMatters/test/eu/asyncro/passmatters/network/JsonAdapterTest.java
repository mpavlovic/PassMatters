package eu.asyncro.passmatters.network;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Milan
 */
public class JsonAdapterTest {
    
    public JsonAdapterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getToken method, of class JsonAdapter.
     */
    @Test
    public void testGetToken() {
        System.out.println("getToken");
        String jsonString = "{\"code\": 1, \"message\": \"Success\", \"token\": \"user_54665946e46653.27284524\"}";
        String expResult = "user_54665946e46653.27284524";
        String result = JsonAdapter.getToken(jsonString);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAuthJsonString method, of class JsonAdapter.
     */
    @Test
    public void testGetAuthJsonString() {
        System.out.println("getAuthJsonString");
        String token = "user_54665946e46653.27284524";
        String expResult = "{\"token\":\"user_54665946e46653.27284524\",\"step\":1}";
        String result = JsonAdapter.getAuthJsonString(token);
        assertEquals(expResult, result);
    }
    
}
