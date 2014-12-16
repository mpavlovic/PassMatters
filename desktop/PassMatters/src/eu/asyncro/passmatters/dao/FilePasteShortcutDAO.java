package eu.asyncro.passmatters.dao;

import static eu.asyncro.passmatters.util.Constants.PASTE_SHORTCUT_FILE_NAME;
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
 *
 * @author Milan
 */
public class FilePasteShortcutDAO implements PasteShortcutDAO {

    @Override
    public PasteShortcut getPasteShortcut() {
        PasteShortcut pasteShortcut = null;
        
        File pasteShortcutFile = new File(PASTE_SHORTCUT_FILE_NAME);
        
        try(ObjectInputStream ois = 
                new ObjectInputStream(new FileInputStream(pasteShortcutFile)))
        {
            pasteShortcut = (PasteShortcut) ois.readObject();
        }
        catch (IOException ex) {
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilePasteShortcutDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pasteShortcut;
    }

    @Override
    public boolean savePasteShortcut(PasteShortcut pasteShortcut) {
        boolean result = true;
        File pasteShortcutFile = new File(PASTE_SHORTCUT_FILE_NAME);
        try(ObjectOutputStream oos = 
                new ObjectOutputStream(new FileOutputStream(pasteShortcutFile))) 
        {
            oos.writeObject(pasteShortcut);
        } catch (IOException ex) {
            // TODO messenger
            
            result = false;
        }
        return result;
    }
    
}
