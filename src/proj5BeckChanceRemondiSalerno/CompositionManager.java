/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj5BeckChanceRemondiSalerno;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import proj5BeckChanceRemondiSalerno.CompositionActions.*;
import proj5BeckChanceRemondiSalerno.Controllers.CompositionController;
import proj5BeckChanceRemondiSalerno.Controllers.MenuBarController;
import proj5BeckChanceRemondiSalerno.Controllers.TempoLineController;
import proj5BeckChanceRemondiSalerno.Models.Note;
import proj5BeckChanceRemondiSalerno.Models.NoteGroup;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj5BeckChanceRemondiSalerno.Views.NoteGroupablePane;
import proj5BeckChanceRemondiSalerno.Views.NoteRectangle;

import java.util.*;

/**
 * This class models a composition sheet manager.
 * Deals with adding and manipulating all the data
 * regarding the composition
 *
 * Singleton
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class CompositionManager {

    /**
     * The shared instance of the singleton
     */
    private static CompositionManager instance = null;

    /**
     * The map for matching note models to their views
     */
    private HashMap<NoteGroupable, NoteGroupablePane> noteGroupableRectsMap = new HashMap<>();

    /**
     * The controller for the tempo line
     */
    private TempoLineController tempoLineController;

    /**
     * The controller for the Composition Pane
     */
    private CompositionController compositionController;

    /**
     * The controller for the MenuBar
     */
    private MenuBarController menuBarController;


    /**
     * The index of the current selected instrument
     */
    private int currentSelectedInstrumentIndex;

    /**
     * The composition player for playing notes
     */
    private CompositionPlayer compositionPlayer = new CompositionPlayer();

    /**
     * The stack for remembering each CompositionAction for redoing.
     */
    private  Stack<CompositionAction> redoActions = new Stack<>();

    /**
     * The stack for remembering each CompositionAction for undoing.
     */
    private  Stack<CompositionAction> undoActions = new Stack<>();

    /**
     * constructor
     * hidden from public to enforce singleton
     */
    private CompositionManager() {}

    /**
     * shared instance getter
     * @return The single shared instance
     */
    public static synchronized CompositionManager getInstance(){
        if (instance == null){
            instance = new CompositionManager();
        }
        return instance;
    }

    /**
     * Setter for tempo line controller
     * @param tempoLineControllerontroller the new tempo line controller
     */
    public void setTempoLineController(TempoLineController tempoLineControllerontroller){
        this.tempoLineController = tempoLineControllerontroller;
    }

    /**
     * Setter for the composition controller
     *
     * @param compositionController the new composition controller
     */
    public void setCompositionController(CompositionController compositionController){
        this.compositionController = compositionController;
    }

    /**
     * Setter for the menu bar controller.
     *
     * @param menuBarController the new menu bar controller
     */
    public void setMenuBarController(MenuBarController menuBarController) {
        this.menuBarController = menuBarController;
    }

    /**
     * Gets the redo stack.
     *
     * @return the redo stack field
     */
    public Stack<CompositionAction> getRedoActions() {
        return redoActions;
    }

    /**
     * Gets the undo stack.
     *
     * @return the undo stack field
     */
    public Stack<CompositionAction> getUndoActions() {
        return undoActions;
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

    /**
     * Setter for current instrument
     * @param newInstrumentIndex The new instrument index
     */
    public void setInstrumentIndex(int newInstrumentIndex) {
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
        if (yPos >= 0 && yPos < 1280) {
            Note note = new Note(xPos, yPos, 100, currentSelectedInstrumentIndex);
            addGroupable(note);
            selectGroupable(note);
            AddNoteAction addNoteAction = new AddNoteAction(note);
            actionCompleted(addNoteAction);
            return note;
        }
        return null;
    }


    /**
     * Creates a note group from current selected groupables
     * @return
     */
    public Optional<NoteGroup> createNoteGroupWithSelectedNotes() {
        ArrayList<NoteGroupable> notesToGroup = new ArrayList<>();
        for (NoteGroupable note : getGroupables()) {
            if (note.isSelected()) {
                notesToGroup.add(note);
            }
        }
        Optional<NoteGroup> group = group(notesToGroup);
        if (group.isPresent()) {
            GroupAction groupAction = new GroupAction(group.get());
            actionCompleted(groupAction);
        }

        return group;
    }

    /**
     * Groups the selected notes.
     *
     * @param notesToGroup an arrayList of notes to group
     * @return an Optional that is either empty or has a NoteGroup
     */
    public Optional<NoteGroup> group(ArrayList<NoteGroupable> notesToGroup) {
        if (notesToGroup.size() < 2) {
            return Optional.empty();
        }
        for (NoteGroupable noteGroupable : notesToGroup) {
            compositionController.removeNotePane(noteGroupableRectsMap.get(noteGroupable));
            noteGroupableRectsMap.remove(noteGroupable);
        }

        NoteGroup group = new NoteGroup(notesToGroup);
        addGroupable(group);

        return Optional.of(group);
    }

    /**
     * Delegates the moving of notes to the Composition Controller.
     *
     * @param notes an ArrayList of notes to move
     * @param dx the change in x
     * @param dy the change in y
     */
    public void moveNotes(ArrayList<NoteGroupable> notes, double dx, double dy) {
        for (NoteGroupable note : notes) {
            compositionController.moveNote(note, dx, dy);
        }
    }

    /**
     * Delegates the resizing of notes to the Composition Controller.
     *
     * @param notes an ArrayList of notes to resize
     * @param dx the change in x
     */
    public void resizeNotes(ArrayList<NoteGroupable> notes, double dx) {
        for (NoteGroupable note : notes) {
            compositionController.resizeNote(note, dx);
        }
    }

    /**
     * Ungroups all current selected groups
     */
    public void ungroupSelectedGroup() {
        ArrayList<NoteGroupable> groupsToUnGroup = new ArrayList<>();
        for (NoteGroupable noteGroupable : noteGroupableRectsMap.keySet()) {
            if (noteGroupable.isSelected() && noteGroupable instanceof NoteGroup) {
                groupsToUnGroup.add(noteGroupable);
            }
        }

        if (groupsToUnGroup.size() != 1) { return; }
        NoteGroup group = (NoteGroup)groupsToUnGroup.get(0);
        ungroup(group);
        UngroupAction ungroupAction = new UngroupAction(group);
        actionCompleted(ungroupAction);
    }

    /**
     * Ungroups a NoteGroup.
     *
     * @param group the NoteGroup to ungroup
     */
    public void ungroup(NoteGroup group) {
        ArrayList<NoteGroupable> subNoteGroupables = ((NoteGroup) group).getNoteGroupables();

        compositionController.removeNotePane(noteGroupableRectsMap.get(group));
        noteGroupableRectsMap.remove(group);

        for (NoteGroupable subNoteGroupable : subNoteGroupables) {
            addGroupable(subNoteGroupable);
        }
    }

    /**
     * Adds the given CompositionAction to the undoActions stack and
     * empties the redo stack.
     *
     * @param action the CompositionAction that is completed.
     */
    public void actionCompleted(CompositionAction action) {
        undoActions.push(action);
        redoActions.removeAllElements();
        updateRedoUndoDisabled();
    }

    /**
     * Undoes the CompositionAction at the top of the undo stack and pushes
     * it onto the redo stack.
     */
    public void undoLastAction() {
        CompositionAction action = undoActions.pop();
        action.undo();
        redoActions.push(action);
        updateRedoUndoDisabled();
    }

    /**
     * Redoes the Composition at the top of the redo stack.
     */
    public void redoLastUndoneAction() {
        redoActions.pop().redo();
        updateRedoUndoDisabled();
    }

    /**
     * Sets the menu options for redo/undo to disabled if there is nothing
     * in the respective stack.
     */
    private void updateRedoUndoDisabled() {
        menuBarController.setRedoDisabled(redoActions.empty());
        menuBarController.setUndoDisabled(undoActions.empty());
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

    /**
     * Getter for the groupable models
     * @return All groupable models in composition
     */
    public Set<NoteGroupable> getGroupables() {
        return noteGroupableRectsMap.keySet();
    }


    /**
     * Selects a note groupable
     * @param noteGroupable The groupable to select
     */
    public void selectGroupable(NoteGroupable noteGroupable) {
        noteGroupable.setSelected(true);
        noteGroupableRectsMap.get(noteGroupable).setSelected(true);
    }

    /**
     * Deselects a note groupable
     * @param noteGroupable The groupable to deselect
     */
    public void deselectNote(NoteGroupable noteGroupable){
        noteGroupable.setSelected(false);
        noteGroupableRectsMap.get(noteGroupable).setSelected(false);
    }

    /**
     * Getter for group pane corresponding to a groupable
     * @param noteGroupable The groupable to get the view for
     * @return The view for the groupable
     */
    public NoteGroupablePane getGroupPane(NoteGroupable noteGroupable){
        return noteGroupableRectsMap.get(noteGroupable);
    }

    /**
     * Selects the notes if they are in the selection box.
     *
     * @param bounds the x and y coordinates of the selection box
     * @return an ArrayList of the selected NoteGroupables
     */
    public ArrayList<NoteGroupable> selectNotesIntersectingRectangle(Bounds bounds) {
        ArrayList<NoteGroupable> selectedNotes = new ArrayList();
        for (NoteGroupable noteGroupable : getGroupables()) {
            if (getGroupPane(noteGroupable).getIsInRectangleBounds(bounds.getMinX(), bounds.getMinY(),
                    bounds.getMaxX(), bounds.getMaxY())) {
                selectGroupable(noteGroupable);
                selectedNotes.add(noteGroupable);
            }
        }
        return selectedNotes;
    }

    /**
     * Clears the list of selected notes
     */
    public void clearSelectedNotes() {
        for (NoteGroupable noteGroupable : noteGroupableRectsMap.keySet()) {
            if (noteGroupable.isSelected()){
                this.deselectNote(noteGroupable);
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

    /**
     * Deletes selected groupables
     */
    public void deleteSelectedGroupables() {
        ArrayList<NoteGroupable> groupablesToDelete = new ArrayList<>();
        for (NoteGroupable noteGroupable : getGroupables()) {
            if (noteGroupable.isSelected()) {
                NoteGroupablePane groupPane = noteGroupableRectsMap.get(noteGroupable);
                groupablesToDelete.add(noteGroupable);
            }
        }

        deleteGroupables(groupablesToDelete);

        DeleteGroupablesAction deleteGroupableAction = new DeleteGroupablesAction(groupablesToDelete);
        actionCompleted(deleteGroupableAction);
    }

    /**
     * Deletes the specified groupables.
     *
     * @param groupables the ArrayList of NoteGroupables to delete
     */
    public void deleteGroupables(ArrayList<NoteGroupable> groupables) {
        for (NoteGroupable noteGroupable : groupables) {
            deleteGroupable(noteGroupable);
        }
    }

    /**
     * Deletes a single groupable.
     *
     * @param noteGroupable the NoteGroupable to delete
     */
    public void deleteGroupable(NoteGroupable noteGroupable) {
        NoteGroupablePane groupPane = noteGroupableRectsMap.get(noteGroupable);
        compositionController.removeNotePane(groupPane);
        noteGroupableRectsMap.remove(noteGroupable);
    }

    /**
     * Adds a
     * @param noteGroupable
     */
    public void addGroupable(NoteGroupable noteGroupable) {
        NoteGroupablePane groupPane = createNoteGroupablePane(noteGroupable);
        noteGroupableRectsMap.put(noteGroupable, groupPane);
        compositionController.addNotePane(groupPane);
        selectGroupable(noteGroupable);
    }

    /**
     * Gets the list of notes.
     *
     * @return ArrayList of MusicalNotes
     */
    private ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (NoteGroupable group : noteGroupableRectsMap.keySet()) {
            for (Note note : group.getNotes()) {
                notes.add(note);
            }
        }
        return notes;
    }

    /**
     * Calculates the stop time for the composition created
     *
     * @return stopTime
     */
    private double calculateStopTime() {
        double stopTime = 0.0;
        for (Note note : this.getNotes()) {
            if (stopTime < note.getStartTick() + note.getDuration()) {
                stopTime = note.getStartTick() + note.getDuration();
            }
        }
        return stopTime;
    }

    /**
     * Creates a new NoteGroupablePane based on a group
     * @param noteGroupable The groupable to create a pane for
     * @return the new groupable pane
     */
    private NoteGroupablePane createNoteGroupablePane(NoteGroupable noteGroupable) {
        NoteGroupablePane groupablePane = new NoteGroupablePane();
        groupablePane.setMinWidth(noteGroupable.getEndTick() - noteGroupable.getStartTick());
        groupablePane.setMinHeight(noteGroupable.getMaxPitch() - noteGroupable.getMinPitch() + 10);
        int x = noteGroupable.getStartTick();
        int y = (127- noteGroupable.getMaxPitch()) * 10;
        groupablePane.setLayoutX(x);
        groupablePane.setLayoutY(y);

        if (noteGroupable instanceof Note) {
            Note note = (Note) noteGroupable;
            NoteRectangle noteBox = new NoteRectangle(noteGroupable.getDuration(),0,0);
            noteBox.setFill(getInstrumentColor(note.getChannel()));
            groupablePane.getChildren().add(noteBox);
            groupablePane.setContainsSingleNote(true);
            return groupablePane;
        } else {
            NoteGroup group = (NoteGroup) noteGroupable;
            for (NoteGroupable subNoteGroupable : group.getNoteGroupables()) {
                NoteGroupablePane subGroupRect = createNoteGroupablePane(subNoteGroupable);
                subGroupRect.setLayoutX(subGroupRect.getLayoutX()-groupablePane.getLayoutX() + 5);
                subGroupRect.setLayoutY(subGroupRect.getLayoutY()-groupablePane.getLayoutY() + 5);
                groupablePane.getChildren().add(subGroupRect);
            }
            return groupablePane;
        }
    }
}
