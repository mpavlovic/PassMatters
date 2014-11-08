package eu.asyncro.passmatters.main;

import static eu.asyncro.passmatters.util.Constants.PASTE_SHORTCUT_FILE_NAME;
import eu.asyncro.passmatters.config.paste.controller.PasteOptionController;
import java.io.File;

/**
 *This is application main class with main method.
 * @author Milan
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if(!isShortcutFileCreated()) {
            PasteOptionController pasteOptionConfigurator = new PasteOptionController();
            pasteOptionConfigurator.configurePasteOption();
        }
        else {
            // TODO login
            System.out.println("login...");
        }
        
        
    }
    
    private static boolean isShortcutFileCreated() {
        File file = new File(PASTE_SHORTCUT_FILE_NAME);
        return file.exists();
    }
    
}
