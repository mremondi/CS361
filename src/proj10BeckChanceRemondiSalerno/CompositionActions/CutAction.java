/*
 * File: CutAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno.CompositionActions;

import proj10BeckChanceRemondiSalerno.CompositionManager;
import proj10BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj10BeckChanceRemondiSalerno.NotesClipboardManager;

import java.util.ArrayList;

/**
 * This class extends the CompositionAction abstract class and represents the action
 * of cutting a NoteGroupable or several NoteGroupables from the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
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
     *
     * @param cutNotes              The notes that were cut
     * @param compositionManager    The composition manager
     * @param notesClipboardManager The clipboard manager
     */
    public CutAction(ArrayList<NoteGroupable> cutNotes, CompositionManager
            compositionManager, NotesClipboardManager notesClipboardManager) {
        this.compositionManager = compositionManager;
        this.cutNotes = cutNotes;
        this.notesClipboardManager = notesClipboardManager;
    }

    /**
     * Undoes the cut action
     */
    @Override
    public void undo() {
        for (NoteGroupable noteGroupable : cutNotes) {
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
