/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Establishes connection and communicates with 
 * remote host via TCP protocol.
 * 
 * @author Milan
 */
public class TCPConnector implements Connector {
    
    private String host;
    private int port;
    private Socket clientSocket;
    
    /**
     * Creates a TCPConnector object which communicates 
     * with remote host server via pure TCP socket.
     * @param host the host name
     * @param port the port number
     * @throws IOException 
     */
    public TCPConnector(String host, int port) throws IOException 
    {
        this.host = host;
        this.port = port;
        clientSocket = new Socket(host, port);
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
        return true;
    }
    
}
