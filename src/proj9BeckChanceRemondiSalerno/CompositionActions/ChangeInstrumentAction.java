/*
 * File: CompositionAction.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj9BeckChanceRemondiSalerno.CompositionActions;

import proj9BeckChanceRemondiSalerno.CompositionManager;
import proj9BeckChanceRemondiSalerno.Models.Note;
import proj9BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class models an action that changes the instrument of notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class ChangeInstrumentAction extends CompositionAction {

    /**
     * notes to change instrument of
     */
    private Iterable<NoteGroupable> noteGroupables;

    /**
     * Map of original channels
     */
    private Map<Note, Integer> oldChannels;

    /**
     * The new channel
     */
    private int newChannel;

    /**
     * Constructor
     * @param noteGroupables
     * @param newChannel
     * @param compositionManager
     */
    public ChangeInstrumentAction(Iterable<NoteGroupable> noteGroupables, int newChannel, CompositionManager compositionManager){
        this.noteGroupables = noteGroupables;
        this.newChannel = newChannel;
        this.compositionManager = compositionManager;
        setupOldVolumeMap();
    }

    /**
     * Saves original volumes
     */
    private void setupOldVolumeMap() {
        oldChannels = new HashMap<>();
        for(NoteGroupable noteGroupable: noteGroupables) {
            for(Note note: noteGroupable.getNotes()) {
                oldChannels.put(note, note.getChannel());
            }
        }
    }

    @Override
    public void undo() {
        for(NoteGroupable noteGroupable: noteGroupables) {
            for(Note note: noteGroupable.getNotes()) {
                compositionManager.setChannelForNote(note, oldChannels.get(note));
            }
        }
    }

    @Override
    public void redo() {
        compositionManager.setChannelForNotes(noteGroupables, newChannel);
    }
}
