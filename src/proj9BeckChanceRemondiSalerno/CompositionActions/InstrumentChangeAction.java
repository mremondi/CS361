package proj9BeckChanceRemondiSalerno.CompositionActions;

import proj9BeckChanceRemondiSalerno.Models.NoteGroupable;

/**
 * Created by mremondi on 12/11/16.
 */
public class InstrumentChangeAction extends CompositionAction {

    private NoteGroupable noteGroupable;

    private int oldInstrument;

    private int newInstrument;

    public InstrumentChangeAction(NoteGroupable noteGroupable,
                                  int oldInstrument, int newInstrument){
        this.noteGroupable = noteGroupable;
        this.oldInstrument = oldInstrument;
        this.newInstrument = newInstrument;
    }


    @Override
    public void undo() {
        this.noteGroupable.setChannel(oldInstrument);
    }

    @Override
    public void redo() {
        this.noteGroupable.setChannel(newInstrument);
    }
}
