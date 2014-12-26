/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main;

import eu.asyncro.passmatters.config.paste.controller.PasteOptionController;
import eu.asyncro.passmatters.network.authentication.controller.AuthenticationController;
import eu.asyncro.passmatters.util.Messenger;

/**
 * Main controller class which establishes main application workflow.
 * @author Milan
 */
public class MainController implements MainAppListener, MainFrameListener {
    
    private PasteOptionController pasteOptionController;
    private AuthenticationController authController;
    
    private MainFrame mainFrame;
    
    /**
     * Constructor.
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
        try {
            System.out.println("login finished"); // todo remove
            
            //authController.logout(); // TODO implement and remove!!
            
            mainFrame.showFrame();
            
        } catch (Exception ex) {
            //Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void passwordFilled() {
        // TODO show on history or buble
        System.out.println("password filled");
    }
    
}
