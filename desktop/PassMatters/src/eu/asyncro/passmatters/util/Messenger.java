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
 * Služi za prikaz poruka obavijesti ili grešaka.
 * @author Milan
 */
public class Messenger {
    public static void showInfoMessage(final String message, final Frame frame) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JOptionPane.showMessageDialog(frame, message);
            }
        });
    }
    
    public static void showErrorMessage(final String message, final Frame frame) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
               JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public static void showWarningMessage(final String message, final Frame frame) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
               JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    } 
}
