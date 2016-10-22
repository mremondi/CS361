/*
 * File: CompositionManager.java
 * Names: Graham Chance, Jenny Lin, Ana Sofia Solis Canales, Mike Remondi
 * Class: CS361
 * Project: 4
 * Due Date: October 11, 2016
 */

package proj5BeckChanceRemondiSalerno;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import proj5BeckChanceRemondiSalerno.Models.MidiPlayer;
import proj5BeckChanceRemondiSalerno.Models.Note;
import proj5BeckChanceRemondiSalerno.Models.TempoLine;
import proj5BeckChanceRemondiSalerno.Views.NoteRectangle;

import javax.sound.midi.ShortMessage;
import java.util.*;

/**
 * This class models a composition sheet manager.
 * Deals with adding and manipulating all the data
 * regarding the composition
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class CompositionManager {

    private static CompositionManager instance = null;

    private MidiPlayer midiPlayer = new MidiPlayer(100, 60);
    private HashMap<Note, NoteRectangle> noteRectangleMap;
    private TempoLine tempoLine;
    private Pane composition;
    private Paint instrumentColor;
    private Hashtable<Paint, Integer> channelMapping;

    private CompositionManager() {
        this.noteRectangleMap = new HashMap<>();
        this.channelMapping = new Hashtable<>();
        setChannelMapping();
    }

    public static synchronized CompositionManager getInstance(){
        if (instance == null){
            instance = new CompositionManager();
        }
        return instance;
    }

    public void setTempoLine(TempoLine line){
        this.tempoLine = line;
    }

    public void setComposition(Pane composition){
        this.composition = composition;
    }

    public HashMap<Note, NoteRectangle> getNoteRectangleMap(){
        return this.noteRectangleMap;
    }

    public Pane getComposition(){
        return this.composition;
    }
    /**
     * Maps channel numbers to specific instrument color association
     */
    public void setChannelMapping() {
        this.channelMapping.put(Color.GRAY, 0);
        this.channelMapping.put(Color.GREEN, 1);
        this.channelMapping.put(Color.BLUE, 2);
        this.channelMapping.put(Color.GOLDENROD, 3);
        this.channelMapping.put(Color.MAGENTA, 4);
        this.channelMapping.put(Color.DEEPSKYBLUE, 5);
        this.channelMapping.put(Color.BLACK, 6);
        this.channelMapping.put(Color.BROWN, 7);
    }

    /**
     * Updates the current color associated with an instrument
     *
     * @param newInstrumentColor new color associated with new instrument
     */
    public void changeInstrument(Paint newInstrumentColor) {
        this.instrumentColor = newInstrumentColor;
    }

    /**
     * Retrieves the channel number associated with the color of
     * the current instrument
     *
     * @param instrumentColor Text color of the instrument
     * @return Channel number which corresponds to the insturment color
     */
    public int getChannelNumber(Paint instrumentColor) {
        return this.channelMapping.get(instrumentColor);
    }


    /**
     * Creates a visual representation of the the notes
     * in the composition sheet.
     *
     * @param xPos the input x position of the note
     * @param yPos the input y position of the note
     *
     * @return the note added
     */
    public Note addNoteToComposition(double xPos, double yPos) {
        if (yPos >= 0 && yPos < 1280) {
            NoteRectangle noteRectangle = createNoteRectangle(xPos, yPos);
            Note note = new Note(xPos, yPos, 100, getChannelNumber(noteRectangle.getFill()));
            this.noteRectangleMap.put(note, noteRectangle);
            selectNote(note); // TODO:
            return note;
        }
        return null;
    }

    public NoteRectangle createNoteRectangle(double x, double y){
        Rectangle noteBox = new Rectangle(100.0, 10.0);
        noteBox.getStyleClass().add("note");
        noteBox.setX(x);
        noteBox.setY(y - (y % 10));
        noteBox.setFill(this.instrumentColor);
        NoteRectangle noteRectangle = new NoteRectangle(noteBox);
        this.composition.getChildren().add(noteBox);
        return noteRectangle;
    }

    /**
     * Adds the note to the sound player
     *
     * @param midiPlayer MIDI sound player
     */
    public void buildSong(MidiPlayer midiPlayer) {
        addProgramChanges(midiPlayer);
        double stopTime = 0.0;
        for (Note note : this.getNotes()) {
            midiPlayer.addNote(note.getPitch(), note.getVolume(), note.getStartTick(), note.getDuration(),
                                note.getChannel(), note.getTrackIndex());
        }
    }

    /**
     * Calculates the stop time for the composition created
     *
     * @return stopTime
     */
    public double calculateStopTime() {
        double stopTime = 0.0;
        for (Note note : this.getNotes()) {
            if (stopTime < note.getStartTick() + note.getDuration()) {
                stopTime = note.getStartTick() + note.getDuration();
            }
        }
        return stopTime;
    }

    /**
     * Checks if note is in composition at the given location
     *
     * @return true or false in composition
     */
    private boolean noteExistsAtCoordinates(double x, double y) {
        for (Note note : this.getNotes()) {
            if (getNoteRectangle(note).getIsInBounds(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of notes.
     *
     * @return ArrayList of MusicalNotes
     */
    public Set<Note> getNotes() {
        return this.noteRectangleMap.keySet();
    }

    /**
     * Finds a Note, if one exists, where the mouse click is inside of
     * its rectangle.
     *
     * @param x MouseEvent x coordinate
     * @param y MouseEvent y coordinate
     */
    public Optional<Note> getNoteAtPoint(double x, double y) {
        for (Note note : this.getNotes()) {
            if (getNoteRectangle(note).getIsInBounds(x, y)) {
                return Optional.of(note);
            }
        }
        return Optional.empty();
    }

    /**
     * Sets the given note to selected and adds it to the selectedNotes list.
     *
     * @param note A Note to be selected
     */
    public void selectNote(Note note) {
        note.select();
        this.noteRectangleMap.get(note).select();
    }

    /**
     * Sets the given note to unselected and removes it from the selectedNotes list.
     *
     * @param note
     */
    public void unselectNote(Note note){
        note.unselect();
        this.noteRectangleMap.get(note).unselect();
    }

    public NoteRectangle getNoteRectangle(Note note){
        return this.noteRectangleMap.get(note);
    }

    public void selectNotesIntersectingRectangle(Bounds bounds) {
        for (Note note : this.getNotes()) {
            if (getNoteRectangle(note).getIsInRectangleBounds(bounds.getMinX(), bounds.getMinY(),
                    bounds.getMaxX(), bounds.getMaxY())) {
                selectNote(note);
            }
        }
    }

    /**
     * Clears the list of selected notes
     */
    public void clearSelectedNotes() {
        for (Note note : this.getNotes()) {
            if (note.isSelected()){
                this.unselectNote(note);
            }
        }
    }

    /**
     * Plays the sequence of notes and animates the TempoLine.
     */
    public void play(){
        this.midiPlayer.stop();
        this.midiPlayer.clear();
        this.buildSong(this.midiPlayer);
        double stopTime = this.calculateStopTime();
        this.tempoLine.updateTempoLine(stopTime);
        playMusicAndAnimation();
    }

    /**
     * Stops the midiPlayer and hides the tempoLine.
     */
    public void stop(){
        this.midiPlayer.stop();
        this.tempoLine.stopAnimation();
        this.tempoLine.hideTempoLine();
    }

    public void deleteSelectedNotes() {
        ArrayList<Note> notesToDelete = new ArrayList<>();
        for (Note note : noteRectangleMap.keySet()) {
            if (note.isSelected()) {
                NoteRectangle noteRectangle = noteRectangleMap.get(note);
                composition.getChildren().remove(noteRectangle.getNoteBox());
                notesToDelete.add(note);
            }
        }

        for (Note note : notesToDelete) {
            noteRectangleMap.remove(note);
        }
    }

    /**
     * starts the reproduction  of the composition
     */
    private void playMusicAndAnimation() {
        this.tempoLine.playAnimation();
        this.midiPlayer.play();
    }

    /**
     * Maps instruments to a given channel in the MIDI sounds player
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

    /**
     * The possible set of directions to resize a note.
     */
    private enum ResizeDirection {
        RIGHT, NONE;
    }
}
