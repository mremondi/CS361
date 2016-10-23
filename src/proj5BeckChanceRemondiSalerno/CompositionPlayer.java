/*
 * File: CompositionPlayer.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 5
 * Due Date: October 23, 2016
 */

package proj5BeckChanceRemondiSalerno;

import proj5BeckChanceRemondiSalerno.Models.MidiPlayer;
import proj5BeckChanceRemondiSalerno.Models.Note;

import javax.sound.midi.ShortMessage;
import java.util.ArrayList;

/**
 * Created by Graham on 10/23/16.
 */
public class CompositionPlayer {

    private MidiPlayer midiPlayer = new MidiPlayer(100, 60);

    public CompositionPlayer() {
        super();
    }

    public void play(ArrayList<Note> notes) {
        System.out.format("playing with %d notes", notes.size());
        midiPlayer.stop();
        midiPlayer.clear();
        buildSong(notes);
        midiPlayer.play();
    }

    public void stop() {
        midiPlayer.stop();
    }


    public void buildSong(ArrayList<Note> notes) {
        addProgramChanges(midiPlayer);
        for (Note note : notes) {
            System.out.format("p: %d, st: %d\n", note.getPitch(), note.getStartTick());
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
