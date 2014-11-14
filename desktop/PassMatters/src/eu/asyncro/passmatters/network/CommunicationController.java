/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

/**
 *
 * @author Milan
 */
public class CommunicationController implements Communicator {

    private final String SERVER_IP_ADDRESS = "";
    private final int SERVER_PORT = 5000;
    
    // Socket objekti
    // SSL Soket objekti
    
    @Override
    public boolean connect() {
        return true;
    }

    @Override
    public boolean disconnect() {
        return false;
    }
    
    private boolean connectViaSocket() {
        
    } 
    
}
