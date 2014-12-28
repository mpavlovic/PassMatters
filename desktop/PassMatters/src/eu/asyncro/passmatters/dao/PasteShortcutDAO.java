/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.dao;

import eu.asyncro.passmatters.config.paste.model.PasteShortcut;

/**
 * Interface containing CRUD methods for PasteShortcut objects.
 * 
 * @author Milan
 */
public interface PasteShortcutDAO {

    /**
     * Returns user's keyboard shortcut for system paste option. 
     * @return PasteShortcut containing recorded key events. @code{null} if reading wasn't successful.
     * @see PasteShortcut
     */
    public PasteShortcut getPasteShortcut();

    /**
     * Saves user's keyboard shortcut for system paste option.
     * @param pasteShortcut PasteShortcut to save
     * @return true if saving finished successfully, false otherwise
     */
    public boolean savePasteShortcut(PasteShortcut pasteShortcut);
}
