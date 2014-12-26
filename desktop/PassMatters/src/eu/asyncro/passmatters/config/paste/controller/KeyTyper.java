/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.config.paste.controller;

import eu.asyncro.passmatters.config.paste.model.KeyEventInfo;
import java.util.ArrayList;

/**
 * Contains method(s) for simulating keyboard key typing.
 * @author Milan
 */
public interface KeyTyper {

    /**
     * Simulates given keystrokes. 
     * This method accepts list of keys to be typed. 
     * @param keys Keys to be typed.
     * @param typeEnterKey If true, the enter key will be typed last.
     * @throws Exception
     */
    public void typeKeys(ArrayList<KeyEventInfo> keys, boolean typeEnterKey) throws Exception;
}
