/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj5BeckChanceRemondiSalerno.CompositionActions;


import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.Note;

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

    /**
     * Constructor
     *
     * @param note takes a note to be added.
     */
    public AddNoteAction(Note note, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.note = note;
    }

    /**
     * Redoes the addition of a Note to the Composition.
     */
    public void redo() {
        compositionManager.addGroupable(note);
    }

    /**
     * Undoes the addition of a Note to the Composition.
     */
    public void undo() {
        compositionManager.deleteGroupable(note);
    }

}
