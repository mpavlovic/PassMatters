/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.config.paste.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Milan
 */
public class PasteShortcut implements Serializable {
    private ArrayList<KeyEventInfo> keyEvents;

    public PasteShortcut(ArrayList<KeyEventInfo> keyEvents) {
        this.keyEvents = keyEvents;
    }
    
    public void setKeyEvents(ArrayList<KeyEventInfo> keyEvents) {
        this.keyEvents = keyEvents;
    }

    public ArrayList<KeyEventInfo> getKeyEvents() {
        return keyEvents;
    }
    
}
