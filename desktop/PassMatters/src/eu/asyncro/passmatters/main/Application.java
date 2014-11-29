package eu.asyncro.passmatters.main;

/**
 *This is application entry point class with main method.
 * @author Milan
 */
public class Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            
            MainController maincontroller = new MainController();
            maincontroller.startApp();

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
            System.out.println(ex.getMessage());
        }   
    }
}
