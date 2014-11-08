/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main;

import eu.asyncro.passmatters.config.paste.controller.PasteOptionController;
import static eu.asyncro.passmatters.util.Constants.PASTE_SHORTCUT_FILE_NAME;
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
            PasteOptionController pasteOptionController = new PasteOptionController();
            pasteOptionController.startConfig();
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
