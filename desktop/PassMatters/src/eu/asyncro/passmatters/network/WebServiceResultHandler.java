/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.network;

/**
 * Interface containing method for handling result (response) string from
 * a web service (or remote server).
 * @author Milan
 */
public interface WebServiceResultHandler {
    public Object handleResult(String result);
}
