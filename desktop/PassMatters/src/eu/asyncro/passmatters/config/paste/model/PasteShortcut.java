/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.config.paste.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model class for user's keyboard shortcut for paste option.
 * @author Milan
 */
public class PasteShortcut implements Serializable {
    private ArrayList<KeyEventInfo> keyEvents;

    /**
     * Constructor.
     * @param keyEvents ArrayList containing KeyEventinfo objects 
     * with information about recorded key events
     */
    public PasteShortcut(ArrayList<KeyEventInfo> keyEvents) {
        this.keyEvents = keyEvents;
    }
    
    /**
     * Sets recored key events for user's keyboard paste option shortcut. 
     * @param keyEvents ArrayList of KeyEventInfo objects 
     */
    public void setKeyEvents(ArrayList<KeyEventInfo> keyEvents) {
        this.keyEvents = keyEvents;
    }

    /**
     * Returns recored key events for user's keyboard paste option shortcut.
     * @return ArrayList of KeyEventInfo objects
     */
    public ArrayList<KeyEventInfo> getKeyEvents() {
        return keyEvents;
    }
    
}
