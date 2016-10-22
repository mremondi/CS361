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
import proj5BeckChanceRemondiSalerno.Models.*;
import proj5BeckChanceRemondiSalerno.Views.NoteGroupRectangle;
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
    private HashMap<Groupable, NoteGroupRectangle> groupablesRectMap = new HashMap<>();
    private TempoLine tempoLine;
    private Pane composition;
    private Paint instrumentColor;
    private Hashtable<Paint, Integer> channelMapping  = new Hashtable<>();

    private CompositionManager() {
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
    public Groupable addNoteToComposition(double xPos, double yPos) {
        System.out.format("adding note at location (%f, %f)\n", xPos, yPos);
        if (yPos >= 0 && yPos < 1280) {
            Note note = new Note(xPos, yPos, 100, getChannelNumber(this.instrumentColor));
            addGroupable(note);
            selectGroupable(note);
            return note;
        }
        return null;
    }

    public void addGroupable(Groupable groupable) {
        NoteGroupRectangle groupPane = createNoteGroupPane(groupable);
        groupPane.setContainsSingleNote(groupable.getNotes().size() == 1);
        groupablesRectMap.put(groupable, groupPane);
        composition.getChildren().add(groupPane);
    }

    public NoteGroup createNoteGroup() {
        System.out.println("grouping");
        ArrayList<Groupable> notesToGroup = new ArrayList<>();
        for (Note note : getNotes()) {
            if (note.isSelected()) {
                notesToGroup.add(note);
            }
        }

        for (Groupable groupable : notesToGroup) {
            composition.getChildren().remove(groupablesRectMap.get(groupable));
            groupablesRectMap.remove(groupable);
        }

        NoteGroup group = new NoteGroup(notesToGroup);
        NoteGroupRectangle rect = createNoteGroupPane(group);
        groupablesRectMap.put(group, rect);
        composition.getChildren().add(rect);
        selectGroupable(group);

        return group;
    }

    public NoteGroupRectangle createNoteGroupPane(Groupable groupable) {
        NoteGroupRectangle groupRect = new NoteGroupRectangle();
        groupRect.setMinWidth(groupable.getEndTick() - groupable.getStartTick());
        groupRect.setMinHeight(groupable.getMaxPitch() - groupable.getMinPitch() + 10);
        int x = groupable.getStartTick();
        int y = (127-groupable.getMaxPitch()) * 10;
        System.out.format("adding notegroup pane at (%d, %d)\n", x,y);
        groupRect.setLayoutX(x);
        groupRect.setLayoutY(y);

        if (groupable instanceof Note) {
            groupRect.getChildren().add(createSingleNoteRectangle(0,0));
            return groupRect;
        } else {
            NoteGroup group = (NoteGroup)groupable;
            for (Groupable subGroupable : group.getGroups()) {
                NoteGroupRectangle subGroupRect = createNoteGroupPane(subGroupable);
                subGroupRect.setLayoutX(subGroupRect.getLayoutX()-groupRect.getLayoutX());
                subGroupRect.setLayoutY(subGroupRect.getLayoutY()-groupRect.getLayoutY());
                groupRect.getChildren().add(subGroupRect);
            }

            return groupRect;
        }

    }

    public void ungroupSelectedGroups() {
        ArrayList<Groupable> groupsToUnGroup = new ArrayList<>();
        for (Groupable groupable : groupablesRectMap.keySet()) {
            if (groupable.isSelected() && groupable instanceof NoteGroup) {
                groupsToUnGroup.add(groupable);
            }
        }

        for (Groupable groupable : groupsToUnGroup) {
            ArrayList<Groupable> subGroupables = ((NoteGroup)groupable).getGroups();
            for (Groupable subGroupable : subGroupables) {
                addGroupable(subGroupable);
            }
            composition.getChildren().remove(groupablesRectMap.get(groupable));
            groupablesRectMap.remove(groupable);
        }
    }

    private NoteRectangle createSingleNoteRectangle(double x, double y){
        NoteRectangle noteBox = new NoteRectangle(x,y);
        noteBox.setFill(instrumentColor);
        return noteBox;
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
            System.out.format("p: %d, st: %d\n", note.getPitch(), note.getStartTick());
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
    private boolean groupableExistsAtCoordinates(double x, double y) {
        for (Groupable groupable : this.getGroupables()) {
            if (getGroupPane(groupable).getIsInBounds(x, y)) {
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
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (Groupable group : groupablesRectMap.keySet()) {
            for (Note note : group.getNotes()) {
                notes.add(note);
            }
        }
        return notes;
    }

    /**
     * Finds a Note, if one exists, where the mouse click is inside of
     * its rectangle.
     *
     * @param x MouseEvent x coordinate
     * @param y MouseEvent y coordinate
     */
    public Optional<Groupable> getGroupableAtPoint(double x, double y) {
        for (Groupable groupable : this.getGroupables()) {
            if (getGroupPane(groupable).getIsInBounds(x, y)) {
                return Optional.of(groupable);
            }
        }
        return Optional.empty();
    }

    public Set<Groupable> getGroupables() {
        return groupablesRectMap.keySet();
    }


    public void selectGroupable(Groupable groupable) {
        groupable.setSelected(true);
        groupablesRectMap.get(groupable).setSelected(true);
    }

    public void unselectNote(Groupable groupable){
        groupable.setSelected(false);
        groupablesRectMap.get(groupable).setSelected(false);
    }

    public NoteGroupRectangle getGroupPane(Groupable groupable){
        return groupablesRectMap.get(groupable);
    }

    public void selectNotesIntersectingRectangle(Bounds bounds) {
        for (Groupable groupable : getGroupables()) {
            if (getGroupPane(groupable).getIsInRectangleBounds(bounds.getMinX(), bounds.getMinY(),
                    bounds.getMaxX(), bounds.getMaxY())) {
                selectGroupable(groupable);
            }
        }
    }

    /**
     * Clears the list of selected notes
     */
    public void clearSelectedNotes() {
        for (Groupable groupable : groupablesRectMap.keySet()) {
            if (groupable.isSelected()){
                this.unselectNote(groupable);
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

    public void deleteSelectedGroups() {
        ArrayList<Groupable> groupablesToDelete = new ArrayList<>();
        for (Groupable groupable : getGroupables()) {
            if (groupable.isSelected()) {
                NoteGroupRectangle groupPane = groupablesRectMap.get(groupable);
                composition.getChildren().remove(groupPane);
                groupablesToDelete.add(groupable);
            }
        }

        for (Groupable groupable : groupablesToDelete) {
            groupablesRectMap.remove(groupable);
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

}
