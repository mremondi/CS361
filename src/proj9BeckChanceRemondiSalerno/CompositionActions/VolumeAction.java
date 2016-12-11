package proj9BeckChanceRemondiSalerno.CompositionActions;

import proj9BeckChanceRemondiSalerno.Models.NoteGroupable;

/**
 * Created by mremondi on 12/11/16.
 */
public class VolumeAction extends CompositionAction {

    private NoteGroupable noteGroupable;

    private int oldVolume;

    private int newVolume;

    public VolumeAction(NoteGroupable noteGroupable, int oldVolume, int newVolume){
        this.noteGroupable = noteGroupable;
        this.oldVolume = oldVolume;
        this.newVolume = newVolume;
    }

    @Override
    public void undo() {
        this.noteGroupable.setVolume(oldVolume);
    }

    @Override
    public void redo() {
        this.noteGroupable.setVolume(newVolume);
    }
}
