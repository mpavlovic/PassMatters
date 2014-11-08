package eu.asyncro.passmatters.dao;

/**
 * Abstract database factory which 
 * creates concrete DAO factories.
 * 
 * @author Milan
 */
public abstract class DAOFactory {
    public static final int FILE = 0;
    
    public static DAOFactory getFactory(int type) {
        switch(type) {
            case FILE:
                return new FileDAOFactory();
            default:
                return null;
        }
    }
    
    public abstract PasteShortcutDAO getPasteShortcutDAO();
    
}
