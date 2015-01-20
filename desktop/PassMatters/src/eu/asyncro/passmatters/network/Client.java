/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Abstract class for sending HTTP or HTTPS requests.
 * @author Milan
 */
public abstract class Client {
    
    public static final String REQUEST_GET = "GET";
    public static final String REQUEST_POST = "POST";    
    
    protected String urlString;
    protected String requestMethod;
    protected String parametersString;
    protected Hashtable<String, String> parametersTable;
    protected URL url;
    
    private final String ENCODING = "UTF-8";

    /**
     * Constructor - creates new instance of child class.
     * @param requestMethod Type of request method which will be used 
     * for sending requests with concrete instance of this class. This 
     * is the <code>public static</code> member of this class.
     */
    public Client(String requestMethod) {
        this.requestMethod = requestMethod;
    }
    
    
    /**
     * Constructor. 
     * @param urlString target URL for sending requests
     * @param requestMethod request method (Client.REQUEST_GET or Client.REQUEST_POST)
     * @param parameters java.util.Hashtable with request parameters
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException 
     */
    public Client(String urlString, String requestMethod, Hashtable<String, String> parameters) 
            throws MalformedURLException, UnsupportedEncodingException 
    {
        this.urlString = urlString;
        this.requestMethod = requestMethod;
        this.parametersTable = parameters;
        finalizeURLAndParameters();
    }
    
    /**
     * Sends request to the specified URL with given parameters.
     * @return HTTP(S) response from remote server
     * @throws Exception 
     */
    public abstract String sendRequest() throws Exception;
    
    /**
     * Creates request which will be sent to the given urlString with given
     * parameters.
     * @param urlString target URL for sending requests
     * @param parameters java.util.Hashtable with request parameters
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException 
     */
    public void createRequest(String urlString, Hashtable<String, String> parameters) 
            throws UnsupportedEncodingException, MalformedURLException 
    {
        this.urlString = urlString;
        this.parametersTable = parameters;
        finalizeURLAndParameters();
    }
    
    /**
     * Formats given parameters to string and concatenates it to specified URL
     * in case of GET request.
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException 
     */
    private void finalizeURLAndParameters() 
            throws UnsupportedEncodingException, MalformedURLException 
    {
        formatStringParameters();
        formatUrl();
    }
    
    /**
     * Creates string with URL parameters from parameters hash table.
     * Name - value parameter pairs are delimited wit '&' sign.
     * @throws UnsupportedEncodingException 
     */
    private void formatStringParameters() throws 
            UnsupportedEncodingException 
    {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = parametersTable.keySet().iterator();
        while(iterator.hasNext()) {
            String paramName = iterator.next();
            String paramValue = parametersTable.get(paramName);
            sb.append(paramName);
            sb.append("=");
            sb.append(URLEncoder.encode(paramValue, ENCODING));
            if(iterator.hasNext()) {
                sb.append("&");
            }
        }
        this.parametersString = sb.toString();
    }
    
    /**
     * Creates final string with URL and possible parameters 
     * and sends it to the new java.net.URL instance as
     * a constructor parameter. If Client is used for GET requests, URL has
     * parameters at the end. Parameter part is delimited with '?' sign. 
     * @throws MalformedURLException 
     */
    private void formatUrl() throws MalformedURLException {
        StringBuilder finalURLBuilder = new StringBuilder();
        finalURLBuilder.append(urlString);
        if(requestMethod.equals(REQUEST_GET)) {
            finalURLBuilder.append("?");
            finalURLBuilder.append(parametersString);
        }
        this.url = new URL(finalURLBuilder.toString());
    }

}
