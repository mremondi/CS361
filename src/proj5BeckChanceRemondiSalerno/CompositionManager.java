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
import proj5BeckChanceRemondiSalerno.Controllers.TempoLineController;
import proj5BeckChanceRemondiSalerno.Models.*;
import proj5BeckChanceRemondiSalerno.Views.NoteGroupablePane;
import proj5BeckChanceRemondiSalerno.Views.NoteRectangle;
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

    private HashMap<NoteGroupable, NoteGroupablePane> noteGroupableRectsMap = new HashMap<>();
    private TempoLineController tempoLineController;
    private Pane composition;
    private int currentSelectedInstrumentIndex;
    private CompositionPlayer compositionPlayer = new CompositionPlayer();

    private CompositionManager() {}

    public static synchronized CompositionManager getInstance(){
        if (instance == null){
            instance = new CompositionManager();
        }
        return instance;
    }

    public void setTempoLineController(TempoLineController line){
        this.tempoLineController = line;
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
    public NoteGroupable addNoteToComposition(double xPos, double yPos) {
        System.out.format("adding note at location (%f, %f)\n", xPos, yPos);
        if (yPos >= 0 && yPos < 1280) {
            Note note = new Note(xPos, yPos, 100, currentSelectedInstrumentIndex);
            addGroupable(note);
            selectGroupable(note);
            return note;
        }
        return null;
    }

    public void addGroupable(NoteGroupable noteGroupable) {
        NoteGroupablePane groupPane = createNoteGroupablePane(noteGroupable);
        noteGroupableRectsMap.put(noteGroupable, groupPane);
        composition.getChildren().add(groupPane);
    }

    public Optional<NoteGroup> createNoteGroup() {
        System.out.println("grouping");
        ArrayList<NoteGroupable> notesToGroup = new ArrayList<>();
        for (NoteGroupable note : getGroupables()) {
            if (note.isSelected()) {
                notesToGroup.add(note);
            }
        }
        if (notesToGroup.size() < 2) {
             return Optional.empty();
        }
        for (NoteGroupable noteGroupable : notesToGroup) {
            composition.getChildren().remove(noteGroupableRectsMap.get(noteGroupable));
            noteGroupableRectsMap.remove(noteGroupable);
        }

        NoteGroup group = new NoteGroup(notesToGroup);
        NoteGroupablePane rect = createNoteGroupablePane(group);
        noteGroupableRectsMap.put(group, rect);
        composition.getChildren().add(rect);
        selectGroupable(group);

        return Optional.of(group);
    }

    public NoteGroupablePane createNoteGroupablePane(NoteGroupable noteGroupable) {
        NoteGroupablePane groupRect = new NoteGroupablePane();
        groupRect.setMinWidth(noteGroupable.getEndTick() - noteGroupable.getStartTick());
        groupRect.setMinHeight(noteGroupable.getMaxPitch() - noteGroupable.getMinPitch() + 10);
        int x = noteGroupable.getStartTick();
        int y = (127- noteGroupable.getMaxPitch()) * 10;
        System.out.format("adding notegroup pane at (%d, %d)\n", x,y);
        groupRect.setLayoutX(x);
        groupRect.setLayoutY(y);

        if (noteGroupable instanceof Note) {
            Note note = (Note) noteGroupable;
            NoteRectangle noteBox = new NoteRectangle(noteGroupable.getDuration(),0,0);
            noteBox.setFill(getInstrumentColor(note.getChannel()));
            groupRect.getChildren().add(noteBox);
            groupRect.setContainsSingleNote(true);
            return groupRect;
        } else {
            NoteGroup group = (NoteGroup) noteGroupable;
            for (NoteGroupable subNoteGroupable : group.getNoteGroupables()) {
                NoteGroupablePane subGroupRect = createNoteGroupablePane(subNoteGroupable);
                subGroupRect.setLayoutX(subGroupRect.getLayoutX()-groupRect.getLayoutX());
                subGroupRect.setLayoutY(subGroupRect.getLayoutY()-groupRect.getLayoutY());
                groupRect.getChildren().add(subGroupRect);
            }

            return groupRect;
        }

    }

    public void ungroupSelectedGroups() {
        ArrayList<NoteGroupable> groupsToUnGroup = new ArrayList<>();
        for (NoteGroupable noteGroupable : noteGroupableRectsMap.keySet()) {
            if (noteGroupable.isSelected() && noteGroupable instanceof NoteGroup) {
                groupsToUnGroup.add(noteGroupable);
            }
        }

        for (NoteGroupable noteGroupable : groupsToUnGroup) {
            ArrayList<NoteGroupable> subNoteGroupables = ((NoteGroup) noteGroupable).getNoteGroupables();

            for (NoteGroupable subNoteGroupable : subNoteGroupables) {
                addGroupable(subNoteGroupable);
            }
            composition.getChildren().remove(noteGroupableRectsMap.get(noteGroupable));
            noteGroupableRectsMap.remove(noteGroupable);
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
     * Gets the list of notes.
     *
     * @return ArrayList of MusicalNotes
     */
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (NoteGroupable group : noteGroupableRectsMap.keySet()) {
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
    public Optional<NoteGroupable> getGroupableAtPoint(double x, double y) {
        for (NoteGroupable noteGroupable : this.getGroupables()) {
            if (getGroupPane(noteGroupable).getIsInBounds(x, y)) {
                return Optional.of(noteGroupable);
            }
        }
        return Optional.empty();
    }

    public Set<NoteGroupable> getGroupables() {
        return noteGroupableRectsMap.keySet();
    }


    public void selectGroupable(NoteGroupable noteGroupable) {
        noteGroupable.setSelected(true);
        noteGroupableRectsMap.get(noteGroupable).setSelected(true);
    }

    public void unselectNote(NoteGroupable noteGroupable){
        noteGroupable.setSelected(false);
        noteGroupableRectsMap.get(noteGroupable).setSelected(false);
    }

    public NoteGroupablePane getGroupPane(NoteGroupable noteGroupable){
        return noteGroupableRectsMap.get(noteGroupable);
    }

    public void selectNotesIntersectingRectangle(Bounds bounds) {
        for (NoteGroupable noteGroupable : getGroupables()) {
            if (getGroupPane(noteGroupable).getIsInRectangleBounds(bounds.getMinX(), bounds.getMinY(),
                    bounds.getMaxX(), bounds.getMaxY())) {
                selectGroupable(noteGroupable);
            }
        }
    }

    /**
     * Clears the list of selected notes
     */
    public void clearSelectedNotes() {
        for (NoteGroupable noteGroupable : noteGroupableRectsMap.keySet()) {
            if (noteGroupable.isSelected()){
                this.unselectNote(noteGroupable);
            }
        }
    }

    /**
     * Plays the sequence of notes and animates the TempoLine.
     */
    public void play(){
        compositionPlayer.play(getNotes());
        double stopTime = this.calculateStopTime();
        this.tempoLineController.updateTempoLine(stopTime);
        this.tempoLineController.playAnimation();
    }

    /**
     * Stops the midiPlayer and hides the tempoLine.
     */
    public void stop(){
        compositionPlayer.stop();
        this.tempoLineController.stopAnimation();
        this.tempoLineController.hideTempoLine();
    }

    public void deleteSelectedGroups() {
        ArrayList<NoteGroupable> groupablesToDelete = new ArrayList<>();
        for (NoteGroupable noteGroupable : getGroupables()) {
            if (noteGroupable.isSelected()) {
                NoteGroupablePane groupPane = noteGroupableRectsMap.get(noteGroupable);
                composition.getChildren().remove(groupPane);
                groupablesToDelete.add(noteGroupable);
            }
        }

        for (NoteGroupable noteGroupable : groupablesToDelete) {
            noteGroupableRectsMap.remove(noteGroupable);
        }
    }


}
