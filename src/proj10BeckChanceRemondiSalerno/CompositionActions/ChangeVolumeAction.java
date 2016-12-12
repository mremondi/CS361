/*
 * File: CompositionAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno.CompositionActions;

import proj10BeckChanceRemondiSalerno.CompositionManager;
import proj10BeckChanceRemondiSalerno.Models.Note;
import proj10BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.HashMap;
import java.util.Map;

/**
 * The class models an action for changing volumes of notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class ChangeVolumeAction extends CompositionAction {

    /**
     * The notes involved in this action
     */
    private Iterable<NoteGroupable> noteGroupables;

    /**
     * The original volumes of notes
     */
    private Map<Note, Integer> oldVolumes;

    /**
     * The new volume
     */
    private int newVolume;

    /**
     * Constructor
     * @param noteGroupables Notes to change volume of
     * @param newVolume new volume
     * @param compositionManager composition manager for performing actions
     */
    public ChangeVolumeAction(Iterable<NoteGroupable> noteGroupables, int newVolume, CompositionManager compositionManager){
        this.noteGroupables = noteGroupables;
        this.newVolume = newVolume;
        this.compositionManager = compositionManager;
        setupOldVolumeMap();
    }

    /**
     * Creates map of original volumes of notes
     */
    private void setupOldVolumeMap() {
        oldVolumes = new HashMap<>();
        for(NoteGroupable noteGroupable: noteGroupables) {
            for(Note note: noteGroupable.getNotes()) {
                oldVolumes.put(note, note.getVolume());
            }
        }
    }

    @Override
    public void undo() {
        for(NoteGroupable noteGroupable: noteGroupables) {
            for(Note note: noteGroupable.getNotes()) {
                note.setVolume(oldVolumes.get(note));
            }
        }
    }

    @Override
    public void redo() {
        for(NoteGroupable noteGroupable: noteGroupables) {
            noteGroupable.setVolume(newVolume);
        }
    }
}
