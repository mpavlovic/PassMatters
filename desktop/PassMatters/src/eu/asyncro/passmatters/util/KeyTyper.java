/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.util;

import eu.asyncro.passmatters.config.paste.controller.KeyEventRecorder;
import eu.asyncro.passmatters.config.paste.model.KeyEventInfo;
import java.util.ArrayList;

/**
 *
 * @author Milan
 */
public class KeyTyper extends KeyEventRecorder { // TODO should KER be in it's current package ?

    public KeyTyper(ArrayList<KeyEventInfo> keysToType) {
        recordedKeyEvents = keysToType;
    }
    
}
