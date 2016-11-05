/*
 * File: PasteAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 7
 * Due Date: November 10, 2016
 */


package proj7BeckChanceRemondiSalerno.CompositionActions;

import proj7BeckChanceRemondiSalerno.CompositionManager;
import proj7BeckChanceRemondiSalerno.Models.NoteGroupable;
import java.util.ArrayList;

/**
 * Created by Graham on 11/5/16.
 */
public class PasteAction extends CompositionAction {

    /**
     * The notes that were pasted
     */
    private ArrayList<NoteGroupable> pastedNotes;

    /**
     * Constructor
     * @param pastedNotes The notes that were pasted
     * @param compositionManager The composition Manager
     */
    public PasteAction(ArrayList<NoteGroupable> pastedNotes, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.pastedNotes = pastedNotes;
    }

    /**
     * Undoes the paste action
     */
    @Override
    public void undo() {
        for (NoteGroupable noteGroupable: pastedNotes) {
            compositionManager.deleteGroupable(noteGroupable);
        }
    }

    /**
     * Redoes the paste action
     */
    @Override
    public void redo() {
        for (NoteGroupable noteGroupable : pastedNotes) {
            compositionManager.addGroupable(noteGroupable.clone());
        }
    }
}
