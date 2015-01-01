/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.util;

import eu.asyncro.passmatters.config.paste.controller.KeyEventRecorder;
import eu.asyncro.passmatters.config.paste.controller.KeyTyper;
import eu.asyncro.passmatters.config.paste.model.PasteShortcut;
import eu.asyncro.passmatters.dao.DAOFactory;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author Milan
 */
public class FormFiller implements ClipboardOwner {

    private final long SLEEP_INTERVAL_FOR_CLIPBOARD = 60;
    private final long SLEEP_INTERVAL_BEFORE_PASSWORD_FILL = 100;
    
    private final int MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD = 10;
    private final int MAX_FAILS_AFTER_PASS_IN_CLIPBOARD = 20;
    
    private final boolean TYPE_ENTER_KEY = true;
    
    private final Clipboard clipboard;
    private final KeyTyper typer;
    

    public FormFiller() 
            throws IllegalStateException 
    {
        typer = new KeyEventRecorder();
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("LOST CLIPBOARD OWNERSHIP");
    }
    
    public boolean fillFocusedForm(String content) throws Exception 
    {      
        Transferable clipboardContentBeforeNewOne = getClipboardContents();
        
        PasteShortcut pasteShortcut = getPasteSchortcut();
        if(null == pasteShortcut) return false;
        
        if(false == tryToClearClipboard()) return false; 
        if(false == tryToSetClipboardContents(content)) return false; 
        
        Thread.sleep(SLEEP_INTERVAL_BEFORE_PASSWORD_FILL);   
        typer.typeKeys(pasteShortcut.getKeyEvents(), TYPE_ENTER_KEY);
        
        return tryToReturnPreviousContentToClipboard(clipboardContentBeforeNewOne);
    }
    
    private Transferable getClipboardContents() throws IllegalStateException
    {
        Transferable contents = clipboard.getContents(null);
        return contents;
    }
    
    private void setClipboardContents(String content) 
            throws IllegalStateException, InterruptedException
    {
        Thread.sleep(SLEEP_INTERVAL_FOR_CLIPBOARD);
        StringSelection stringSelection = new StringSelection(content);
        clipboard.setContents(stringSelection, this);
    }
    
    private void returnContentsToClipboard(Transferable contents) 
            throws IllegalStateException, InterruptedException
    {
        clearClipboard();
        Thread.sleep(SLEEP_INTERVAL_FOR_CLIPBOARD);
        clipboard.setContents(contents, this);
    }
    
    private void clearClipboard() throws InterruptedException, 
            IllegalStateException 
    {
        Thread.sleep(SLEEP_INTERVAL_FOR_CLIPBOARD);
        
        clipboard.setContents(new Transferable() {

            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[0];
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return false;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) 
                    throws UnsupportedFlavorException, IOException 
            {
                throw new UnsupportedFlavorException(flavor);
            }
            
        }, this);
    }
    
    private PasteShortcut getPasteSchortcut() throws Exception 
    {
        PasteShortcut pasteShortcut
                = DAOFactory.getFactory(DAOFactory.FILE)
                .getPasteShortcutDAO()
                .getPasteShortcut();

        if (null == pasteShortcut) {
            System.out.println("Password typing exception occured.");
            throw new Exception("Password typing exception occured.");
        }

        return pasteShortcut;
    }
    
    private boolean tryToClearClipboard() {
        int numberOfFails = 0;
        boolean success = false;
        while(numberOfFails < MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD && !success) {
            try {
                clearClipboard();
                success = true;
            } catch(InterruptedException| IllegalStateException ex) {
                numberOfFails++;
            }
        }
        return numberOfFails < MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD;
    }
    
    private boolean tryToSetClipboardContents(String content) {
        int numberOfFails = 0;
        boolean success = false;
        while(numberOfFails < MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD && !success) {
            try {
                setClipboardContents(content);
                success = true;
            } catch (IllegalStateException | InterruptedException ex) {
                numberOfFails++;
            }
        }
        return numberOfFails < MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD;
    }
    
    private boolean tryToReturnPreviousContentToClipboard(Transferable content) {
        int numberOfFails = 0;
        boolean success = false;
        while(numberOfFails < MAX_FAILS_AFTER_PASS_IN_CLIPBOARD && !success) {
            try {
                returnContentsToClipboard(content);
                success = true;
            } catch (IllegalStateException | InterruptedException ex) {
                numberOfFails++;
            }
        }
        return numberOfFails < MAX_FAILS_AFTER_PASS_IN_CLIPBOARD;
    }
    
}
