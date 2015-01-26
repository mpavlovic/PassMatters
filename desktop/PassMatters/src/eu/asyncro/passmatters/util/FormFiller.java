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
 * Form filler is a class for filling focused form fields with
 * provided data. It is also a ClipboardOwner because it manages with clipboard 
 * content during filed filling.
 * @author Milan
 */
public class FormFiller implements ClipboardOwner {

    private final long SLEEP_INTERVAL_FOR_CLIPBOARD = 60;
    private final long SLEEP_INTERVAL_BEFORE_PASSWORD_FILL = 100;
    private final long SLEEP_INTERVAL_AFTER_PASSWORD_FILL = 200;
    
    private final int MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD = 10;
    private final int MAX_FAILS_AFTER_PASS_IN_CLIPBOARD = 20;
    
    private final boolean TYPE_ENTER_KEY = true;
    
    private final Clipboard clipboard;
    private final KeyTyper typer;
    
    private Transferable clipboardContentBeforeNewOne;

    /**
     * Constructor.
     * @throws IllegalStateException 
     */
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
    
    /**
     * Fills the focused form field with given string.
     * First saves the current clipboard content, sets the provided content
     * to clipboard and performs paste option. Then returns old content back 
     * to clipboard.
     * @param content string to be typed into the focused form field
     * @return true if content was filled successfully and old clipboard content
     * was returned to the clipboard, false otherwise
     * @throws Exception 
     */
    public boolean fillFocusedFormField(String content) throws Exception 
    {      
        //Thread.sleep(3000); // TODO remove
        if(false == tryToGetClipboardContents()) return false;
        
        PasteShortcut pasteShortcut = getPasteSchortcut();
        if(null == pasteShortcut) return false;
        
        if(false == tryToClearClipboard()) return false; 
        if(false == tryToSetClipboardContents(content)) return false; 
        
        Thread.sleep(SLEEP_INTERVAL_BEFORE_PASSWORD_FILL);   
        typer.typeKeys(pasteShortcut.getKeyEvents(), TYPE_ENTER_KEY);
        Thread.sleep(SLEEP_INTERVAL_AFTER_PASSWORD_FILL);
        
        return tryToReturnPreviousContentToClipboard(clipboardContentBeforeNewOne);
    }
    
    /**
     * Returns the current clipboard content.
     * @return java.awt.datatransfer.Transferable object representing clipboard 
     *         content
     * @throws IllegalStateException
     * @throws InterruptedException 
     */
    private Transferable getClipboardContents() 
            throws IllegalStateException, InterruptedException
    {
        Thread.sleep(SLEEP_INTERVAL_BEFORE_PASSWORD_FILL);
        Transferable contents = clipboard.getContents(null);
        return contents;
    }
    
    /**
     * Sets the provided string content to clipboard. If old content wasn't 
     * saved before, it will be overwritten with a new one.
     * @param content new content for placing into clipboard
     * @throws IllegalStateException
     * @throws InterruptedException 
     */
    private void setClipboardContents(String content) 
            throws IllegalStateException, InterruptedException
    {
        Thread.sleep(SLEEP_INTERVAL_FOR_CLIPBOARD);
        StringSelection stringSelection = new StringSelection(content);
        clipboard.setContents(stringSelection, this);
    }
    
    /**
     * Sets the provided Transferable content to cleared clipboard. Before the
     * content is set, it clears current clipboard contents and places provided
     * one in cleared clipboard.
     * @param contents content to be set (or returned) to clipboard
     * @throws IllegalStateException
     * @throws InterruptedException
     */
    private void returnContentsToClipboard(Transferable contents) 
            throws IllegalStateException, InterruptedException
    {
        clearClipboard();
        Thread.sleep(SLEEP_INTERVAL_FOR_CLIPBOARD);
        clipboard.setContents(contents, this);
    }
    
    /**
     * Clears the current clipboard content. If succeeds, system clipboard 
     * should be empty.
     * @throws InterruptedException
     * @throws IllegalStateException 
     */
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
    
    /**
     * Fetches the saved user's keyboard shortcut for system paste option.  
     * @return PaseShortcut with system paste key events (eg. Ctrl + V)
     * @throws Exception 
     */
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
    
    /**
     * Tries to clear system clipboard contents for 
     * MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD times.
     * @return true if system clipboard was cleared, false otherwise
     */
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
        return success;
    }
    
    /**
     * Tries to get system clipboard content for
     * MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD times.
     * @return true if clipboard content is fetched successfully, false otherwise
     */
    private boolean tryToGetClipboardContents() {
        int numberOfFails = 0;
        boolean success = false;
        while(numberOfFails < MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD && !success) {
            try {
                clipboardContentBeforeNewOne = getClipboardContents();
                success = true;
            }
            catch(IllegalStateException | InterruptedException ex) {
                numberOfFails++;
            }
        }
        return success;
    }
    
    /**
     * Tries to set provided string content to system clipboard for 
     * MAX_FAILS_BEFORE_PASS_IN_CLIPBOARD times.
     * @param content content to be set to system clipboard
     * @return true if content was set successfully, false otherwise
     */
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
        return success;
    }
    
    /**
     * Tries to set the provided (old) clipboard content back to clipboard
     * for MAX_FAILS_AFTER_PASS_IN_CLIPBOARD times.
     * Assumes that content was firstly fetched form clipboard.
     * @param content content to be returned back to clipboard
     * @return true if content was returned successfully, false otherwise.
     */
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
        return success;
    }
    
}
