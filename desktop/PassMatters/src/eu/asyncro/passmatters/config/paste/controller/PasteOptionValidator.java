/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.config.paste.controller;

/**
 * Contains methods that can be called from view
 * class for entering paste keyboard shortcut. This interface
 * should be implemented in appropriate controller class.
 * 
 * @author Milan
 */
public interface PasteOptionValidator {
    void validatePasteShortcut();
}
