/*
 * File: AddNoteAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno.CompositionActions;

import proj10BeckChanceRemondiSalerno.CompositionManager;
import proj10BeckChanceRemondiSalerno.Models.Note;
import proj10BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * This class extends the CompositionAction abstract class and represents the action
 * of adding a Note to the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class AddNoteAction extends CompositionAction {

    /**
     * The note to be added
     */
    private Note note;

    /**
     * The notes that were unselected when the new note was added.
     */
    private ArrayList<NoteGroupable> deselectedNotes;

    /**
     * Constructor
     *
     * @param note takes a note to be added.
     */
    public AddNoteAction(Note note, ArrayList<NoteGroupable> deselectedNotes,
                         CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.deselectedNotes = deselectedNotes;
        this.note = note;
    }

    /**
     * Redoes the addition of a Note to the Composition.
     */
    public void redo() {
        for (NoteGroupable note : deselectedNotes) {
            compositionManager.deselectNote(note);
        }
        compositionManager.addGroupable(note);
    }

    /**
     * Undoes the addition of a Note to the Composition.
     */
    public void undo() {
        for (NoteGroupable note : deselectedNotes) {
            compositionManager.selectGroupable(note);
        }
        compositionManager.deleteGroupable(note);
    }
}
