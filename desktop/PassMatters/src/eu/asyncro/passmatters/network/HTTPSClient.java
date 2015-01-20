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

     /**
     * Constructor.
     * @param requestMethod request method type (public static member of Client)
     */
    public HTTPSClient(String requestMethod) {
        super(requestMethod);
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

    /**
     * Opens HTTP connection to the remote URL
     * @see public URLConnection openConnection() in java.net.URL
     * @throws IOException 
     */
    private void openUrlConnection() throws IOException 
    {
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    /**
     * Used for writing parameters to the opened URL connection 
     * through DataOutputStream. This method is used when this Client needs to 
     * send a HTTP POST request. 
     * @throws IOException 
     */
    private void writeParametersBytesToServer() throws IOException 
    {
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.writeBytes(parametersString);
        dos.close();
    }
    
    
}
