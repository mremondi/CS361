package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.Models.NoteGroup;

public class GroupAction implements CompositionAction {

    private NoteGroup group;

    public GroupAction(NoteGroup group) {
        this.group = group;
    }

    public void redo() {

    }

    public void undo() {

    }

}
