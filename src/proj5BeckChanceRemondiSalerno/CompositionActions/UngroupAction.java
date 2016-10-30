/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj5BeckChanceRemondiSalerno.CompositionActions;


import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroup;

/**
 * This class implements the CompositionAction interface and represents the action
 * of ungrouping notes in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class UngroupAction extends CompositionAction {
    /**
     * The note group
     */
    private NoteGroup group;

    /**
     * Constructor
     *
     * @param group the note group
     */
    public UngroupAction(NoteGroup group, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.group = group;
    }

    /**
     * Redoes the ungrouping of notes.
     */
    public void redo() {
        compositionManager.ungroup(group);
    }

    /**
     * Undoes the ungrouping of notes by regrouping them.
     */
    public void undo() {
        compositionManager.group(group.getNoteGroupables());
    }

}
