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
    
    // response messages
    public static final String BAD_CREDENTIALS = "{\"code\":0,\"message\":\"Bad credentials\"}";
    public static final String CONNECTED = "{status: 1, message: \"Connected\"}";
    public static final String USER_NOT_FOUND = "{status: 0, message: \"User not found\"}";
    public static final String AUTHENTICATED = "{status: 1, message: \"Authenticated\"}";
    
    // request messages
    public static final String LOGOUT = "{step: 3}";
}
