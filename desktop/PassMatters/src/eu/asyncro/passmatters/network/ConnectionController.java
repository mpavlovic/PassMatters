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
public abstract class ConnectionController {

    protected final String IP_ADDRESS = "178.62.212.164";
    protected final int PORT = 1337;
    
    protected volatile boolean listening = false; // do i need it ?
    
    public abstract boolean openConnection() throws Exception;
    public abstract String sendData(String data) throws Exception;
    public abstract void startListening();
    public abstract boolean closeConnection() throws Exception;
    
    
}
