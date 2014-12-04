/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network.authentication.controller;

import eu.asyncro.passmatters.main.MainAppListener;
import eu.asyncro.passmatters.network.Client;
import eu.asyncro.passmatters.network.ConnectionController;
import eu.asyncro.passmatters.network.HTTPClient;
import eu.asyncro.passmatters.network.JsonAdapter;
import eu.asyncro.passmatters.network.Protocol;
import eu.asyncro.passmatters.network.TCPSocketConnectionController;
import eu.asyncro.passmatters.network.WebServiceResultHandler;
import eu.asyncro.passmatters.network.authentication.model.User;
import eu.asyncro.passmatters.network.authentication.view.LoginFrame;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author Milan
 */
public class AuthenticationController implements Loginer, Logouter {
    
    private final String LOGIN_URL = "http://178.62.212.164/api/login";
    private final String LOGOUT_URL = "http://178.62.212.164/api/logout";
    
    private LoginFrame loginFrame;
    private Client client;
    private ConnectionController connectionController;
    private MainAppListener mainAppListener;
    private boolean isUserLoggedIn = false;
    private String token;
    
    public AuthenticationController() {
        initailize();
    }

    public AuthenticationController(MainAppListener mainAppListener) {
        this();
        this.mainAppListener = mainAppListener;
    }
    
    private void initailize() {
        loginFrame = new LoginFrame();
        loginFrame.setLoginer(this);
        
        client = new HTTPClient(LOGIN_URL, Client.REQUEST_POST);
        connectionController = new TCPSocketConnectionController();
    }
    
    public void startLogin() {
        loginFrame.showFrame();
    }

    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }
    
    @Override
    public void submit(final User user) {       
        new SwingWorker<Boolean, Object>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                boolean result = true;
                token = getLoginToken(user.getUsername(), user.getPassword());
                
                if(token == null) return false;
                
                if(!connectionController.openConnection()) return false;
                
                if(!authenticateOnServer(token)) return false;
                
                connectionController.startListening();
                
                return result;
            }

            @Override
            protected void done() {
                super.done();
                try {
                    boolean result = get();
                    if(result) {
                        System.out.println("logged in"); // TODO maknuti
                        isUserLoggedIn = true;
                        mainAppListener.loginFinished();
                        loginFrame.dispose();
                        // obavijestiti app o main windowu i prikazati ga
                    }
                    else {
                        loginFrame.setLoginFailed();
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                    // TODO messenger
                }
            }
            
            
        }.execute();
    }

    private String getLoginToken(String username, String password) 
            throws Exception 
    {
        Hashtable<String, String> parameters = new Hashtable<>();
        parameters.put("username", username);
        parameters.put("password", password);
        client.setParameters(parameters);
        String loginResponse = client.sendRequest();
        return (String) loginResponseHandler.handleResult(loginResponse);
    }
    
    WebServiceResultHandler loginResponseHandler = new WebServiceResultHandler() {
        @Override
        public Object handleResult(String result) {
            if(result.equals(Protocol.BAD_CREDENTIALS)) {
                return null;
            }
            else {
                return JsonAdapter.getToken(result);
            }
        }
    };
    
    private boolean authenticateOnServer(String token) throws Exception {
        String authString = JsonAdapter.getAuthJsonString(token);
        String authResult = connectionController.sendData(authString);
        return authResult.equals(Protocol.AUTHENTICATED);
    }
    
    private boolean invalidateToken() 
            throws Exception 
    {
        Hashtable<String, String> params = new Hashtable<>();
        params.put("token", token);
        client.setUrl(LOGOUT_URL);
        client.setParameters(params);
        String response = client.sendRequest();
        System.out.println(response); // TODO remove
        return response.equals(Protocol.USER_LOGGED_OUT);
    }

    @Override
    public void logout() throws Exception {
        
        new SwingWorker<Boolean, Object>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                System.out.println("tcp logout started"); // TODO remove
                if(!connectionController.closeConnection()) return false;
                System.out.println("tcp logout finished"); // TODO remove
                
                if(!invalidateToken()) return false;
                
                return true;
            }

            @Override
            protected void done() {
                try {
                    super.done();
                    if(get()) {
                        System.out.println("FINISH"); // TODO remove
                        isUserLoggedIn = false;
                        // TODO inform main controller about finished logout
                    }
                    else {
                        System.out.println("logout problem"); // TODO change
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
                    // TODO message ?
                }
            }
            
        }.execute();
        
        
        
    }
    
    
}
