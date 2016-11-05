/*
 * File: MidiPlayer.java
 * Names: Graham Chance, Ryan Salerno, Charlie Beck, Mike Remondi, Dale Skrien
 * Class: CS361 
 * Project: 5
 * Date: October 23, 2016
 */

package proj7BeckChanceRemondiSalerno.Models;

import javax.sound.midi.*;

/**
 * This class has a simple interface for playing MIDI sounds.
 *
 * @author Dale Skrien
 */
public class MidiPlayer {
    private static final int NUM_TRACKS = 8;

    /**
     * the sequencer that stores Midi events and plays them when requested
     */
    private Sequencer sequencer;

    /**
     * the number of beats per minute that is used when a sound is played
     */
    private int beatsPerMinute;

    /**
     * Creates a new proj3RinkerSolisSalernoPatrizio.MidiPlayer with the given parameters.
     *
     * @param resolution     the number of ticks per beat
     * @param beatsPerMinute the number of beats per minute
     */
    public MidiPlayer(int resolution, int beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
        try {
            sequencer = MidiSystem.getSequencer(); // factory
            sequencer.open();
            Sequence sequence = new Sequence(Sequence.PPQ,
                    resolution, NUM_TRACKS);
            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(beatsPerMinute);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the number of ticks per beat
     */
    public int getResolution() {
        return sequencer.getSequence().getResolution();
    }

    /**
     * adds a new MidiEvent to the current composition.
     * Assumes all parameters are legal values (in the appropriate range).
     * This method is very low level but gives you greater freedom to
     * add almost any kind of Midi event you wish.
     *
     * @param status     the integer value of the status byte
     * @param data1      the integer value of the data1 byte
     * @param data2      the integer value of the data2 byte
     * @param startTick  the starting time of the event in ticks
     * @param trackIndex the index of the track to which the new event will be
     *                   added
     */
    public void addMidiEvent(int status, int data1, int data2, double startTick,
                             int trackIndex) {
        Track track = sequencer.getSequence().getTracks()[trackIndex];
        ShortMessage message = new ShortMessage();
        try {
            message.setMessage(status, data1, data2);
            MidiEvent event = new MidiEvent(message, (long)startTick);
            track.add(event);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * A convenience method for adding notes to the composition.
     *
     * @param pitch      an integer from 0 to 127 giving the pitch
     * @param volume     an integer from 0 to 127 giving the volume
     * @param startTick  tells when the note is to start playing (in ticks)
     * @param duration   the number of ticks the note is to play
     * @param channel    an integer from 0 to 15; each channel typically
     *                   corresponds to a different instrument.  The default
     *                   instrument for all channels is the grand piano.
     * @param trackIndex an integer from 0 to 7 giving the track for the note
     */
    public void addNote(int pitch, int volume, int startTick, double duration,
                        int channel, int trackIndex) {
        addMidiEvent(ShortMessage.NOTE_ON + channel, pitch, volume,
                startTick, trackIndex);
        addMidiEvent(ShortMessage.NOTE_OFF + channel, pitch, volume,
                startTick + duration, trackIndex);
    }

    /**
     * plays all the Midi events in all the tracks of this composition
     * immediately
     */
    public void play() {

        sequencer.setTempoInBPM(beatsPerMinute);
        sequencer.start();
    }

    public boolean getIsPlaying() {
        return sequencer.isRunning();
    }

    /**
     * stops all the Midi events currently playing or yet to be played
     */
    public void stop() {
        sequencer.stop();
    }

    /**
     * removes all Midi events from the current composition.
     */
    public void clear() {
        try {
            sequencer.setSequence(new Sequence(Sequence.PPQ,
                    getResolution(), NUM_TRACKS));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
