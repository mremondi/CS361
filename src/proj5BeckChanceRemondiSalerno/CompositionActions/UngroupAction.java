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

public class UngroupAction implements CompositionAction {

    private NoteGroup group;

    public UngroupAction(NoteGroup group) {
        this.group = group;
    }

    public void redo() {
        CompositionManager.getInstance().ungroup(group);
    }

    public void undo() {
        CompositionManager.getInstance().group(group.getNoteGroupables());
    }

}
