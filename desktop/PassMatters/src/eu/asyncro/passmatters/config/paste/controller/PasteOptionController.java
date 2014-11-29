package eu.asyncro.passmatters.config.paste.controller;

import eu.asyncro.passmatters.config.paste.model.PasteShortcut;
import eu.asyncro.passmatters.config.paste.view.InputPasteOptionFrame;
import eu.asyncro.passmatters.config.paste.view.PasteOptionValidationFrame;
import eu.asyncro.passmatters.config.paste.view.PasteValidationFailedFrame;
import eu.asyncro.passmatters.dao.DAOFactory;
import eu.asyncro.passmatters.main.MainAppListener;
import static eu.asyncro.passmatters.util.Constants.PASTE_SHORTCUT_FILE_NAME;
import java.awt.AWTException;
import java.io.File;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;


/**
 * Used for user's paste keyboard shortcut configuration. 
 * @author Milan
 */
public class PasteOptionController implements PasteOptionValidator, 
        PasteOptionConfigurator 
{
    private boolean savingResult = false;
    
    private InputPasteOptionFrame inputPasteOptionFrame;
    private PasteOptionValidationFrame pasteOptionValidationFrame;
    private String copyText;
    private KeyEventRecorder recorder;
    private MainAppListener mainAppListener;
    
    public PasteOptionController() {
        initialize();
    }

    public PasteOptionController(MainAppListener mainAppListener) {
        this();
        this.mainAppListener = mainAppListener;
    }  
    
    private void initialize() {
        inputPasteOptionFrame = new InputPasteOptionFrame();
        inputPasteOptionFrame.setValidator(this);
        
        pasteOptionValidationFrame = new PasteOptionValidationFrame();
        pasteOptionValidationFrame.setValidator(this);
    }
    
    
    @Override
    public void configurePasteOption() {
        startConfig();
    }
    
    @Override
    public void startPasteValidation(KeyEventRecorder recorder) {
        this.recorder = recorder;
        try {
            pasteOptionValidationFrame.showFrame();
        } catch (AWTException ex) {
            // TODO messenger
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void pasteRecordedText() {
        new SwingWorker<Void, Void> () {
            
            @Override
            protected Void doInBackground() throws Exception {
                recorder.typeRecordedKeys();
                return null;
            }

            @Override
            protected void done() {
                super.done();
                checkPastedText();
            }
            
        }.execute();
    }
    
    private void startConfig() {
        generateCopyText();
        inputPasteOptionFrame.clearAll();
        inputPasteOptionFrame.showFrame(copyText);
    }
    
    private void checkPastedText() {
        String pastedText = pasteOptionValidationFrame.getPastedText();
        pasteOptionValidationFrame.dispose();
        
        if(pastedText.equals(copyText)) {
            savePasteShortcut();
            
        }
        else {
            recorder.clear();
            new PasteValidationFailedFrame(this);
        }
    }
    
    private void generateCopyText() {
        // TODO complete method logic
        
        copyText = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4=";
    }
    
    private void savePasteShortcut() {
        final PasteShortcut pasteShortcut = new PasteShortcut(recorder.getRecordedKeyEvents());
        try {
                new SwingWorker<Boolean, Void>() {

                @Override
                protected Boolean doInBackground() throws Exception {
                    return DAOFactory.getFactory(DAOFactory.FILE)
                            .getPasteShortcutDAO()
                            .savePasteShortcut(pasteShortcut);
                }

                    @Override
                    protected void done() {
                        try {
                            super.done();
                            savingResult = get();
                            finishConfig();
                        } catch (InterruptedException | ExecutionException ex) {
                            // TODO messenger
                        }
                    }
            }.execute();
        } catch (Exception ex) {
            // TODO Messenger  
        }
    }

    public boolean isShortcutFileCreated() {
        File file = new File(PASTE_SHORTCUT_FILE_NAME);
        return file.exists();
    }
    
    private void finishConfig() {
        if(true == savingResult) {
            mainAppListener.pasteConfigFinished();
        }
        else {
            // TODO show error messege
        }
    }
    
}
