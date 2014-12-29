/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Establishes connection and communicates with 
 * remote host via SSL over the TCP protocol.
 * @author Milan
 */
public class SSLConnector implements Connector {

    private String host;
    private int sslPort;
    private SSLSocket clientSocket;
    
    /**
     * Creates a SSLConnector object which communicates 
     * with remote host server via SLL socket.
     * @param host the host name
     * @param sslPort the SSL port number
     * @throws IOException 
     */
    public SSLConnector(String host, int sslPort) throws IOException 
    {
        this.host = host;
        this.sslPort = sslPort;
//        Security.addProvider(new Provider()); // ??
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        clientSocket = (SSLSocket) sslSocketFactory.createSocket(host, sslPort);
    }
    
    @Override
    public OutputStream getOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return clientSocket.getInputStream();
    }

    @Override
    public void close() throws IOException {
        clientSocket.close();
    }

    @Override
    public boolean checkServerIdentity() {
        return true; // for now...
    }
    
}
