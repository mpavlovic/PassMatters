/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.util;

import eu.asyncro.passmatters.main.MainAppListener;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

/**
 *
 * @author Milan
 */
public class PasswordTyper implements ClipboardOwner {

    private String password;
    private MainAppListener mainAppListener;
    
    
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        
    }
    
    
    private Transferable getClipboardContents() throws IllegalStateException
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // TODO should it be outside ?
        Transferable contents = clipboard.getContents(null);
        return contents;
    }
    
    private void setClipboardContents(String content) {
        
    }
    
    
    
}
