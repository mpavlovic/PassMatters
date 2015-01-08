/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main.controller;

/**
 * Contains methods which are called from main frame when
 * particular event on MainFrame occurs. This interface
 * is implemented by MainController class.
 * @author Milan
 */
public interface MainFrameListener {
    /**
     * This method is called when user clicks 
     * on logout button on MainFrame.
     */
    public void logoutPerformed();
}
