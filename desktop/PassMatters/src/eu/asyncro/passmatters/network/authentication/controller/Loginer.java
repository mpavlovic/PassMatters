/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network.authentication.controller;

import eu.asyncro.passmatters.network.authentication.model.User;

/**
 * This interface contains method declarations for login functionality. 
 * @author Milan
 */
public interface Loginer {
    
    /**
     * Submits the user credentials to the remote server.
     * @param user User object which encapsulates login credentials
     */
    public void submit(User user);
}
