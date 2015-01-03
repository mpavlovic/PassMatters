/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.main;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Enables moving of application to system tray.
 * 
 * @author Milan
 */
public class SystemTrayController implements ActionListener {

    private final String systemTrayImageName = "trayicon.png";
    private final Image systemTrayImage;
    private final TrayIcon trayIcon;
    private final MainAppListener mainAppListener;
    
    private boolean isTrayIconAdded = false;
    
    /**
     * Creates new instance of this class.
     * @param mainAppListener MainAppListener instance
     */
    public SystemTrayController(MainAppListener mainAppListener) {
        systemTrayImage = Toolkit.getDefaultToolkit().getImage(systemTrayImageName);
        trayIcon = new TrayIcon(systemTrayImage, "PassMatters");
        trayIcon.addActionListener(this);
        this.mainAppListener = mainAppListener;
    }
    
    
    /**
     * Returns whether is system tray supported or not.
     * @return <code>true</code> if system tray is supported, <code>false</code> otherwise
     * @see java.awt.SystemTray
     */
    public boolean isSystemTraySupported() {
       return SystemTray.isSupported();
    }

    /**
     * Returns whether tray icon is added to system tray.
     * @return <code>true</code> if tray icon is added, <code>false</code> otherwise
     */
    public boolean isTrayIconAdded() {
        return isTrayIconAdded;
    }
    
    /**
     * Shows application icon in system tray.
     * @throws AWTException 
     * @throws UnsupportedOperationException
     * @throws HeadlessException
     * @throws SecurityException
     */
    public void showInSystemTray() 
            throws AWTException, UnsupportedOperationException, 
                HeadlessException, SecurityException
    {
        SystemTray systemTray = SystemTray.getSystemTray(); // TODO exeptions
        systemTray.add(trayIcon);
        isTrayIconAdded = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("event in tray");
        mainAppListener.systemTrayActionPerformed();
    }
}
