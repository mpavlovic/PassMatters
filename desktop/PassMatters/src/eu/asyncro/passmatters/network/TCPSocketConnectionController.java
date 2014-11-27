/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Maintains raw TCP connection to remote server via TCP socket.
 * @author Milan
 */
public class TCPSocketConnectionController extends ConnectionController {
    
    private Socket clientSocket;
    private DataOutputStream outputToServer;
    private BufferedReader inputFromServer;

    @Override
    public void openConnection() throws IOException 
    {
        clientSocket = new Socket(IP_ADDRESS, PORT);
        openStreams();

        String messageFromServer;
        messageFromServer = inputFromServer.readLine();
        System.out.println(messageFromServer);
    }
    
    private void openStreams() throws IOException 
    {
        outputToServer = new DataOutputStream(clientSocket.getOutputStream());
        inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.
                getInputStream()));
    }
    
    @Override
    public void sendData(String data) throws IOException 
    {
        outputToServer.flush();
        outputToServer.write(data.getBytes());

        // TODO - waiting for message ??
        String messageFromServer;
        while((messageFromServer = inputFromServer.readLine()) != null) {
            System.out.println(messageFromServer);
            // TODO handle message
        }   
    }
    
    /**
     * Closes the TCP connection with remote server. 
     * @throws IOException 
     */
    @Override
    public void closeConnection() throws IOException 
    {
        clientSocket.close();
    }

}
