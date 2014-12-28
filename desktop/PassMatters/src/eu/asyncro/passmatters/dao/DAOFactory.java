package eu.asyncro.passmatters.dao;

/**
 * Abstract Database Access Object factory which 
 * creates concrete DAO factories, depending on storage type.
 * Current storage types: FILE
 * 
 * @author Milan
 */
public abstract class DAOFactory {
    public static final int FILE = 0;
    
    /**
     * Returns DAO factory object for specified storage type.
     * @param type Integer specifying desired factory type (static member of DAOFactory) 
     * @return DAOFactory for requested storage type
     */
    public static DAOFactory getFactory(int type) {
        switch(type) {
            case FILE:
                return new FileDAOFactory();
            default:
                return null;
        }
    }
    /**
     * Returns DAO object for (CRUD) manipulating with paste shortcuts.
     * @see PasteShortcut
     * @return PasteShortcutDAO
     */
    public abstract PasteShortcutDAO getPasteShortcutDAO();
    
}
