/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.util;

import java.awt.EventQueue;
import java.awt.Frame;
import javax.swing.JOptionPane;

/**
 * Class with static methods for showing different message dialogs.
 * @author Milan
 */
public class Messenger {
    /**
     * Shows dialog with info message.
     * @param message info message to be shown in dialog
     * @param frame parent frame of dialog
     */
    public static void showInfoMessage(final String message, final Frame frame) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JOptionPane.showMessageDialog(frame, message);
            }
        });
    }
    
    /**
     * Shows dialog with error message.
     * @param message error message to be shown in dialog
     * @param frame parent frame of dialog
     */
    public static void showErrorMessage(final String message, final Frame frame) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
               JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    /**
     * Shows dialog with warning message.
     * @param message warning message to be shown in dialog
     * @param frame parent frame of dialog
     */
    public static void showWarningMessage(final String message, final Frame frame) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
               JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    } 
}
