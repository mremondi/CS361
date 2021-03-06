/*
 * File: CompositionPlayer.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno;

import proj10BeckChanceRemondiSalerno.Models.MidiPlayer;
import proj10BeckChanceRemondiSalerno.Models.Note;
import proj10BeckChanceRemondiSalerno.Models.NoteGroupable;

import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;


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
     *
     * @param notes     Notes to play.
     * @param startTick The start location to play from
     */
    public void play(Iterable<Note> notes, long startTick) {
        midiPlayer.stop();
        midiPlayer.clear();
        buildSong(notes);
        midiPlayer.setTickPosition(startTick);
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
     *
     * @param notes Notes to build the song with
     */
    private void buildSong(Iterable<Note> notes) {
        addProgramChanges(midiPlayer);
        for (Note note : notes) {
            midiPlayer.addNote(note.getPitch(), note.getVolume(), note.getStartTick(),
                    note.getDuration(),
                    note.getChannel(), note.getTrackIndex());
        }
    }

    /**
     * Maps instruments to a given channel in the MIDI sounds player
     * f
     *
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
