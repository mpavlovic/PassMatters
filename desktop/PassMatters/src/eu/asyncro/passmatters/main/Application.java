package eu.asyncro.passmatters.main;

import eu.asyncro.passmatters.main.controller.MainController;
import eu.asyncro.passmatters.util.Messenger;

/**
 * This is application entry point class with main method.
 * @author Milan
 */
public class Application {
    
    /**
     * The main method. Starts the application by instantiating 
     * MainController class and calling .startApp() method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            
            MainController maincontroller = new MainController();
            maincontroller.startApp();
           

        } catch (Exception ex) {
            ex.printStackTrace();
            Messenger.showErrorMessage(ex.getMessage(), null);
        }   
    }
}
