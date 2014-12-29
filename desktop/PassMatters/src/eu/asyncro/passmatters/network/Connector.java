/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface contains wrapper methods for implementing objects which 
 * contains java.net.Socket or javax.net.ssl.SSLSocket objects as a 
 * part of composition association.
 * They enable communicating with remote servers via pure TCP or SSL client sockets.
 * 
 * @author Milan
 * @see java.net.Socket
 * @see javax.net.ssl.SSLSocket
 */
public interface Connector {
    
    /**
     * Returns an output stream of associated Socket.
     * @return an output stream for writing bytes from implementing Socket
     * @throws IOException
     * @see java.net.Socket
     */
    public OutputStream getOutputStream() throws IOException;
    
    /**
     * Returns an input stream of associated Socket.
     * @return an input stream for reading bytes from implementing Socket
     * @throws IOException
     * @see java.net.Socket
     */
    public InputStream getInputStream() throws IOException;
    
    /**
     * Closes associated socket.
     * @throws IOException 
     * @see java.net.Socket
     */
    public void close() throws IOException;
    
    /**
     * Checks the identity of server to which 
     * the connection is established.
     * @return true if server identity is valid, false otherwise
     */
    public boolean checkServerIdentity();
    
}
