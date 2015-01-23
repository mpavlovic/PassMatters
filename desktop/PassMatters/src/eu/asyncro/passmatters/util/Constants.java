/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.util;

/**
 * This class contains all application constant values. 
 * It can't be extended or instantiated.
 * 
 * @author Milan
 */
public final class Constants {
    
    /**
     * Private constructor does not allow 
     * class instantiation.
     */
    private Constants() {
        
    }
    
    private static final String USER_HOME_DIRECTORY = System.getProperty("user.home");
    private static final String APP_DIRECTORY = "/passmatters/";
    private static final String PASTE_SHORTCUT_FILE_NAME = "paste.ser";
    
    public static final String FULL_APP_DIRECTORY = USER_HOME_DIRECTORY + APP_DIRECTORY; 
    public static final String PASTE_SHORTCUT_FULL_PATH = FULL_APP_DIRECTORY
            + PASTE_SHORTCUT_FILE_NAME;
    
    
}
