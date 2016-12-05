/*
 * File: PasteAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */


package proj8BeckChanceRemondiSalerno.CompositionActions;

import proj8BeckChanceRemondiSalerno.CompositionManager;
import proj8BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * This class extends the CompositionAction abstract class and represents the action
 * of pasting a NoteGroupable or NoteGroupables to the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class PasteAction extends CompositionAction {

    /**
     * The notes that were pasted
     */
    private ArrayList<NoteGroupable> pastedNotes;

    /**
     * Constructor
     *
     * @param pastedNotes        The notes that were pasted
     * @param compositionManager The composition Manager
     */
    public PasteAction(ArrayList<NoteGroupable> pastedNotes, CompositionManager
            compositionManager) {
        this.compositionManager = compositionManager;
        this.pastedNotes = pastedNotes;
    }

    /**
     * Undoes the paste action
     */
    @Override
    public void undo() {
        for (NoteGroupable noteGroupable : pastedNotes) {
            compositionManager.deleteGroupable(noteGroupable);
        }
    }

    /**
     * Redoes the paste action
     */
    @Override
    public void redo() {
        for (NoteGroupable noteGroupable : pastedNotes) {
            compositionManager.addGroupable(noteGroupable);
        }
    }
}
