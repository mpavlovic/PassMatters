package eu.asyncro.passmatters.config.paste.controller;

import eu.asyncro.passmatters.config.paste.model.PasteShortcut;
import eu.asyncro.passmatters.config.paste.view.InputPasteOptionFrame;
import eu.asyncro.passmatters.config.paste.view.PasteOptionValidationFrame;
import eu.asyncro.passmatters.config.paste.view.PasteValidationFailedFrame;
import eu.asyncro.passmatters.dao.DAOFactory;
import eu.asyncro.passmatters.main.MainAppListener;
import static eu.asyncro.passmatters.util.Constants.PASTE_SHORTCUT_FILE_NAME;
import eu.asyncro.passmatters.util.Messenger;
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
    
    /**
     * Constructor.
     */
    public PasteOptionController() {
        initialize();
    }
    
    /**
     * Constructor. 
     * @param mainAppListener MainAppListener for global application events.
     */
    public PasteOptionController(MainAppListener mainAppListener) {
        this();
        this.mainAppListener = mainAppListener;
    }  
    
    /**
     * Initializes some class components.
     */
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
            Messenger.showErrorMessage("There was a problem during validation.", null);
        }
    }
    
    @Override
    public void pasteTextWithRecordedKeys() {
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
    
    /**
     * Starts paste option configuration 
     * by showing config frame.
     */
    private void startConfig() {
        generateCopyText();
        inputPasteOptionFrame.clearAll();
        inputPasteOptionFrame.showFrame(copyText);
    }
    
    /**
     * Checks whether the user pasted text in
     * paste option validation frame matches generated one.
     * If true, starts paste shortcut saving.
     */
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
    
    /**
     * Generates random string for copying 
     * during paste option configuration. 
     */
    private void generateCopyText() {
        // TODO complete method logic
        
        copyText = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4=";
    }
    
    /**
     * Saves recorded keys for paste shortcut.
     */
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
                        Messenger.showErrorMessage("Could not finish schortcut saving.", null);
                    }
                }
            }.execute();
        } catch (Exception ex) {
            Messenger.showErrorMessage("Could not save schortcut.", null);
        }
    }
    
    /**
     * Checks if file with user's paste shortcut keystrokes exists.
     * @return true if file exists, false otherwise
     * @see exists() method in JDK class File
     */
    public boolean isShortcutFileCreated() {
        File file = new File(PASTE_SHORTCUT_FILE_NAME);
        return file.exists();
    }
    
    /**
     * Informs main application listener 
     * that paste option configuration finished.
     */
    private void finishConfig() {
        if(true == savingResult) {
            mainAppListener.pasteConfigFinished();
        }
        else {
            Messenger.showErrorMessage("Could not finish configuration.", null);
        }
    }
    
}
