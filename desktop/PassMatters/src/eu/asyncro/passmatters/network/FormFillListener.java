/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import eu.asyncro.passmatters.main.controller.MainAppListener;
import eu.asyncro.passmatters.security.SymmetricEncrypter;
import eu.asyncro.passmatters.util.FormFiller;
import eu.asyncro.passmatters.util.Messenger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;

/**
 * This class is responsible for listening if incoming message from remote 
 * server arrives. This message is actually a password which need to be decrypted 
 * and placed in focused form field. Listening is established in separate thread.
 * 
 * @author Milan
 */
public class FormFillListener extends Thread {

    private final FormFiller formFiller;
    private final Connector connector;
    private final MainAppListener mainAppListener;
    private final String DELIMITER = "\\|::\\|";
    private SymmetricEncrypter symmetricEncrypter;
    
    /**
     * Constructor.
     * @param connector Connector for establishing TCP connection to the remote server 
     * via (SSL)Socket.
     * @param mainAppListener MainAppListener which needs to be noticed when focused
     * form field is filled with received password.
     */
    public FormFillListener(Connector connector, MainAppListener mainAppListener) 
            throws NoSuchAlgorithmException, NoSuchPaddingException 
    {
        this.connector = connector;
        this.mainAppListener = mainAppListener;
        formFiller = new FormFiller();
        symmetricEncrypter = new SymmetricEncrypter();
    }
    
    @Override
    public void run() {
        try (BufferedReader listener = new BufferedReader(
                new InputStreamReader(connector.getInputStream()))) {
            String message;
            System.out.println("listening..."); // TODO remove
            
            while ((message = listener.readLine()) != null) {
                System.out.println("From listener: " + message); // TODO remove
                
                String[] data = message.split(DELIMITER);
                byte[] key = mainAppListener.passwordArrived();
                
                System.out.println("Pass: " + data[0]);
                System.out.println("IV: " + data[1]);
                
                String pass = symmetricEncrypter.decrypt(data[0], key, data[1]);
                
                if(!formFiller.fillFocusedForm(pass)) {
                    Messenger.showErrorMessage("There was a problem during "
                            + "password entering.", null);
                }
                else mainAppListener.passwordFilled();
            }

        } catch (Exception ex) {
            Logger.getLogger(FormFillListener.class.getName()).log(Level.SEVERE, null, ex); // TODO fix
            // TODO watch out on SocketException when closing socket
        }
    }
}
