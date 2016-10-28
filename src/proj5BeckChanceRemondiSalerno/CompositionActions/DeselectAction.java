package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.ArrayList;

/**
 * Created by Graham on 10/27/16.
 */
public class DeselectAction implements CompositionAction {


    private NoteGroupable note;

    public DeselectAction(NoteGroupable note) {
        this.note = note;
    }


    @Override
    public void redo() {
        CompositionManager.getInstance().deselectNote(note);
    }

    @Override
    public void undo() {
        CompositionManager.getInstance().selectGroupable(note);
    }
}
