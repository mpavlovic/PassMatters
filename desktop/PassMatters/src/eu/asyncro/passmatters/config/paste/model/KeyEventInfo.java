package eu.asyncro.passmatters.config.paste.model;

/**
 * Contains information about keyboard events.
 * 
 * @author Milan
 */
public class KeyEventInfo {
    
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
