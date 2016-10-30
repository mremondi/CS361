/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */


package proj5BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import proj5BeckChanceRemondiSalerno.CompositionActions.*;
import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj5BeckChanceRemondiSalerno.Views.NoteGroupablePane;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;


/**
 * This models a controller for the composition view.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class CompositionController {

    /**
     * The composition sheet pane containing notes.
     */
    @FXML
    private Pane composition;

    /**
     * Accesses the CompositionManager shared instance.
     */
    private final CompositionManager managerInstance = CompositionManager.getInstance();

    /**
     * The last location of the drag.
     */
    private Point2D.Double lastDragLocation = new Point2D.Double();

    /**
     * The starting location of the current drag.
     */
    private Point2D.Double dragStartLocation = new Point2D.Double();

    /**
     * Whether mouse is currently dragging.
     */
    private boolean isDragging;

    /**
     * Whether notes are currently being moved.
     */
    private boolean isMovingNotes;

    /**
     * Whether notes are currently being resized.
     */
    private boolean isResizing;

    /**
     * Drag box for easy multiple selection.
     */
    private Rectangle dragBox;

    public void initialize() {
        CompositionManager.getInstance().setCompositionController(this);
    }

    /**
     * Handles the GUI's mousePressed event.
     *
     * @param mouseEvent the GUI's mouseEvent
     */
    @FXML
    public void handleMousePressed(MouseEvent mouseEvent) {
        lastDragLocation.x = mouseEvent.getX();
        lastDragLocation.y = mouseEvent.getY();
        dragStartLocation = (Point2D.Double)lastDragLocation.clone();
        handleDragStartedAtLocation(mouseEvent.getX(), mouseEvent.getY(), mouseEvent.isControlDown());
    }

    /**
     * Handles the GUI's mouseDrag event.
     *
     * @param mouseEvent the GUI's mouseEvent
     */
    @FXML
    public void handleMouseDrag(MouseEvent mouseEvent) {
        handleDragMoved(mouseEvent.getX() - lastDragLocation.x,
                mouseEvent.getY() - lastDragLocation.y, mouseEvent.isControlDown());
        lastDragLocation.x = mouseEvent.getX();
        lastDragLocation.y = mouseEvent.getY();
        this.isDragging = true;
    }

    /**
     * Handles the GUI's mouseReleased event.
     *
     * @param mouseEvent the GUI's mouseEvent
     */
    @FXML
    public void handleMouseReleased(MouseEvent mouseEvent) {
        handleDragEnded();
        if (!isDragging) {
            if (mouseEvent.isControlDown()) {
                handleControlClickAt(mouseEvent.getX(), mouseEvent.getY());
            } else {
                handleClickAt(mouseEvent.getX(), mouseEvent.getY());
            }
        }
        lastDragLocation.x = mouseEvent.getX();
        lastDragLocation.y = mouseEvent.getY();
        isDragging = false;
    }


    /**
     * Handles the drag movement of the mouse.
     *
     * Responds to mouse drag movement by moving the selected notes,
     * resizing the selected notes, or creates a DragBox and selects
     * and unselects the corresponding notes.
     *
     * @param dx
     * @param dy
     */
    private void handleDragMoved(double dx, double dy, boolean controlDrag) {
        if (isMovingNotes) {
            moveSelectedNotes(dx, dy);
        } else if (isResizing) {
            resizeSelectedNotes(dx);
        } else {
            if (!controlDrag){
                managerInstance.clearSelectedNotes();
            }
            this.dragBox.setWidth(this.dragBox.getWidth() + dx);
            this.dragBox.setHeight(this.dragBox.getHeight() + dy);
            Bounds bounds = this.dragBox.getBoundsInParent();
            managerInstance.selectNotesIntersectingRectangle(bounds);
        }
    }


    public void removeNotePane(NoteGroupablePane pane) {
        composition.getChildren().remove(pane);
    }


    public void addNotePane(NoteGroupablePane pane) {
        composition.getChildren().add(pane);
    }

    /**
     * Resizes the notes horizontally.
     *
     * @param dx the change in the mouse's x coordinate
     */
    private void resizeSelectedNotes(double dx) {
        for (NoteGroupable noteGroupable : managerInstance.getGroupables()) {
            if (noteGroupable.isSelected()){
                resizeNote(noteGroupable,dx);
            }
        }
    }

    public void resizeNote(NoteGroupable note, double dx) {
        note.changeNoteDurations(dx);
        managerInstance.getGroupPane(note).changeWidth(dx);
    }

    /**
     * Handles the start of the drag motion of the mouse.
     *
     * Checks if the mouse is on a note, if it is, it checks whether to
     * move the note(s) or to resize them, if it is not on a note, it creates
     * a DragBox.
     *
     * @param x
     * @param y
     */
    private void handleDragStartedAtLocation(double x, double y, boolean controlDrag) {
        if (controlDrag) { return; }
        isResizing = false;
        isMovingNotes = false;
        Optional<NoteGroupable> optionalNote = managerInstance.getGroupableAtPoint(x, y);
        // if the click is on a note
        if (optionalNote.isPresent()) {
            NoteGroupable note = optionalNote.get();
            NoteGroupablePane groupPane = managerInstance.getGroupPane(note);
            boolean onNoteEdge = false;
            // if it is on the edge of a note
            if (groupPane.getIsOnEdge(x, y)) {
                onNoteEdge = true;
                isResizing = true;
            }

            if (!note.isSelected()) {
                managerInstance.clearSelectedNotes();
                managerInstance.selectGroupable(note);
            }

            if (!onNoteEdge) {
                isMovingNotes = true;
            }
        }
        // click is not on a note so create a DragBox
        else {
            createDragBox(x, y);
        }
    }

    /**
     * Moves the selected note(s) by the change in mouse movement.
     *
     * @param dx the change in the mouse's x coordinate
     * @param dy the change in the mouse's y coordinate
     */
    private void moveSelectedNotes(double dx, double dy) {
        for (NoteGroupable note : managerInstance.getGroupables()) {
            if (note.isSelected()){
                moveNote(note,dx,dy);
            }
        }
    }

    public void moveNote(NoteGroupable note, double dx, double dy) {
        NoteGroupablePane noteGroupablePane = managerInstance.getGroupPane(note);
        System.out.println(noteGroupablePane.getLayoutX());
        noteGroupablePane.setTranslateX(noteGroupablePane.getTranslateX() + dx);
        noteGroupablePane.setTranslateY(noteGroupablePane.getTranslateY() + dy);
    }

    /**
     * Handles the release of the drag motion of the mouse.
     *
     * Responds to the end of the drag movement of the mouse by releasing
     * the moving/resizing notes if there are any and sets all of our
     * corresponding pieces back to their resting state.
     */
    private void handleDragEnded() {
        if(isMovingNotes) {
            releaseMovedNotes();
        } else if (isResizing) {
            releaseResizedNotes();
        } else {
            Bounds bounds = this.dragBox.getBoundsInParent();
            ArrayList<NoteGroupable> selected = managerInstance.selectNotesIntersectingRectangle(bounds);
            CompositionAction action = new SelectAction(selected);
            CompositionManager.getInstance().actionCompleted(action);
        }
        isResizing = false;
        isMovingNotes = false;
        composition.getChildren().remove(this.dragBox);
    }

    /**
     * Releases the notes that were being moved and drops them in
     * the nearest horizontal bar.
     */
    private void releaseMovedNotes() {
        ArrayList<NoteGroupable> movedNotes = new ArrayList<>();
        for (NoteGroupable note : managerInstance.getGroupables()) {
            if(note.isSelected()) {
                double pitchdy = (dragStartLocation.getY() - lastDragLocation.getY())/10;
                double startTickdy = lastDragLocation.getX() - dragStartLocation.getX();
                note.changePitch(pitchdy);
                note.changeStartTick(startTickdy);
                movedNotes.add(note);
            }
        }

        MoveAction moveAction = new MoveAction(movedNotes,
                lastDragLocation.getX() - dragStartLocation.getX(),
                lastDragLocation.getY() - dragStartLocation.getY());
        CompositionManager.getInstance().actionCompleted(moveAction);
    }

    private void releaseResizedNotes() {
        ArrayList<NoteGroupable> resizedNotes = new ArrayList<>();
        for (NoteGroupable note : managerInstance.getGroupables()) {
            if(note.isSelected()) {
                resizedNotes.add(note);
            }
        }

        ResizeAction resizeAction = new ResizeAction(resizedNotes, lastDragLocation.getX() - dragStartLocation.getX());
        CompositionManager.getInstance().actionCompleted(resizeAction);
    }

    /**
     * Handles the control click of the mouse.
     *
     * Responds to the control click of the mouse and selects/unselects
     * the appropriate notes.
     *
     * @param x the x location of the mouse click on the pane
     * @param y the y location of the mouse click on the pane
     */
    private void handleControlClickAt(double x, double y) {
        Optional<NoteGroupable> noteAtClickLocation = managerInstance.getGroupableAtPoint(x, y);
        // if there is a note at the click location
        if (noteAtClickLocation.isPresent()) {
            // if this note is already selected, unselect it
            if (noteAtClickLocation.get().isSelected()){
                managerInstance.deselectNote(noteAtClickLocation.get());
                CompositionAction action = new DeselectAction(noteAtClickLocation.get());
                CompositionManager.getInstance().actionCompleted(action);
            }
            // if it is not selected, select it
            else {
                managerInstance.selectGroupable(noteAtClickLocation.get());
                CompositionAction action = new SelectAction(noteAtClickLocation.get());
                CompositionManager.getInstance().actionCompleted(action);
            }
        }
        // add a new note and select it
        else{
            NoteGroupable note = managerInstance.addNoteToComposition(x, y);
            managerInstance.selectGroupable(note);
        }
    }

    /**
     * Handles the click of the mouse.
     *
     * Responds to the click of the mouse and selects/unselects the appropriate notes.
     *
     * @param x the x location of the mouse click on the pane
     * @param y the y location of the mouse click on the pane
     */
    private void handleClickAt(double x, double y) {
        managerInstance.stop();
        Optional<NoteGroupable> noteAtClickLocation = managerInstance.getGroupableAtPoint(x, y);
        managerInstance.clearSelectedNotes();
        if (noteAtClickLocation.isPresent()) {
            if (!noteAtClickLocation.get().isSelected()){
                managerInstance.selectGroupable(noteAtClickLocation.get());
            }
        } else {
            managerInstance.addNoteToComposition(x, y);
        }
    }

    /**
     * Creates and adds the drag box to the view.
     * @param x X location of the box.
     * @param y Y location of the box.
     */
    private void createDragBox(double x, double y){
        this.dragBox = new Rectangle(0, 0);
        this.dragBox.setX(x);
        this.dragBox.setY(y);
        this.dragBox.getStyleClass().add("dragBox");
        composition.getChildren().add(this.dragBox);
    }
}
