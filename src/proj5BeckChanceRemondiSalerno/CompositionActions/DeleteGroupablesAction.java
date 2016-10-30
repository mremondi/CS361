/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * This class implements the CompositionAction interface and represents the action
 * of deleting NoteGroupables from the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class DeleteGroupablesAction implements CompositionAction {
    /**
     * An ArrayList of NoteGroupables to be deleted
     */
    private ArrayList<NoteGroupable> groupables;

    /**
     * Constructor
     *
     * @param groupables the NoteGroupables to be deleted
     */
    public DeleteGroupablesAction(ArrayList<NoteGroupable> groupables) {
        this.groupables = groupables;
    }

    /**
     * Redoes the deleteGroupables action
     */
    public void redo() {
        CompositionManager.getInstance().deleteGroupables(groupables);
    }

    /**
     * Undoes the delete groupables action
     */
    public void undo() {
        for (NoteGroupable groupable : groupables) {
            CompositionManager.getInstance().addGroupable(groupable);
        }
    }
}
