
package proj5BeckChanceRemondiSalerno.CompositionActions;

import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;



public class ResizeAction implements CompositionAction {

    private NoteGroupable note;
    private double startWidth;
    private double endWidth;


    public ResizeAction(NoteGroupable note, double startWidth, double endWidth) {
        this.note = note;
        this.startWidth = startWidth;
        this.endWidth = endWidth;
    }

    public void redo() {

    }

    public void undo() {

    }

}
