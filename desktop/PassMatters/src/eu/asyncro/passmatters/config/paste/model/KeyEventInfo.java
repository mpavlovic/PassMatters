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
    
    /**
     * Returns KeyEvent type of this key event.
     * @return integer representing KeyEvent type
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * Returns KeyEvent key code of this key event.
     * @return integer representing KeyEvent key code.
     */
    public int getKeyCode() {
        return keyCode;
    }
}
