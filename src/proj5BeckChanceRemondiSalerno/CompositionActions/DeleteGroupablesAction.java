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

public class DeleteGroupablesAction implements CompositionAction {
    private ArrayList<NoteGroupable> groupables;

    public DeleteGroupablesAction(ArrayList<NoteGroupable> groupables) {
        this.groupables = groupables;
    }

    public void redo() {
        CompositionManager.getInstance().deleteGroupables(groupables);
    }

    public void undo() {
        for (NoteGroupable groupable : groupables) {
            CompositionManager.getInstance().addGroupable(groupable);
        }
    }
}
