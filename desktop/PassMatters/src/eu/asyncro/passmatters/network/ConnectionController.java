/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;
/**
 * This is abstract class used for managing transport layer connection 
 * between this client application an remote server.
 * @author Milan
 */
public abstract class ConnectionController {

    protected final String HOST = "passmatters.eu";
    protected final int PORT = 1337;
    protected final int SECURE_PORT = 8000;
    
    protected volatile boolean listening = false; // TODO do i need it ?
    
    /**
     * Opens a connection to remote server. Any transport layer protocol 
     * (TCP or UDP) can be used.
     * @return true if connection opened successfully, false otherwise
     * @throws Exception 
     */
    public abstract boolean openConnection() throws Exception;
    
    /**
     * Sends raw String data to the remote server via opened connection. 
     * @param data String for sending to the remote server
     * @return String which can be for example, response from remote server.
     * @throws Exception 
     */
    public abstract String sendData(String data) throws Exception;
    
    /**
     * Starts listening for incoming (asynchronous) messages from remote server.
     * @throws java.lang.Exception
     */
    public abstract void startListening() throws Exception;
    
    /**
     * Closes opened connection to the remote server.
     * @return true if connection was closed successfully, false otherwise
     * @throws Exception 
     */
    public abstract boolean closeConnection() throws Exception;
    
    
}
