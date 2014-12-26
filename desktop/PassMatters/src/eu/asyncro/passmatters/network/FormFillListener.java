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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milan
 */
public class FormFillListener extends Thread {

    private Socket clientSocket;
    private FormFiller formFiller;

    public FormFillListener(Socket clientSocket, MainAppListener mainAppListener) {
        this.clientSocket = clientSocket;
        formFiller = new FormFiller(mainAppListener);
    }
    
    @Override
    public void run() {
        try (BufferedReader listener = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()))) {
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
        }
    }
}
