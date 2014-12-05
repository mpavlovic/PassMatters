/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network.authentication.controller;

import eu.asyncro.passmatters.network.authentication.model.User;

/**
 *
 * @author Milan
 */
public interface Loginer {
    public void submit(User user);
}
