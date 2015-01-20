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
 * Class for sending HTTP requests to a remote server.
 * @author Milan
 */
public class HTTPClient extends Client {
    
    private HttpURLConnection connection;

    /**
     * Constructor.
     * @param url URL destination for sending requests.
     * @param requestMethod request method type (public static member of Client)
     * @param parameters java.util.Hashtable of name-value parameter pairs
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException 
     */
    public HTTPClient(String url, String requestMethod, 
            Hashtable<String, String> parameters) 
            throws MalformedURLException, UnsupportedEncodingException 
    {
        super(url, requestMethod, parameters);
    }

    /**
     * Constructor.
     * @param requestMethod request method type (public static member of Client)
     */
    public HTTPClient(String requestMethod) {
        super(requestMethod);
    }
    
    @Override
    public String sendRequest() throws IOException {
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
        connection = (HttpURLConnection) url.openConnection();
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
