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
import proj5BeckChanceRemondiSalerno.Views.NoteGroupablePane;
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
    private HashMap<Groupable, NoteGroupablePane> noteGroupableRectsMap = new HashMap<>();
    private TempoLine tempoLine;
    private Pane composition;
    private int currentSelectedInstrumentIndex;
    private Hashtable<Integer, Paint> channelMapping  = new Hashtable<>();

    private CompositionManager() {}

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
    public Paint getInstrumentColor(int instrumentIndex) {
        switch (instrumentIndex) {
            case 0:
                return Color.GRAY;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GOLDENROD;
            case 4:
                return Color.MAGENTA;
            case 5:
                return Color.DEEPSKYBLUE;
            case 6:
                return Color.BLACK;
            case 7:
                return Color.BROWN;
        }
        return null;
    }


    public void changeInstrument(int newInstrumentIndex) {
        currentSelectedInstrumentIndex = newInstrumentIndex;
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
            Note note = new Note(xPos, yPos, 100, currentSelectedInstrumentIndex);
            addGroupable(note);
            selectGroupable(note);
            return note;
        }
        return null;
    }

    public void addGroupable(Groupable groupable) {
        NoteGroupablePane groupPane = createNoteGroupablePane(groupable);
        noteGroupableRectsMap.put(groupable, groupPane);
        composition.getChildren().add(groupPane);
    }

    public Optional<NoteGroup> createNoteGroup() {
        System.out.println("grouping");
        ArrayList<Groupable> notesToGroup = new ArrayList<>();
        for (Groupable note : getGroupables()) {
            if (note.isSelected()) {
                notesToGroup.add(note);
            }
        }
        if (notesToGroup.size() < 2) {
             return Optional.empty();
        }
        for (Groupable groupable : notesToGroup) {
            composition.getChildren().remove(noteGroupableRectsMap.get(groupable));
            noteGroupableRectsMap.remove(groupable);
        }

        NoteGroup group = new NoteGroup(notesToGroup);
        NoteGroupablePane rect = createNoteGroupablePane(group);
        noteGroupableRectsMap.put(group, rect);
        composition.getChildren().add(rect);
        selectGroupable(group);

        return Optional.of(group);
    }

    public NoteGroupablePane createNoteGroupablePane(Groupable groupable) {
        NoteGroupablePane groupRect = new NoteGroupablePane();
        groupRect.setMinWidth(groupable.getEndTick() - groupable.getStartTick());
        groupRect.setMinHeight(groupable.getMaxPitch() - groupable.getMinPitch() + 10);
        int x = groupable.getStartTick();
        int y = (127-groupable.getMaxPitch()) * 10;
        System.out.format("adding notegroup pane at (%d, %d)\n", x,y);
        groupRect.setLayoutX(x);
        groupRect.setLayoutY(y);

        if (groupable instanceof Note) {
            Note note = (Note)groupable;
            NoteRectangle noteBox = new NoteRectangle(groupable.getDuration(),0,0);
            noteBox.setFill(getInstrumentColor(note.getChannel()));
            groupRect.getChildren().add(noteBox);
            groupRect.setContainsSingleNote(true);
            return groupRect;
        } else {
            NoteGroup group = (NoteGroup)groupable;
            for (Groupable subGroupable : group.getGroupables()) {
                NoteGroupablePane subGroupRect = createNoteGroupablePane(subGroupable);
                subGroupRect.setLayoutX(subGroupRect.getLayoutX()-groupRect.getLayoutX());
                subGroupRect.setLayoutY(subGroupRect.getLayoutY()-groupRect.getLayoutY());
                groupRect.getChildren().add(subGroupRect);
            }

            return groupRect;
        }

    }

    public void ungroupSelectedGroups() {
        ArrayList<Groupable> groupsToUnGroup = new ArrayList<>();
        for (Groupable groupable : noteGroupableRectsMap.keySet()) {
            if (groupable.isSelected() && groupable instanceof NoteGroup) {
                groupsToUnGroup.add(groupable);
            }
        }

        for (Groupable groupable : groupsToUnGroup) {
            ArrayList<Groupable> subGroupables = ((NoteGroup)groupable).getGroupables();

            for (Groupable subGroupable : subGroupables) {
                addGroupable(subGroupable);
            }
            composition.getChildren().remove(noteGroupableRectsMap.get(groupable));
            noteGroupableRectsMap.remove(groupable);
        }
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
        for (Groupable group : noteGroupableRectsMap.keySet()) {
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
        return noteGroupableRectsMap.keySet();
    }


    public void selectGroupable(Groupable groupable) {
        groupable.setSelected(true);
        noteGroupableRectsMap.get(groupable).setSelected(true);
    }

    public void unselectNote(Groupable groupable){
        groupable.setSelected(false);
        noteGroupableRectsMap.get(groupable).setSelected(false);
    }

    public NoteGroupablePane getGroupPane(Groupable groupable){
        return noteGroupableRectsMap.get(groupable);
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
        for (Groupable groupable : noteGroupableRectsMap.keySet()) {
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
                NoteGroupablePane groupPane = noteGroupableRectsMap.get(groupable);
                composition.getChildren().remove(groupPane);
                groupablesToDelete.add(groupable);
            }
        }

        for (Groupable groupable : groupablesToDelete) {
            noteGroupableRectsMap.remove(groupable);
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
