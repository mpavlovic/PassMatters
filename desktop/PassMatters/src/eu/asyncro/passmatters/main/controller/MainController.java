/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main.controller;

import eu.asyncro.passmatters.main.view.MainFrame;
import eu.asyncro.passmatters.config.paste.controller.PasteOptionController;
import eu.asyncro.passmatters.network.authentication.controller.AuthenticationController;
import eu.asyncro.passmatters.util.Messenger;
import java.awt.AWTException;
import javax.swing.JFrame;

/**
 * Main controller class which establishes main application workflow.
 * @author Milan
 */
public class MainController implements MainAppListener, MainFrameListener {
    
    private PasteOptionController pasteOptionController;
    private AuthenticationController authController;
    private SystemTrayController systemTrayController;
    
    private MainFrame mainFrame;
    
    /**
     * Creates new instance of this class.
     */
    public MainController() {
        initialize();
    }
    
    /**
     * Initializes all other components (controllers etc.).
     */
    private void initialize() {
        authController = new AuthenticationController(this);
        pasteOptionController = new PasteOptionController(this);
        systemTrayController = new SystemTrayController(this);
        
        mainFrame = new MainFrame();
        mainFrame.setMainFrameListener(this);
    }
    
    /**
     * First method which is called in Application's main method.
     * If Application is started for first time, paste option 
     * configuration wizard begins. In other case app login starts.
     */
    public void startApp() {
        try {
            
            if(!pasteOptionController.isShortcutFileCreated()) {
                pasteOptionController.configurePasteOption();
            }
            else {
                authController.startLogin();
            }
            
        } catch(Exception ex) {
            System.out.println(ex.getMessage()); // TODO remove
            Messenger.showErrorMessage("Unexpected error occured.", null);
        }
    }
    
    /**
     * Method is called every time when paste option
     * configuration is finished. If user is not logged in,
     * login will be performed.
     */
    @Override
    public void pasteConfigFinished() {
        if(!authController.isUserLoggedIn()) {
            authController.startLogin();
        }
    }
    
    /**
     * Method is called every time when login procedure
     * is finished. Main app window will be shown. 
     */
    @Override
    public void loginFinished() {
        System.out.println("login finished"); // todo remove
        mainFrame.setStatus("You are logged in.");
        mainFrame.showFrame();
        showInSystemTray();
    }

    /**
     * Shows application's system tray icon if not already showed.
     * Calls showInSystemTray() method SystemTrayController
     */
    private void showInSystemTray() {
        if(systemTrayController.isSystemTraySupported()
                && !systemTrayController.isTrayIconAdded()) 
        {
            try {
                systemTrayController.showInSystemTray();
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            } catch (AWTException | UnsupportedOperationException | SecurityException ex) {
                System.out.println("Unexpected problem with System Tray occured.");
                ex.printStackTrace(); // TODO remove
            }
        }
    }
    
    @Override
    public void passwordFilled() {
        // TODO show on history or buble
        System.out.println("password filled");
    }

    @Override
    public void logoutPerformed() {
        mainFrame.setStatus("Logging out...");
        authController.logout();
    }

    @Override
    public void logoutFinished() {
        mainFrame.dispose();
        authController.startLogin();
    }

    @Override
    public void systemTrayActionPerformed() {
        if(authController.isUserLoggedIn()) {
            if(!mainFrame.isVisible()) {
                mainFrame.showFrame();
            }
            else {
                mainFrame.toFront();
            }
        }
    }

    @Override
    public byte[] passwordArrived() {
        return authController.getSecretKey();
    }
    
}
