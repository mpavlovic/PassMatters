/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.config.paste.controller;

import eu.asyncro.passmatters.config.paste.model.KeyEventInfo;
import java.util.ArrayList;

/**
 *
 * @author Milan
 */
public interface KeyTyper {
    void typeKeys(ArrayList<KeyEventInfo> keys, boolean typeEnterKey) throws Exception;
}
