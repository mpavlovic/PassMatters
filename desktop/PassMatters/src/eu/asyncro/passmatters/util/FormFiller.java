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
import eu.asyncro.passmatters.main.MainAppListener;
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

    private MainAppListener mainAppListener;
    private Clipboard clipboard;
    private static final long SLEEP_INTERVAL = 40;
    
    public FormFiller(MainAppListener mainAppListener) 
            throws IllegalStateException 
    {
        this.mainAppListener = mainAppListener;
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("lost ownership");
    }
    
    public void fillFocusedForm(String content) throws Exception 
    {
        Thread.sleep(2000);
        
        Transferable clipboardContentBeforeNewOne = getClipboardContents(); //TODO null ?
        
        PasteShortcut pasteShortcut = getPasteSchortcut();
        
        clearClipboard();
        setClipboardContents(content);
        
        KeyTyper typer = new KeyEventRecorder();
        typer.typeKeys(pasteShortcut.getKeyEvents(), false);
        
        returnContentsToClipboard(clipboardContentBeforeNewOne);
        
        mainAppListener.passwordFilled();
    }
    
    private Transferable getClipboardContents() throws IllegalStateException
    {
        Transferable contents = clipboard.getContents(null);
        return contents;
    }
    
    private void setClipboardContents(String content) 
            throws IllegalStateException, InterruptedException
    {
        Thread.sleep(SLEEP_INTERVAL);
        StringSelection stringSelection = new StringSelection(content);
        //Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }
    
    private void returnContentsToClipboard(Transferable contents) 
            throws IllegalStateException, InterruptedException
    {
        clearClipboard();
        Thread.sleep(SLEEP_INTERVAL);
        //Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(contents, this);
    }
    
    private void clearClipboard() throws InterruptedException 
    {
        Thread.sleep(SLEEP_INTERVAL);
        
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
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
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
    
}
