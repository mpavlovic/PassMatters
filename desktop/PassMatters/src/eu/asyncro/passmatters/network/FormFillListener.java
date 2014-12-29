/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import eu.asyncro.passmatters.main.MainAppListener;
import eu.asyncro.passmatters.util.FormFiller;
import eu.asyncro.passmatters.util.Messenger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milan
 */
public class FormFillListener extends Thread {

    private FormFiller formFiller;
    private Connector connector;

    public FormFillListener(Connector connector, MainAppListener mainAppListener) {
        this.connector = connector;
        formFiller = new FormFiller(mainAppListener);
    }
    
    @Override
    public void run() {
        try (BufferedReader listener = new BufferedReader(
                new InputStreamReader(connector.getInputStream()))) {
            String message;
            System.out.println("listening..."); // TODO remove
            
            while ((message = listener.readLine()) != null) {
                System.out.println("From listener: " + message); // TODO remove
                // TODO decryption
                formFiller.fillFocusedForm(message);
            }

        } catch (Exception ex) {
            Logger.getLogger(FormFillListener.class.getName()).log(Level.SEVERE, null, ex); // TODO fix
            Messenger.showErrorMessage("There was a problem in network listener:" + ex.getMessage(), null);
            // watch out on SocketException when closing socket
        }
    }
}
