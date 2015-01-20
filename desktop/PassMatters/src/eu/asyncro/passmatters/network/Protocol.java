/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

/**
 * This class contains final JSON formatted messages which can be 
 * received from or send to server while communicating.
 * @author Milan
 */
public class Protocol {
    
    // RESPONSE MESSAGES
    /**
     * 'Bad credentials' message.
     */
    public static final String BAD_CREDENTIALS = "{\"code\":0,\"message\":\"Bad credentials\"}";
    
    /**
     * 'Connected' message.
     */
    public static final String CONNECTED = "{status: 1, message: \"Connected\"}";
    
    /**
     * 'User not found' message
     */
    public static final String USER_NOT_FOUND = "{status: 0, message: \"User not found\"}";
    
    /**
     * 'Authenticated' message
     */
    public static final String AUTHENTICATED = "{status: 1, message: \"Authenticated\"}";
    
    /**
     * 'User logged out' message
     */
    public static final String USER_LOGGED_OUT = "{\"code\":1,\"message\":\"User logged out\"}";
    
    
    // REQUEST MESSAGES
    /**
     * 'Logout' message
     */
    public static final String LOGOUT = "{\"step\": 3}";
}
