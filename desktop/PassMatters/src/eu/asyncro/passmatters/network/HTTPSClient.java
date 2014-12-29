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
import javax.net.ssl.HttpsURLConnection;

/**
 * Class for sending HTTPS requests to a remote server.
 * @author Milan
 */
public class HTTPSClient extends Client {
    
    private HttpsURLConnection connection;

    public HTTPSClient(String urlString, String requestMethod) {
        super(urlString, requestMethod);
    }

    @Override
    public String sendRequest() throws Exception {
        openUrlConnection();
        
        if(requestMethod.equals(Client.REQUEST_POST)) {
            writeParametersBytesToServer();
        }

        InputStreamReader isr = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        
        String responseLine;
        StringBuilder responseBuilder = new StringBuilder();
        
        while((responseLine = br.readLine()) != null) {
            responseBuilder.append(responseLine);
            //System.out.print(responseLine); // TODO remove
        }
        
        br.close();
        return responseBuilder.toString();
    }

    private void openUrlConnection() throws IOException 
    {
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    private void writeParametersBytesToServer() throws IOException 
    {
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(parametersString);
        dos.close();
    }
    
    
}
