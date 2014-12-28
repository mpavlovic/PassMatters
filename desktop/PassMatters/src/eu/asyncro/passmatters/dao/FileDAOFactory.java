/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.dao;

/**
 * Concrete DAO factory for file storage type.
 * Produces DAO objects which manipulate with 
 * data saved in files.
 * 
 * @author Milan
 */
public class FileDAOFactory extends DAOFactory {

    @Override
    public PasteShortcutDAO getPasteShortcutDAO() {
        return new FilePasteShortcutDAO();
    }
    
}
