/*
 * File: GroupAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj9BeckChanceRemondiSalerno.CompositionActions;

import proj9BeckChanceRemondiSalerno.CompositionManager;
import proj9BeckChanceRemondiSalerno.Models.NoteGroup;
import proj9BeckChanceRemondiSalerno.Models.NoteGroupable;

/**
 * This class extends the CompositionAction abstract class and represents the action
 * of Grouping notes in the Composition.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class GroupAction extends CompositionAction {
    /**
     * The NoteGroup to group
     */
    private NoteGroup group;

    /**
     * Constructor
     *
     * @param group the NoteGroup to group
     */
    public GroupAction(NoteGroup group, CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        this.group = group;
    }

    /**
     * Redoes the grouping of the specified NoteGroup.
     */
    public void redo() {
        for (NoteGroupable noteGroupable : group.getNoteGroupables()) {
            compositionManager.deleteGroupable(noteGroupable);
        }
        compositionManager.addGroupable(group);
    }

    /**
     * Undoes the grouping of the specified NoteGroup by ungrouping the group.
     */
    public void undo() {
        compositionManager.ungroup(group);
    }

}
