/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import eu.asyncro.passmatters.main.MainAppListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Maintains raw TCP connection to remote server via TCP socket.
 * @author Milan
 */
public class TCPSocketConnectionController extends ConnectionController {
    
    private Connector connector;
    private DataOutputStream outputToServer;
    private BufferedReader inputFromServer;
    private MainAppListener mainAppListener;
    
    public TCPSocketConnectionController(MainAppListener mainAppListener) {
        this.mainAppListener = mainAppListener;
    } 

    @Override
    public boolean openConnection() throws IOException 
    {
        
        connector = new TCPConnector(IP_ADDRESS, PORT);
        openStreams();

        String messageFromServer;
        messageFromServer = inputFromServer.readLine();
        return messageFromServer.equals(Protocol.CONNECTED);
    }
    
    private void openStreams() throws IOException 
    {
        outputToServer = new DataOutputStream(connector.getOutputStream());
        inputFromServer = new BufferedReader(new InputStreamReader(connector.
                getInputStream()));
    }
    
    @Override
    public String sendData(String data) throws IOException 
    {
        outputToServer.flush();
        outputToServer.write(data.getBytes());

        String messageFromServer = null;
        if(!data.equals(Protocol.LOGOUT)) {
            messageFromServer = inputFromServer.readLine();
        }
        
        return messageFromServer;
    }
    
    /**
     * Closes the TCP connection with remote server. 
     * @throws IOException 
     */
    @Override
    public boolean closeConnection() throws IOException 
    {
        sendData(Protocol.LOGOUT);
        System.out.println("before close"); // TODO remove
        connector.close();
        System.out.println("after close"); // TODO remove
        return true;
    }
    
    @Override
    public void startListening() {
        FormFillListener formFillListener = 
                new FormFillListener(connector, mainAppListener);
        formFillListener.start();
    }
    
 
}
