/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network.authentication.controller;

import eu.asyncro.passmatters.main.controller.MainAppListener;
import eu.asyncro.passmatters.network.Client;
import eu.asyncro.passmatters.network.ConnectionController;
import eu.asyncro.passmatters.network.HTTPSClient;
import eu.asyncro.passmatters.network.JsonAdapter;
import eu.asyncro.passmatters.network.Protocol;
import eu.asyncro.passmatters.network.TCPSocketConnectionController;
import eu.asyncro.passmatters.network.WebServiceResultHandler;
import eu.asyncro.passmatters.network.authentication.model.User;
import eu.asyncro.passmatters.network.authentication.view.LoginFrame;
import eu.asyncro.passmatters.security.EncryptionKeyGenerator;
import eu.asyncro.passmatters.util.Messenger;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 * This controller class contains logic for performing network login and logout.
 * @author Milan
 */
public class AuthenticationController implements Loginer, Logouter {
    
    private final String LOGIN_URL = "https://www.passmatters.eu/api/login";
    private final String LOGOUT_URL = "https://www.passmatters.eu/api/logout";
    
    private LoginFrame loginFrame;
    private Client client;
    private ConnectionController connectionController;
    private final MainAppListener mainAppListener;
    private boolean isUserLoggedIn = false;
    private String token;
    private byte[] secretKey;
    
    /**
     * Creates new instance of this class.
     * @param mainAppListener MainAppListener for registering on events.
     */
    public AuthenticationController(MainAppListener mainAppListener) {
        this.mainAppListener = mainAppListener;
        initailize();
    }
    
    /**
     * Initializes class members. 
     */
    private void initailize() {
        loginFrame = new LoginFrame();
        loginFrame.setLoginer(this);
        
        client = new HTTPSClient(Client.REQUEST_POST);
        connectionController = new TCPSocketConnectionController(mainAppListener);
    }
    
    /**
     * Starts the user login by showing login frame.
     */
    public void startLogin() {
        loginFrame.showFrame();
    }

    /**
     * Checks whether is user logged in or not.
     * @return <code>true</code> if user is logged in, <code>false</code> otherwise
     */
    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    /**
     * Returns generated secret key for password decryption.
     * @return secret key bytes 
     */
    public byte[] getSecretKey() {
        return secretKey;
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
                
                secretKey = EncryptionKeyGenerator.generateSecretKey(user.getPassword(), null);
                
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
                        loginFrame.dispose();
                        mainAppListener.loginFinished();
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

    /**
     * Fetches the login token from remote server. 
     * Token is used for authentication after TCP connection is established.
     * @param username user's username 
     * @param password user's password
     * @return assigned temporary token
     * @throws Exception 
     */
    private String getLoginToken(String username, String password) 
            throws Exception 
    {
        Hashtable<String, String> parameters = new Hashtable<>();
        parameters.put("username", username);
        parameters.put("password", password);
        client.createRequest(LOGIN_URL, parameters);
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
    
    /**
     * Sends the given login token to the remote server via sockets 
     * for authentication.
     * @param token obtained token after http authentication
     * @return true if user is authenticated with good token, false otherwise
     * @throws Exception 
     */
    private boolean authenticateOnServer(String token) throws Exception {
        String authString = JsonAdapter.getAuthJsonString(token);
        String authResult = connectionController.sendData(authString);
        return authResult.equals(Protocol.AUTHENTICATED);
    }
    
    /**
     * Invalidates the current user's token. 
     * This method is used when logout is performed.
     * @return true if token was invalidated successfully, false otherwise
     * @throws Exception 
     */
    private boolean invalidateToken() 
            throws Exception 
    {
        Hashtable<String, String> params = new Hashtable<>();
        params.put("token", token);
        client.createRequest(LOGOUT_URL, params);
        String response = client.sendRequest();
        System.out.println(response); // TODO remove
        return response.equals(Protocol.USER_LOGGED_OUT);
    }

    @Override
    public void logout() {
        
        new SwingWorker<Boolean, Object>() {

            @Override
            protected Boolean doInBackground() throws Exception {
                System.out.println("tcp logout started"); // TODO remove
                if(!connectionController.closeConnection()) return false;
                System.out.println("tcp logout finished"); // TODO remove
                
                return invalidateToken();
            }

            @Override
            protected void done() {
                try {
                    super.done();
                    if(get()) {
                        System.out.println("FINISH"); // TODO remove
                        isUserLoggedIn = false;
                        mainAppListener.logoutFinished();
                    }
                    else {
                        Messenger.showErrorMessage("Unexpected logout problem occured.", null);
                    }
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex); //  TODO remove
                    Messenger.showErrorMessage("Unexpected logout exception occured.", null);
                }
            }
            
        }.execute();
    }
}
