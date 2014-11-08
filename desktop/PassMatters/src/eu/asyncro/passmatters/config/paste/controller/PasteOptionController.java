/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.asyncro.passmatters.config.paste.controller;

import eu.asyncro.passmatters.config.paste.view.InputPasteOptionFrame;
import eu.asyncro.passmatters.config.paste.view.PasteOptionValidationFrame;


/**
 * Used for user's paste keyboard shortcut configuration. 
 * @author Milan
 */
public class PasteOptionController implements PasteOptionValidator {
    
    private InputPasteOptionFrame inputPasteOptionFrame;
    private PasteOptionValidationFrame pasteOptionValidationFrame;
    
    private String pasteString;
    
    public PasteOptionController() {
        initialize();
    }
    
    private void initialize() {
        inputPasteOptionFrame = new InputPasteOptionFrame();
        inputPasteOptionFrame.setValidator(this);
        
        pasteOptionValidationFrame = new PasteOptionValidationFrame();
    }
    
    
    public void startConfig() {
        generatePasteString();
        inputPasteOptionFrame.showFrame(pasteString);
    }

    @Override
    public void validatePasteShortcut() {
        
    }
    
    
    private void generatePasteString() {
        // TODO complete method logic
        
        pasteString = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4=";
    }
    
}
