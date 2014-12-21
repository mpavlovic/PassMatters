package eu.asyncro.passmatters.config.paste.controller;

import eu.asyncro.passmatters.config.paste.model.KeyEventInfo;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Used for keyboard events recording.
 * It can be set as KeyListener if needed.
 * 
 * @author Milan
 */
public class KeyEventRecorder extends KeyAdapter implements KeyTyper {
    
    protected ArrayList<KeyEventInfo> recordedKeyEvents = new ArrayList<>();
    
    /**
     * Executes every time when keyboard key is released.
     * @param e KeyEvent for key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
        addKeyEventInfo(e, KeyEvent.KEY_RELEASED);
    }
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e); //To change body of generated methods, choose Tools | Templates.
        addKeyEventInfo(e, KeyEvent.KEY_PRESSED);
    }
    
    /**
     * Adds fired KeyEvent to internal list of recorded key events.
     * @param e fired KeyEvent
     * @param type KeyEvent.KEY_PRESSED or KeyEvent.KEY_RELEASED
     */
    public void addKeyEventInfo(KeyEvent e, int type) {
        if(type == KeyEvent.KEY_PRESSED) {
            recordedKeyEvents.add(new KeyEventInfo(KeyEvent.KEY_PRESSED, e.getKeyCode()));
        }
        else if(type == KeyEvent.KEY_RELEASED) {
            recordedKeyEvents.add(new KeyEventInfo(KeyEvent.KEY_RELEASED, e.getKeyCode()));
        }
    }
    
    /**
     * Simulates recorded key strokes. 
     * The effect is as same as it wolud be if user types keyboard keys.
     * @throws AWTException 
     */
    public void typeRecordedKeys() throws AWTException {
        Robot robot = new Robot();
        
        for(KeyEventInfo info : recordedKeyEvents) {
            if(info.getEventType() == KeyEvent.KEY_PRESSED) {
                robot.keyPress(info.getKeyCode());
            }
            else if(info.getEventType() == KeyEvent.KEY_RELEASED) {
                robot.keyRelease(info.getKeyCode());
            }
        }
    }
    
    /**
     * Clears all recorded key events.
     */
    public void clear() {
        recordedKeyEvents.clear();
    }
    
    /**
     * Resturns recorded key events.
     * @return recorded key events ArrayList.
     */
    public ArrayList<KeyEventInfo> getRecordedKeyEvents() {
        return recordedKeyEvents;
    }

    /**
     * Simulates given keystrokes. 
     * This method accepts list of keys to be typed. 
     * It doesn't use keys which are currently saved inside this class.
     * @param keys Keys to be typed.
     * @param typeEnterKey If true, the enter key will be typed last.
     * @throws AWTException 
     */
    @Override
    public void typeKeys(ArrayList<KeyEventInfo> keys, boolean typeEnterKey) 
            throws AWTException 
    {
        Robot robot = new Robot();
        
        for(KeyEventInfo info: keys) {
            if(info.getEventType() == KeyEvent.KEY_PRESSED) {
                robot.keyPress(info.getKeyCode());
            }
            else if(info.getEventType() == KeyEvent.KEY_RELEASED) {
                robot.keyRelease(info.getKeyCode());
            }
        }
        
        if(typeEnterKey) {
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
    }
    
}
