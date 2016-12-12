/*
 * File: CompositionManager.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj9BeckChanceRemondiSalerno;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import proj9BeckChanceRemondiSalerno.CompositionActions.*;
import proj9BeckChanceRemondiSalerno.Controllers.*;
import proj9BeckChanceRemondiSalerno.LSystem.LSystem;
import proj9BeckChanceRemondiSalerno.LSystem.LSystemFileManager;
import proj9BeckChanceRemondiSalerno.LSystem.MusicalInterpreter;
import proj9BeckChanceRemondiSalerno.Models.Composition;
import proj9BeckChanceRemondiSalerno.Models.Note;
import proj9BeckChanceRemondiSalerno.Models.NoteGroup;
import proj9BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj9BeckChanceRemondiSalerno.Views.NoteGroupablePane;
import proj9BeckChanceRemondiSalerno.Views.NoteRectangle;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

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

    /**
     * The map for matching note models to their views
     */
    private ObservableMap<NoteGroupable, NoteGroupablePane> noteGroupableRectsMap =
            FXCollections.observableHashMap();

    /**
     * The controller for the tempo line
     */
    private TempoLineController tempoLineController;

    /**
     * The controller for the Composition Pane
     */
    private CompositionController compositionController;

    /**
     * The LSystem that takes in a base string and outputs one with the
     * rules applied
     */
    private LSystem lSystem;

    /**
     * The manager to open LSystem files
     */
    private LSystemFileManager lSystemFileManager = new LSystemFileManager();

    /**
     * Translater between the LSystem result string and the notes
     */
    private MusicalInterpreter musicalInterpreter;

    /**
     * Source for getting the current instrument.
     */
    private InstrumentPaneController instrumentPaneController;

    /**
     * The composition player for playing notes
     */
    private CompositionPlayer compositionPlayer = new CompositionPlayer();

    /**
     * Manager for the performed composition actions
     */
    private CompositionActionManager compositionActionManager = new
            CompositionActionManager();

    /**
     * The property for the notes map
     */
    private SimpleMapProperty<NoteGroupable, NoteGroupablePane> notesMapProperty = new
            SimpleMapProperty<>();

    /**
     * The property for whether nothing is selected
     */
    private SimpleBooleanProperty isNothingSelectedProperty = new SimpleBooleanProperty();

    /**
     * The property for whether a group action can occur
     */
    private SimpleBooleanProperty cannotGroupProperty = new SimpleBooleanProperty();

    /**
     * The property for whether an ungroup action can occur
     */
    private SimpleBooleanProperty cannotUngroupProperty = new SimpleBooleanProperty();


    /**
     * Manager for copying, cutting, and pasting notes
     */
    private NotesClipboardManager notesClipboardManager;

    /**
     * The CompositionFileManager for saving and loading files
     */
    private CompositionFileManager compositionFileManager = new CompositionFileManager();

    /**
     * A boolean indicator of any changes made
     */
    private boolean changeSinceLastSave = false;

    /**
     * constructor
     */
    public CompositionManager() {
        notesClipboardManager = new NotesClipboardManager(this);
        notesMapProperty.set(noteGroupableRectsMap);
        isNothingSelectedProperty.set(true);
        cannotUngroupProperty.set(true);
        cannotGroupProperty.set(true);
        notesMapProperty.addListener(new MapChangeListener() {
            @Override
            public void onChanged(Change change) {
                updateProperties();
                changeSinceLastSave = true;
            }
        });
    }


    /**
     * Setter for tempo line controller
     *
     * @param tempoLineControllerontroller the new tempo line controller
     */
    public void setTempoLineController(TempoLineController tempoLineControllerontroller) {
        this.tempoLineController = tempoLineControllerontroller;
    }

    /**
     * Setter for the instrument controller
     *
     * @param instrumentPaneController The new instrument controller
     */
    public void setInstrumentPaneController(InstrumentPaneController
                                                    instrumentPaneController) {
        this.instrumentPaneController = instrumentPaneController;
    }

    /**
     * Setter for the composition controller
     *
     * @param compositionController the new composition controller
     */
    public void setCompositionController(CompositionController compositionController) {
        this.compositionController = compositionController;
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
     * Creates a visual representation of the the notes
     * in the composition sheet.
     *
     * @param xPos the input x position of the note
     * @param yPos the input y position of the note
     * @return the note added
     */
    public Optional<Note> addNoteToComposition(double xPos, double yPos) {
        if (yPos >= 0 && yPos < 1280) {
            Note note = new Note(xPos, yPos, 100, instrumentPaneController
                    .getCurrentInstrumentIndex());
            AddNoteAction addNoteAction = new AddNoteAction(note, getSelectedNotes(),
                    this);
            compositionActionManager.actionCompleted(addNoteAction);
            clearSelectedNotes();
            addGroupable(note);
            selectGroupable(note);
            return Optional.of(note);
        }
        return Optional.empty();
    }

    /**
     * Creates a note group from current selected groupables
     *
     * @return
     */
    public Optional<NoteGroup> createNoteGroupWithSelectedNotes() {
        ArrayList<NoteGroupable> notesToGroup = getSelectedNotes();

        Optional<NoteGroup> group = group(notesToGroup);
        if (group.isPresent()) {
            GroupAction groupAction = new GroupAction(group.get(), this);
            compositionActionManager.actionCompleted(groupAction);
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
        deleteGroupables(notesToGroup);
        NoteGroup group = new NoteGroup(notesToGroup);
        addGroupable(group);
        return Optional.of(group);
    }

    /**
     * Delegates the moving of notes to the Composition Controller.
     *
     * @param notes an ArrayList of notes to move
     * @param dx    the change in x
     * @param dy    the change in y
     */
    public void moveNotes(ArrayList<NoteGroupable> notes, double dx, double dy) {
        for (NoteGroupable note : notes) {
            compositionController.moveNote(note, dx, dy);
            note.changeStartTick(dx);
            note.changePitch(-dy / 10);
        }
    }

    /**
     * Delegates the resizing of notes to the Composition Controller.
     *
     * @param notes an ArrayList of notes to resize
     * @param dx    the change in x
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

        if (groupsToUnGroup.size() != 1) {
            return;
        }
        NoteGroup group = (NoteGroup) groupsToUnGroup.get(0);
        ungroup(group);
        UngroupAction ungroupAction = new UngroupAction(group, this);
        compositionActionManager.actionCompleted(ungroupAction);
    }

    /**
     * Ungroups a NoteGroup.
     *
     * @param group the NoteGroup to ungroup
     */
    public void ungroup(NoteGroup group) {
        ArrayList<NoteGroupable> subNoteGroupables = ((NoteGroup) group)
                .getNoteGroupables();

        compositionController.removeNotePane(noteGroupableRectsMap.get(group));
        noteGroupableRectsMap.remove(group);

        for (NoteGroupable subNoteGroupable : subNoteGroupables) {
            addGroupable(subNoteGroupable);
        }
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
     *
     * @return All groupable models in composition
     */
    public Set<NoteGroupable> getGroupables() {
        return noteGroupableRectsMap.keySet();
    }


    /**
     * Selects a note groupable
     *
     * @param noteGroupable The groupable to select
     */
    public void selectGroupable(NoteGroupable noteGroupable) {
        noteGroupable.setSelected(true);
        noteGroupableRectsMap.get(noteGroupable).setSelected(true);
        updateProperties();
    }

    /**
     * Deselects a note groupable
     *
     * @param noteGroupable The groupable to deselect
     */
    public void deselectNote(NoteGroupable noteGroupable) {
        noteGroupable.setSelected(false);
        noteGroupableRectsMap.get(noteGroupable).setSelected(false);
        updateProperties();
    }

    /**
     * Getter for group pane corresponding to a groupable
     *
     * @param noteGroupable The groupable to get the view for
     * @return The view for the groupable
     */
    public NoteGroupablePane getGroupPane(NoteGroupable noteGroupable) {
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
            if (getGroupPane(noteGroupable).getIsInRectangleBounds(bounds.getMinX(),
                    bounds.getMinY(),
                    bounds.getMaxX(), bounds.getMaxY())) {
                selectGroupable(noteGroupable);
                selectedNotes.add(noteGroupable);
            }
        }
        return selectedNotes;
    }

    /**
     * Selects all notes on the composition
     */
    public void selectAllNotes() {
        ArrayList<NoteGroupable> newlySelectedNotes = new ArrayList<>();
        for (NoteGroupable note : getGroupables()) {
            if (!note.isSelected()) {
                newlySelectedNotes.add(note);
            }
        }
        clearSelectedNotes();
        for (NoteGroupable noteGroupable : getGroupables()) {
            selectGroupable(noteGroupable);
        }

        SelectAction selectAction = new SelectAction(newlySelectedNotes, this);
        compositionActionManager.actionCompleted(selectAction);
    }

    /**
     * Clears the list of selected notes
     */
    public void clearSelectedNotes() {
        for (NoteGroupable noteGroupable : noteGroupableRectsMap.keySet()) {
            if (noteGroupable.isSelected()) {
                this.deselectNote(noteGroupable);
            }
        }
    }

    /**
     * Plays the sequence of all notes and animates the TempoLine.
     */
    public void play() {
        play(getGroupables(), false);
    }

    /**
     * Plays the sequence of given notes and animates the TempoLine.
     *
     * @param noteGroupables    Notes to play
     * @param playFromFirstNote Whether to play starting at the left most note
     */
    public void play(Iterable<NoteGroupable> noteGroupables, boolean playFromFirstNote) {
        ArrayList<Note> notes = new ArrayList<>();
        for (NoteGroupable groupable : noteGroupables) {
            for (Note note : groupable.getNotes()) {
                notes.add(note);
            }
        }
        double startTime = playFromFirstNote ? calculateStartTime(notes) : 0;
        compositionPlayer.play(notes, (long) startTime);
        this.tempoLineController.setStopTime(calculateStopTime(notes));
        this.tempoLineController.setStartTime(startTime);
        this.tempoLineController.playAnimation();
    }

    /**
     * Stops the midiPlayer and hides the tempoLine.
     */
    public void stop() {
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

        DeleteGroupablesAction deleteGroupableAction = new DeleteGroupablesAction
                (groupablesToDelete, this);
        compositionActionManager.actionCompleted(deleteGroupableAction);
    }

    /**
     * Deletes the specified groupables.
     *
     * @param groupables the ArrayList of NoteGroupables to delete
     */
    public void deleteGroupables(Collection<NoteGroupable> groupables) {
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
        Platform.runLater(() -> { // avoids ConcurrentModificationException if
            // iterating over notes to delete
            NoteGroupablePane groupPane = noteGroupableRectsMap.get(noteGroupable);
            compositionController.removeNotePane(groupPane);
            noteGroupableRectsMap.remove(noteGroupable);
        });
    }

    /**
     * Deselects all notes on the composition
     */
    public void deselectAllNotes() {
        for (NoteGroupable noteGroupable : getGroupables()) {
            deselectNote(noteGroupable);
        }
    }

    /**
     * Copies selected notes to the clipboard
     */
    public void copySelectedNotes() {
        notesClipboardManager.copyNotes(getSelectedNotes());
    }

    /**
     * Copies selected notes to the clipboard and removes them from the composition.
     */
    public void cutSelectedNotes() {
        ArrayList<NoteGroupable> selectedNotes = getSelectedNotes();
        notesClipboardManager.cutNotes(selectedNotes);
        CutAction cutAction = new CutAction(selectedNotes, this, notesClipboardManager);
        compositionActionManager.actionCompleted(cutAction);
    }

    /**
     * Pastes the notes on the clipboard to the composition
     */
    public void pasteNotes() {
        notesClipboardManager.pasteNotes();
    }


    /**
     * Adds a
     *
     * @param noteGroupable
     */
    public void addGroupable(NoteGroupable noteGroupable) {
        NoteGroupablePane groupPane = createNoteGroupablePane(noteGroupable);
        noteGroupableRectsMap.put(noteGroupable, groupPane);
        compositionController.addNotePane(groupPane);
        selectGroupable(noteGroupable);
    }

    /**
     * Gets the currently selected notes.
     *
     * @return an ArrayList of NoteGroupables that are selected
     */
    public ArrayList<NoteGroupable> getSelectedNotes() {
        ArrayList<NoteGroupable> selectedNotes = new ArrayList<>();
        for (NoteGroupable noteGroupable : getGroupables()) {
            if (noteGroupable.isSelected()) {
                selectedNotes.add(noteGroupable);
            }
        }
        return selectedNotes;
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
     * Calculates the start time for the given notes
     *
     * @return startTime
     */
    private double calculateStartTime(Iterable<Note> notes) {
        double startTime = Double.MAX_VALUE;
        for (Note note : notes) {
            if (startTime > note.getStartTick()) {
                startTime = note.getStartTick();
            }
        }
        return startTime;
    }

    /**
     * Calculates the time for the given notes
     *
     * @return stopTime
     */
    private double calculateStopTime(Iterable<Note> notes) {
        double stopTime = 0.0;
        for (Note note : notes) {
            if (stopTime < note.getStartTick() + note.getDuration()) {
                stopTime = note.getStartTick() + note.getDuration();
            }
        }
        return stopTime;
    }

    /**
     * Creates a new NoteGroupablePane based on a group
     *
     * @param noteGroupable The groupable to create a pane for
     * @return the new groupable pane
     */
    private NoteGroupablePane createNoteGroupablePane(NoteGroupable noteGroupable) {
        NoteGroupablePane groupablePane = new NoteGroupablePane();
        groupablePane.setMinHeight(noteGroupable.getMaxPitch() - noteGroupable
                .getMinPitch() + 10);
        int x = noteGroupable.getStartTick();
        int y = (127 - noteGroupable.getMaxPitch()) * 10;
        groupablePane.setLayoutX(x);
        groupablePane.setLayoutY(y);

        if (noteGroupable instanceof Note) {
            Note note = (Note) noteGroupable;
            NoteRectangle noteBox = new NoteRectangle(noteGroupable.getDuration(), 0, 0);
            note.addObserver(noteBox);
            noteBox.setFill(getInstrumentColor(note.getChannel()));
            groupablePane.getChildren().add(noteBox);
            groupablePane.setContainsSingleNote(true);
            return groupablePane;
        } else {
            NoteGroup group = (NoteGroup) noteGroupable;
            for (NoteGroupable subNoteGroupable : group.getNoteGroupables()) {
                NoteGroupablePane subGroupRect = createNoteGroupablePane
                        (subNoteGroupable);
                subGroupRect.setLayoutX(subGroupRect.getLayoutX() - groupablePane
                        .getLayoutX());
                subGroupRect.setLayoutY(subGroupRect.getLayoutY() - groupablePane
                        .getLayoutY());
                groupablePane.getChildren().add(subGroupRect);
            }
            return groupablePane;
        }
    }

    /**
     * Tells the ActionManager to redo the last undone action.
     */
    public void redoLastUndoneAction() {
        this.compositionActionManager.redoLastUndoneAction();
    }

    /**
     * Tells the ActionManager to undo the last action.
     */
    public void undoLastAction() {
        this.compositionActionManager.undoLastAction();
    }

    /**
     * Tells the action manager CompositionAction has been completed.
     *
     * @param action The completed CompositionAction
     */
    public void actionCompleted(CompositionAction action) {
        changeSinceLastSave = true;
        this.compositionActionManager.actionCompleted(action);
    }


    /**
     * Getter for emptyProperty of notesMapProperty
     *
     * @return emptyProperty of notesMapProperty
     */
    public ReadOnlyBooleanProperty getNotesEmptyProperty() {
        return notesMapProperty.emptyProperty();
    }

    /**
     * Getter for isNothingSelectedProperty
     *
     * @return isNothingSelectedProperty
     */
    public SimpleBooleanProperty getIsNothingSelectedProperty() {
        return isNothingSelectedProperty;
    }

    /**
     * Getter for clipboardEmptyProperty
     *
     * @return clipboardEmptyProperty
     */
    public SimpleBooleanProperty getClipboardEmptyProperty() {
        return notesClipboardManager.getClipboardEmptyProperty();
    }

    /**
     * Getter for undoEmptyProperty
     *
     * @return undoEmptyProperty
     */
    public SimpleBooleanProperty getUndoEmptyProperty() {
        return compositionActionManager.getUndoEmptyProperty();
    }

    /**
     * Getter for redoEmptyProperty
     *
     * @return redoEmptyProperty
     */
    public SimpleBooleanProperty getRedoEmptyProperty() {
        return compositionActionManager.getRedoEmptyProperty();
    }

    /**
     * Getter for cannotGroupProperty
     *
     * @return cannotGroupProperty
     */
    public SimpleBooleanProperty getCannotGroupProperty() {
        return cannotGroupProperty;
    }

    /**
     * Getter for cannotUngroupProperty
     *
     * @return cannotUngroupProperty
     */
    public SimpleBooleanProperty getCannotUngroupProperty() {
        return cannotUngroupProperty;
    }

    /**
     * Updates the composition manager's properties
     */
    private void updateProperties() {
        int selectedCount = getSelectedNotes().size();
        isNothingSelectedProperty.set(selectedCount == 0);
        cannotGroupProperty.set(selectedCount < 2);
        cannotUngroupProperty.set(!(getSelectedNotes().size() == 1
                && getSelectedNotes().get(0) instanceof NoteGroup));
    }

    /**
     * Saves the current composition
     */
    public void saveComposition() {
        Composition composition = new Composition(new ArrayList<>(notesMapProperty
                .keySet()));
        try {
            changeSinceLastSave = compositionFileManager.saveComposition(composition);
        } catch (JAXBException e) {
            // Catch the potential Exception
        }
    }

    /**
     * Saves the current composition as a new file
     */
    public void saveCompositionAsNew() {
        changeSinceLastSave = false;
        Composition composition = new Composition(new ArrayList<>(notesMapProperty
                .keySet()));
        try {
            compositionFileManager.saveCompositionAsNew(composition);
        } catch (JAXBException e) {
            // Catch the potential Exception
        }
    }

    /**
     * Opens a composition from a file
     */
    public void openComposition() {
        if (canDiscardComposition()) {
            clearComposition();
            Optional<Composition> composition = Optional.empty();
            try {
                composition = compositionFileManager.openComposition();
            } catch (CompositionFileManager.LoadCompositionFileException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Opening Composition File");
                alert.setContentText(e.getLocalizedMessage());
                alert.show();
            }
            if (composition.isPresent()) {
                if (composition.get().getNotes() != null)
                    composition.get().getNotes().forEach(this::addGroupable);
            }
        }
    }

    /**
     * Determines whether the current composition can be discarded
     *
     * @return whether the current composition can be discarded
     */
    public boolean canDiscardComposition() {
        if (changeSinceLastSave) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Changes");
            alert.setHeaderText("Would you like to save your changes before creating a " +
                    "new composition?");
            alert.setContentText("Your changes will be lost if you do not save.");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData
                    .CANCEL_CLOSE);

            alert.getButtonTypes().setAll(yesButton, noButton, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                saveComposition();
                return true;
            } else if (result.get() == noButton) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Delegates the file selection to the lsystem file manager and passes the selected
     * path to the loadLsystem method.
     */
    public void selectLSystem(){
        loadLSystem(this.lSystemFileManager.selectFile());
    }

    /**
     * Creates a dialog window and asks the user for the LSystem Parameters
     *
     * @return an Optional containing values corresponding to the LSystem parameters
     */
    public Optional<int[]> getLSystemParametersFromUser(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("./Views/LSystemOptions.fxml"));
            Parent instrumentsRoot = fxmlLoader.load();
            Alert alert = new Alert(Alert.AlertType.NONE);
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(instrumentsRoot);
            alert.setDialogPane(dialogPane);
            ButtonType confirmButton = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
            ButtonType buttonTypeCancel = new ButtonType("CANCEL",
                    ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, buttonTypeCancel);
            LSystemOptionsController lSystemOptionsController = fxmlLoader.getController();
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {
                int iterations = lSystemOptionsController.getIterations();
                int duration = lSystemOptionsController.getDuration();
                int startPitch = lSystemOptionsController.getStartPitch();
                int[] options = {iterations, duration, startPitch};
                return Optional.of(options);
            }
        } catch (IOException e) {
            // Do nothing
        }
        return Optional.empty();
    }

    /**
     * creates a new lSystem with the given filename, reads the file
     * and builds the string. Then it creates a MusicalInterpreter
     * to convert the string to notes.
     *
     * @param filename the path to the selected file
     */
    public void loadLSystem(String filename){
        this.lSystem = new LSystem(filename);

        int iterations;
        int distance;
        int startPitch;
        Optional<int[]> options = getLSystemParametersFromUser();
        if (!options.isPresent()){
            System.out.println("here");
            iterations = 3;
            distance = 100;
            startPitch = 500;
        }
        else {
            iterations = options.get()[0];
            distance = options.get()[1];
            startPitch = options.get()[2];
        }

        this.lSystem.read();
        String resultString = this.lSystem.buildString(iterations);
        this.musicalInterpreter = new MusicalInterpreter(this);
        this.musicalInterpreter.stringToNotes(resultString, distance, startPitch);
    }

    /**
     * Changes the channels of notes
     *
     * @param notes   Notes to change the channel of
     * @param channel new instrument channel
     */
    public void setChannelForNotes(Iterable<NoteGroupable> notes, int channel) {
        for (NoteGroupable note : notes) {
            setChannelForNote(note, channel);
        }
    }

    /**
     * Changes the channels of a note
     * @param note Note to set channel of
     * @param channel new channel
     */
    public void setChannelForNote(NoteGroupable note, int channel) {
        note.setChannel(channel);
        NoteGroupablePane noteGroupablePane = getGroupPane(note);
        noteGroupablePane.setNoteFill(getInstrumentColor(channel));
    }

    /**
     * Starts a new composition
     */
    public void createNewComposition() {
        if (canDiscardComposition()) {
            clearComposition();
        }
    }

    /**
     * Removes all notes from the composition
     */
    private void clearComposition() {
        Main.setPrimaryStageTitle("New Composition");
        deleteGroupables(getGroupables());
        compositionFileManager.removeCurrentSavePath();
        changeSinceLastSave = false;
        compositionActionManager.clearActions();
    }
}
