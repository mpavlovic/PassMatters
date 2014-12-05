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

    public Client(String urlString, String requestMethod) {
        this.urlString = urlString;
        this.requestMethod = requestMethod;
    }
    
    public Client(String urlString, String requestMethod, Hashtable<String, String> parameters) 
            throws MalformedURLException, UnsupportedEncodingException 
    {
        this.urlString = urlString;
        this.requestMethod = requestMethod;
        this.parametersTable = parameters;
        finalizeURLParameters();
    }
    
    public abstract String sendRequest() throws Exception;

    public void setParameters(Hashtable<String, String> parameters) 
            throws UnsupportedEncodingException, MalformedURLException 
    {
        this.parametersTable = parameters;
        finalizeURLParameters();
    }

    public void setUrl(String urlString) 
            throws UnsupportedEncodingException, MalformedURLException 
    {
        this.urlString = urlString;
        finalizeURLParameters();
    }
    
    private void finalizeURLParameters() 
            throws UnsupportedEncodingException, MalformedURLException 
    {
        formatStringParameters();
        formatUrl();
    }
    
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
