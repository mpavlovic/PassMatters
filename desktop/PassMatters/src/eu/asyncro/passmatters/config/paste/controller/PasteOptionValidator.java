package eu.asyncro.passmatters.config.paste.controller;

/**
 * Contains methods that can be called from view
 * class for entering paste keyboard shortcut. This interface
 * should be implemented in appropriate controller class.
 * 
 * @author Milan
 */
public interface PasteOptionValidator {

    /**
     * Starts paste option validation procedure.
     * @param recorder Recorder which contains recorded keyboard events.
     */
    public void startPasteValidation(KeyEventRecorder recorder);

    /**
     *Simulates recored keyboard events for validation purposes.
     * Ideally, keyboard events should be for user's paste keyboard shortcut.
     */
    public void pasteTextWithRecordedKeys();
}
