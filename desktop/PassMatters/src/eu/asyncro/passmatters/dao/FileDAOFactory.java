/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.dao;

/**
 *
 * @author Milan
 */
public class FileDAOFactory extends DAOFactory {

    @Override
    public PasteShortcutDAO getPasteShortcutDAO() {
        return new FilePasteShortcutDAO();
    }
    
}
