package eu.asyncro.passmatters.main;

import static eu.asyncro.passmatters.util.Constants.PASTE_SHORTCUT_FILE_NAME;
import eu.asyncro.passmatters.config.paste.controller.PasteOptionController;
import eu.asyncro.passmatters.network.ConnectionController;
import eu.asyncro.passmatters.network.TCPSocketConnectionController;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 *This is application main class with main method.
 * @author Milan
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if(!isShortcutFileCreated()) {
            PasteOptionController pasteOptionConfigurator = new PasteOptionController();
            pasteOptionConfigurator.configurePasteOption();
        }
        else {
            try {
                // TODO login - this is only test
                /*
                System.out.println("login...");
                Hashtable<String, String> params = new Hashtable<>();
                params.put("username","admik");
                params.put("password", "admin");
                Client client = new HTTPClient("http://178.62.212.164/api/login", Client.REQUEST_POST, params);
                client.sendRequest();
                */
                
                /*
                SenderInterface s = new SSLTCPSender();
                ConnecControl cc = new TCPSCC(s);
                */
                
                
                ConnectionController connectionController = new TCPSocketConnectionController();
                connectionController.openConnection();
                connectionController.sendData("{\"step\":1,\"token\":\"e4d64c7d0dcf3996eac73cb0e5a654766fff0dcc\"}");
                connectionController.closeConnection();
                        
                
            } catch (MalformedURLException | UnsupportedEncodingException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        
    }
    
    private static boolean isShortcutFileCreated() {
        File file = new File(PASTE_SHORTCUT_FILE_NAME);
        return file.exists();
    }
    
}
