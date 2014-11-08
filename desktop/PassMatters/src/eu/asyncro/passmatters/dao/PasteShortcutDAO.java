/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.dao;

import eu.asyncro.passmatters.config.paste.model.PasteShortcut;

/**
 *
 * @author Milan
 */
public interface PasteShortcutDAO {
    PasteShortcut getPasteShortcut();
    boolean savePasteShortcut(PasteShortcut pasteShortcut);
}
