package eu.asyncro.passmatters.config.paste.model;

import java.io.Serializable;

/**
 * Contains information about keyboard events.
 * 
 * @author Milan
 */
public class KeyEventInfo implements Serializable {
    
    private int eventType;
    private int keyCode;

    public KeyEventInfo(int eventType, int keyCode) {
        this.eventType = eventType;
        this.keyCode = keyCode;
    }

    public int getEventType() {
        return eventType;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
