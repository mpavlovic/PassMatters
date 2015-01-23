/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main.controller;

/**
 * Interface containing methods which are called
 * from other application controllers and classes when particular 
 * application event occurs. 
 * This interface is implemented by MainController class.
 * @author Milan
 */
public interface MainAppListener {

    /**
     * This method is called from PasteOptionController when 
     * user's default keyboard shortcut for paste option is
     * recorded, validated and saved.
     */
    public void pasteConfigFinished();

    /**
     * This method is called when user login is finished
     * successfully in AuthenticationController.
     */
    public void loginFinished();

    /**
     * This method is called when user logout is finished
     * successfully in AuthenticationController.
     */
    public void logoutFinished();

    /**
     * This method is called from FormFillListener 
     * when user password is successfully placed in 
     * focused form field.
     */
    public void passwordFilled();

    /**
     * This method is called from SystemTrayContrtoller 
     * when user double-clicks on system tray icon.
     */
    public void systemTrayActionPerformed();
    
    /**
     * This method is called when password is received from remote server.
     * @return password byte array
     */
    public byte[] passwordArrived();
}
