package eu.asyncro.passmatters.config.paste.controller;

/**
 * Contains methods that can be called from view
 * class for entering paste keyboard shortcut. This interface
 * should be implemented in appropriate controller class.
 * 
 * @author Milan
 */
public interface PasteOptionValidator {
    void startPasteValidation(KeyEventRecorder recorder);
    void pasteRecordedText();
}
