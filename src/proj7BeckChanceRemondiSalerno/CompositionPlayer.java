/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj7BeckChanceRemondiSalerno;

import proj7BeckChanceRemondiSalerno.Models.MidiPlayer;
import proj7BeckChanceRemondiSalerno.Models.Note;

import javax.sound.midi.ShortMessage;
import java.util.ArrayList;


/**
 * This class controls all the Actions directly related to the MidiPlayer
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */

public class CompositionPlayer {

    /**
     * The midi player for playing audible notes.
     */
    private MidiPlayer midiPlayer = new MidiPlayer(100, 60);

    /**
     * Constructor
     */
    public CompositionPlayer() {
        super();
    }

    /**
     * Plays a song with given notes.
     * @param notes Notes to play.
     */
    public void play(ArrayList<Note> notes) {
        midiPlayer.stop();
        midiPlayer.clear();
        buildSong(notes);
        midiPlayer.play();
    }

    /**
     * Stops playing music.
     */
    public void stop() {
        midiPlayer.stop();
    }

    /**
     * Builds a song with given notes and adds them to the midiplayer.
     * @param notes Notes to build the song with
     */
    private void buildSong(ArrayList<Note> notes) {
        addProgramChanges(midiPlayer);
        for (Note note : notes) {
            midiPlayer.addNote(note.getPitch(), note.getVolume(), note.getStartTick(), note.getDuration(),
                    note.getChannel(), note.getTrackIndex());
        }
    }

    /**
     * Maps instruments to a given channel in the MIDI sounds player
     *f
     * @param midiPlayer MIDI sounds player
     */
    private void addProgramChanges(MidiPlayer midiPlayer) {
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE, 0, 0, 0, 0);
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 1, 6, 0, 0, 0);
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 2, 12, 0, 0, 0);
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 3, 19, 0, 0, 0);
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 4, 21, 0, 0, 0);
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 5, 25, 0, 0, 0);
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 6, 40, 0, 0, 0);
        midiPlayer.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 7, 60, 0, 0, 0);
    }

}
