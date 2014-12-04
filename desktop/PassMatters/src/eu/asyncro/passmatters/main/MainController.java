/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main;

import eu.asyncro.passmatters.config.paste.controller.PasteOptionController;
import eu.asyncro.passmatters.network.authentication.controller.AuthenticationController;

/**
 *
 * @author Milan
 */
public class MainController implements MainAppListener {
    
    private PasteOptionController pasteOptionController;
   AuthenticationController authController;
    
    public MainController() {
        initialize();
    }
    
    private void initialize() {
        authController = new AuthenticationController(this);
        pasteOptionController = new PasteOptionController(this);
    }
    
    public void startApp() {
        try {
            
            if(!pasteOptionController.isShortcutFileCreated()) {
                pasteOptionController.configurePasteOption();
            }
            else {
                authController.startLogin();
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            // TODO messenger
        }
    }
    
    @Override
    public void pasteConfigFinished() {
        if(!authController.isUserLoggedIn()) {
            authController.startLogin();
        }
    }
    

    @Override
    public void loginFinished() {
        try {
            System.out.println("login finished"); // todo remove
            
            //authController.logout(); // TODO remove!!
            
        } catch (Exception ex) {
            //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
}
