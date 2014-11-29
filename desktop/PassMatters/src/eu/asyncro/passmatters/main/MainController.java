/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main;

import eu.asyncro.passmatters.config.paste.controller.PasteOptionController;
import eu.asyncro.passmatters.network.login.controller.LoginController;
import static eu.asyncro.passmatters.util.Constants.PASTE_SHORTCUT_FILE_NAME;
import java.io.File;

/**
 *
 * @author Milan
 */
public class MainController implements MainAppListener {
    
    private PasteOptionController pasteOptionController;
    private LoginController loginController;
    
    public MainController() {
        initialize();
    }
    
    private void initialize() {
        loginController = new LoginController(this);
        pasteOptionController = new PasteOptionController(this);
    }
    
    public void startApp() {
        try {
            
            if(!pasteOptionController.isShortcutFileCreated()) {
                pasteOptionController.configurePasteOption();
            }
            else {
                loginController.startLogin();
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            // TODO messenger
        }
    }
    
    @Override
    public void pasteConfigFinished() {
        if(!loginController.isUserLoggedIn()) {
            loginController.startLogin();
        }
    }
    

    @Override
    public void loginFinished() {
        System.out.println("login finished");
    }
    
}
