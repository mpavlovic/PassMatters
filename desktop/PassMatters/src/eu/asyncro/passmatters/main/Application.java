package eu.asyncro.passmatters.main;

import eu.asyncro.passmatters.main.controller.MainController;
import eu.asyncro.passmatters.util.FormFiller;

/**
 * This is application entry point class with main method.
 * @author Milan
 */
public class Application {
    
    /**
     * Main. Starts the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            MainController maincontroller = new MainController();
            maincontroller.startApp();
            
            /*
            FormFiller ff = new FormFiller();
            boolean res = ff.fillFocusedForm("1q2w3e");
            if(res) System.out.println("OK");
            else System.out.println("FALSE");
            System.exit(0);
            */
            
            //System.out.println("gotovo");
            
            
            /*
            System.out.println("login...");
            Hashtable<String, String> params = new Hashtable<>();
            params.put("username","admin");
            params.put("password", "admin");
            Client client = new HTTPClient("http://178.62.212.164/api/login", Client.REQUEST_POST, params);
            client.sendRequest();
            */

            /*
            SenderInterface s = new SSLTCPSender();
            ConnecControl cc = new TCPSCC(s);
            */

            /*
            ConnectionController connectionController = new TCPSocketConnectionController();
            connectionController.openConnection();
            connectionController.sendData("{\"step\":1,\"token\":\"e4d64c7d0dcf3996eac73cb0e5a654766fff0dcc\"}");
            connectionController.closeConnection();
            */        

        } catch (Exception ex) {
            ex.printStackTrace();
        }   
    }
}
