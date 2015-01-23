package eu.asyncro.passmatters.dao;

import static eu.asyncro.passmatters.util.Constants.*;
import eu.asyncro.passmatters.config.paste.model.PasteShortcut;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains CRUD methods for working with PasteShortcut objects. 
 * 
 * @see PasteShortcutDAO
 * @see PasteShortcut
 * @author Milan
 */
public class FilePasteShortcutDAO implements PasteShortcutDAO {

    @Override
    public PasteShortcut getPasteShortcut() {
        PasteShortcut pasteShortcut = null;
        
        File pasteShortcutFile = new File(PASTE_SHORTCUT_FULL_PATH);
        
        try(ObjectInputStream ois = 
                new ObjectInputStream(new FileInputStream(pasteShortcutFile)))
        {
            pasteShortcut = (PasteShortcut) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FilePasteShortcutDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return pasteShortcut;
    }

    @Override
    public boolean savePasteShortcut(PasteShortcut pasteShortcut) {
        boolean result = true;
        File appDirectory = new File(FULL_APP_DIRECTORY);
        if(!appDirectory.exists()) {
            if(!appDirectory.mkdirs()) return false;
        }
        File pasteShortcutFile = new File(PASTE_SHORTCUT_FULL_PATH);
        try(ObjectOutputStream oos = 
                new ObjectOutputStream(new FileOutputStream(pasteShortcutFile))) 
        {
            oos.writeObject(pasteShortcut);
        } catch (IOException ex) {
            result = false;
        }
        return result;
    }
    
}
