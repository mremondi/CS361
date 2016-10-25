package proj5BeckChanceRemondiSalerno.CompositionActions;


import proj5BeckChanceRemondiSalerno.Models.NoteGroup;

public class UngroupAction implements CompositionAction {

    private NoteGroup group;

    public UngroupAction(NoteGroup group) {
        this.group = group;
    }

    public void redo() {

    }

    public void undo() {

    }

}
