/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network.authentication.model;

/**
 * Model class for user's credentials needed for login action.
 * @author Milan
 */
public class User {
    
    private String username;
    private String password;

    /**
     * Creates new instance of this class with entered 
     * username and password for the current user.
     * @param username user's username
     * @param password user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }    
    
    /**
     * Returns the provided username.
     * @return user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the entered user's username.
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the provided password.
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the entered user's password.
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
