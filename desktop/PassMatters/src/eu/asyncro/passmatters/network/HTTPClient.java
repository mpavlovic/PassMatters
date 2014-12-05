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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Hashtable;

/**
 * Class for sending HTTP requests to a remote HTTP server.
 * @author Milan
 */
public class HTTPClient extends Client {
    
    private HttpURLConnection connection;

    public HTTPClient(String url, String requestMethod, 
            Hashtable<String, String> parameters) throws MalformedURLException, 
            UnsupportedEncodingException 
    {
        super(url, requestMethod, parameters);
    }

    public HTTPClient(String urlString, String requestMethod) {
        super(urlString, requestMethod);
    }
    
    @Override
    public String sendRequest() throws IOException {
        // TODO in new function
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        
        // TODO in new function
        if(requestMethod.equals(Client.REQUEST_POST)) {
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(parametersString);
            dos.close();
        }

        InputStreamReader isr = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        
        String responseLine;
        StringBuilder responseBuilder = new StringBuilder();
        
        while((responseLine = br.readLine()) != null) {
            responseBuilder.append(responseLine);
            //System.out.print(responseLine); // TODO remove line
        }
        
        br.close();
        return responseBuilder.toString();
    }
    
}
