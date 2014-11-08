package eu.asyncro.passmatters.config.paste.controller;

import eu.asyncro.passmatters.config.paste.model.KeyEventInfo;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Used for keyboard events recording.
 * It can be set as KeyListener if needed.
 * 
 * @author Milan
 */
public class KeyEventRecorder extends KeyAdapter {
    
    private ArrayList<KeyEventInfo> recordedKeyEvents = new ArrayList<>();
    
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
    
    public void addKeyEventInfo(KeyEvent e, int type) {
        if(type == KeyEvent.KEY_PRESSED) {
            recordedKeyEvents.add(new KeyEventInfo(KeyEvent.KEY_PRESSED, e.getKeyCode()));
        }
        else if(type == KeyEvent.KEY_RELEASED) {
            recordedKeyEvents.add(new KeyEventInfo(KeyEvent.KEY_RELEASED, e.getKeyCode()));
        }
    }
    
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
    
    public void clear() {
        recordedKeyEvents.clear();
    }

    public ArrayList<KeyEventInfo> getRecordedKeyEvents() {
        return recordedKeyEvents;
    }
    
}
