/*
 * File: CompositionController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 5
 * Due Date: October 23, 2016
 */

package proj5BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import proj5BeckChanceRemondiSalerno.CompositionManager;
import proj5BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj5BeckChanceRemondiSalerno.Views.NoteGroupablePane;
import java.awt.geom.Point2D;
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
    public void handleDragMoved(double dx, double dy, boolean controlDrag) {
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



    /**
     * Resizes the notes horizontally.
     *
     * @param dx the change in the mouse's x coordinate
     */
    public void resizeSelectedNotes(double dx) {
        for (NoteGroupable noteGroupable : managerInstance.getGroupables()) {
            if (noteGroupable.isSelected()){
                noteGroupable.changeNoteDurations(dx);
                managerInstance.getGroupPane(noteGroupable).changeWidth(dx);
            }
        }
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
    public void handleDragStartedAtLocation(double x, double y, boolean controlDrag) {
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
    public void moveSelectedNotes(double dx, double dy) {
        for (NoteGroupable note : managerInstance.getGroupables()) {
            if (note.isSelected()){
                NoteGroupablePane noteRectangle = managerInstance.getGroupPane(note);
                noteRectangle.setTranslateX(noteRectangle.getTranslateX() + dx);
                noteRectangle.setTranslateY(noteRectangle.getTranslateY() + dy);
            }
        }
    }



    /**
     * Handles the release of the drag motion of the mouse.
     *
     * Responds to the end of the drag movement of the mouse by releasing
     * the moving/resizing notes if there are any and sets all of our
     * corresponding pieces back to their resting state.
     */
    public void handleDragEnded() {
        if(isMovingNotes) { releaseMovedNotes(); };
        isResizing = false;
        isMovingNotes = false;
        managerInstance.getComposition().getChildren().remove(this.dragBox);
    }

    /**
     * Releases the notes that were being moved and drops them in
     * the nearest horizontal bar.
     */
    public void releaseMovedNotes() {
        for (NoteGroupable note : managerInstance.getGroupables()) {
            if(note.isSelected()) {
                double pitchdy = (dragStartLocation.getY() - lastDragLocation.getY())/10;
                double startTickdy = lastDragLocation.getX() - dragStartLocation.getX();
                System.out.format("pitch dy: %f, start tick dx: %f\n", pitchdy, startTickdy);
                note.changePitch(pitchdy);
                note.changeStartTick(startTickdy);
            }
        }
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
    public void handleControlClickAt(double x, double y) {
        Optional<NoteGroupable> noteAtClickLocation = managerInstance.getGroupableAtPoint(x, y);
        // if there is a note at the click location
        if (noteAtClickLocation.isPresent()) {
            // if this note is already selected, unselect it
            if (noteAtClickLocation.get().isSelected()){
                managerInstance.deselectNote(noteAtClickLocation.get());
            }
            // if it is not selected, select it
            else {
                managerInstance.selectGroupable(noteAtClickLocation.get());
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
    public void handleClickAt(double x, double y) {
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
    public void createDragBox(double x, double y){
        this.dragBox = new Rectangle(0, 0);
        this.dragBox.setX(x);
        this.dragBox.setY(y);
        this.dragBox.getStyleClass().add("dragBox");
        managerInstance.getComposition().getChildren().add(this.dragBox);
    }
}
