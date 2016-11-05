package proj6BeckChanceRemondiSalerno.CompositionActions;

import proj6BeckChanceRemondiSalerno.CompositionManager;
import proj6BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj6BeckChanceRemondiSalerno.NotesClipboardManager;
import java.util.ArrayList;

/**
 * Created by Graham on 11/5/16.
 */
public class CutAction extends CompositionAction {

    /**
     * The notes that were cut
     */
    private ArrayList<NoteGroupable> cutNotes;

    /**
     * The manager for the clipboard
     */
    private NotesClipboardManager notesClipboardManager;

    /**
     * Constructor
     * @param cutNotes The notes that were cut
     * @param compositionManager The composition manager
     * @param notesClipboardManager The clipboard manager
     */
    public CutAction(ArrayList<NoteGroupable> cutNotes, CompositionManager compositionManager, NotesClipboardManager notesClipboardManager) {
        this.compositionManager = compositionManager;
        this.cutNotes = cutNotes;
        this.notesClipboardManager = notesClipboardManager;
    }

    /**
     * Undoes the cut action
     */
    @Override
    public void undo() {
        for (NoteGroupable noteGroupable: cutNotes) {
            compositionManager.addGroupable(noteGroupable);
        }
    }

    /**
     * redoes the cut action
     */
    @Override
    public void redo() {
        notesClipboardManager.cutNotes(cutNotes);
    }
}
