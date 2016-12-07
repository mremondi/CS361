/*
 * File: DeselectAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */

package proj9BeckChanceRemondiSalerno.CompositionActions;

import proj9BeckChanceRemondiSalerno.CompositionManager;
import proj9BeckChanceRemondiSalerno.Models.NoteGroupable;

/**
 * This class extends the CompositionAction abstract class and represents the action
 * of deselecting a note in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class DeselectAction extends CompositionAction {
    /**
     * The note to deselect.
     */
    private NoteGroupable note;

    /**
     * Constructor
     *
     * @param note the note to deselect
     */
    public DeselectAction(NoteGroupable note, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.note = note;
    }

    /**
     * Redoes the deselection of a note.
     */
    public void redo() {
        compositionManager.deselectNote(note);
    }

    /**
     * Undoes the deselection of a note by reselecting it.
     */
    public void undo() {
        compositionManager.selectGroupable(note);
    }
}
