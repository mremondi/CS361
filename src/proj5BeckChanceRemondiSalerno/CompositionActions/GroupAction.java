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
 * of Grouping notes in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class GroupAction implements CompositionAction {
    /**
     * The NoteGroup to group
     */
    private NoteGroup group;

    /**
     * Constructor
     *
     * @param group the NoteGroup to group
     */
    public GroupAction(NoteGroup group) {
        this.group = group;
    }

    /**
     * Redoes the grouping of the specified NoteGroup.
     */
    public void redo() {
        CompositionManager.getInstance().group(group.getNoteGroupables());
    }

    /**
     * Undoes the grouping of the specified NoteGroup by ungrouping the group.
     */
    public void undo() {
        CompositionManager.getInstance().ungroup(group);
    }

}
