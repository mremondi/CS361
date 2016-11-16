/*
 * File: AddNoteAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj8BeckChanceRemondiSalerno.CompositionActions;


import proj8BeckChanceRemondiSalerno.CompositionManager;
import proj8BeckChanceRemondiSalerno.Models.Note;
import proj8BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * This class implements the CompositionAction interface and represents the action
 * of adding a Note to the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class AddNoteAction extends CompositionAction {

    /**
     *  The note to be added
     */
    private Note note;

    private ArrayList<NoteGroupable> deselectedNotes;

    /**
     * Constructor
     *
     * @param note takes a note to be added.
     */
    public AddNoteAction(Note note, ArrayList<NoteGroupable> deselectedNotes, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.deselectedNotes = deselectedNotes;
        this.note = note;
    }

    /**
     * Redoes the addition of a Note to the Composition.
     */
    public void redo() {
        for (NoteGroupable note: deselectedNotes) {
            compositionManager.deselectNote(note);
        }
        compositionManager.addGroupable(note);
    }

    /**
     * Undoes the addition of a Note to the Composition.
     */
    public void undo() {
        for (NoteGroupable note: deselectedNotes) {
            compositionManager.selectGroupable(note);
        }
        compositionManager.deleteGroupable(note);
    }

}
