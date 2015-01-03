/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main;

/**
 *
 * @author Milan
 */
public interface MainAppListener {
    public void pasteConfigFinished();
    public void loginFinished();
    public void logoutFinished();
    public void passwordFilled();
    public void systemTrayActionPerformed();
}
