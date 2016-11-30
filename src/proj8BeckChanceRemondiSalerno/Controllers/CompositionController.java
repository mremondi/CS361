/*
 * File: CompositionController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */


package proj8BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import proj8BeckChanceRemondiSalerno.CompositionActions.*;
import proj8BeckChanceRemondiSalerno.CompositionManager;
import proj8BeckChanceRemondiSalerno.Models.NoteGroupable;
import proj8BeckChanceRemondiSalerno.Views.NoteGroupablePane;

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
    private CompositionManager compositionManager;

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
     * Setter for the composition manager
     *
     * @param compositionManager The new composition manager
     */
    public void setCompositionManager(CompositionManager compositionManager) {
        this.compositionManager = compositionManager;
        compositionManager.setCompositionController(this);
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
        dragStartLocation = (Point2D.Double) lastDragLocation.clone();
        handleDragStartedAtLocation(mouseEvent.getX(), mouseEvent.getY(), mouseEvent
                .isControlDown());
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
            if (mouseEvent.isSecondaryButtonDown()) {
                handleRightClick(mouseEvent.getX(), mouseEvent.getY());
            } else if (mouseEvent.isControlDown()) {
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
     * Handles a right mouse click
     * @param x x location of click
     * @param y y location of click
     */
    private void handleRightClick(double x, double y) {
        Optional<NoteGroupable> noteAtClickLocation = compositionManager
                .getGroupableAtPoint(x, y);

        if (noteAtClickLocation.isPresent()) {
            // if it is not selected, select it
            if (!noteAtClickLocation.get().isSelected()) {
                compositionManager.selectGroupable(noteAtClickLocation.get());
                CompositionAction action = new SelectAction(noteAtClickLocation.get(),
                        compositionManager);
                compositionManager.actionCompleted(action);
            }
        }

    }


    /**
     * Handles the drag movement of the mouse.
     * <p>
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
            if (!controlDrag) {
                compositionManager.clearSelectedNotes();
            }
            this.dragBox.setWidth(this.dragBox.getWidth() + dx);
            this.dragBox.setHeight(this.dragBox.getHeight() + dy);
            Bounds bounds = this.dragBox.getBoundsInParent();
            compositionManager.selectNotesIntersectingRectangle(bounds);
        }
    }

    /**
     * Removes the note pane from the composition.
     *
     * @param pane the note pane to remove
     */
    public void removeNotePane(NoteGroupablePane pane) {
        composition.getChildren().remove(pane);
    }

    /**
     * Adds the note pane from the composition.
     *
     * @param pane the note pane to add
     */
    public void addNotePane(NoteGroupablePane pane) {
        composition.getChildren().add(pane);
    }

    /**
     * Resizes the notes horizontally.
     *
     * @param dx the change in the mouse's x coordinate
     */
    private void resizeSelectedNotes(double dx) {
        for (NoteGroupable noteGroupable : compositionManager.getGroupables()) {
            if (noteGroupable.isSelected()) {
                resizeNote(noteGroupable, dx);
            }
        }
    }

    /**
     * Resizes the specified note by changing both its duration
     * and the physical width of its corresponding rectangle.
     *
     * @param note the note to resize
     * @param dx   the change in the x direction
     */
    public void resizeNote(NoteGroupable note, double dx) {
        note.changeNoteDurations(dx);
        compositionManager.getGroupPane(note).changeWidth(dx);
    }

    /**
     * Handles the start of the drag motion of the mouse.
     * <p>
     * Checks if the mouse is on a note, if it is, it checks whether to
     * move the note(s) or to resize them, if it is not on a note, it creates
     * a DragBox.
     *
     * @param x
     * @param y
     */
    private void handleDragStartedAtLocation(double x, double y, boolean controlDrag) {
        if (controlDrag) {
            return;
        }
        isResizing = false;
        isMovingNotes = false;
        Optional<NoteGroupable> optionalNote = compositionManager.getGroupableAtPoint
                (x, y);
        // if the click is on a note
        if (optionalNote.isPresent()) {
            NoteGroupable note = optionalNote.get();
            NoteGroupablePane groupPane = compositionManager.getGroupPane(note);
            boolean onNoteEdge = false;
            // if it is on the edge of a note
            if (groupPane.getIsOnEdge(x, y)) {
                onNoteEdge = true;
                isResizing = true;
            }

            if (!note.isSelected()) {
                compositionManager.clearSelectedNotes();
                compositionManager.selectGroupable(note);
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
        for (NoteGroupable note : compositionManager.getGroupables()) {
            if (note.isSelected()) {
                moveNote(note, dx, dy);
            }
        }
    }

    /**
     * Moves the note the specified note
     *
     * @param note the note to move
     * @param dx   the change in the x direction
     * @param dy   the change in the y direction
     */
    public void moveNote(NoteGroupable note, double dx, double dy) {
        NoteGroupablePane noteGroupablePane = compositionManager.getGroupPane(note);
        noteGroupablePane.setLayoutX(noteGroupablePane.getLayoutX() + dx);
        noteGroupablePane.setLayoutY(noteGroupablePane.getLayoutY() + dy);
    }

    /**
     * Handles the release of the drag motion of the mouse.
     * <p>
     * Responds to the end of the drag movement of the mouse by releasing
     * the moving/resizing notes if there are any and sets all of our
     * corresponding pieces back to their resting state.
     */
    private void handleDragEnded() {
        if (isMovingNotes) {
            releaseMovedNotes();
        } else if (isResizing) {
            releaseResizedNotes();
        } else {
            Bounds bounds = this.dragBox.getBoundsInParent();
            ArrayList<NoteGroupable> selected = compositionManager
                    .selectNotesIntersectingRectangle(bounds);
            if (selected.size() > 0) {
                CompositionAction action = new SelectAction(selected, compositionManager);
                compositionManager.actionCompleted(action);
            }
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
        ArrayList<NoteGroupable> outOfRangeNotes = new ArrayList<>();
        for (NoteGroupable note : compositionManager.getGroupables()) {
            if (note.isSelected()) {
                double pitchdy = ((int) dragStartLocation.getY() - (int)
                        lastDragLocation.getY()) / 10;
                double startTickdy = lastDragLocation.getX() - dragStartLocation.getX();
                note.changePitch(pitchdy);
                note.changeStartTick(startTickdy);
                compositionManager.getGroupPane(note).roundUpYLocation();
                movedNotes.add(note);
                if (note.getStartTick() < 0 || note.getStartTick() > 2000) {
                    // remove note completely
                    movedNotes.remove(note);
                    outOfRangeNotes.add(note);
                }
                if (note.getMinPitch() < 0 || note.getMaxPitch() > 127) {
                    // remove note completely
                    movedNotes.remove(note);
                    outOfRangeNotes.add(note);
                }
            }
        }
        for (NoteGroupable note : outOfRangeNotes) {
            compositionManager.deleteGroupable(note);
        }
        // new move action with dx, dy, and manager
        MoveAction moveAction = new MoveAction(movedNotes,
                lastDragLocation.getX() - dragStartLocation.getX(),
                lastDragLocation.getY() - dragStartLocation.getY(), compositionManager);
        compositionManager.actionCompleted(moveAction);
    }

    /**
     * Release the resized notes.
     */
    private void releaseResizedNotes() {
        ArrayList<NoteGroupable> resizedNotes = new ArrayList<>();
        for (NoteGroupable note : compositionManager.getGroupables()) {
            if (note.isSelected()) {
                resizedNotes.add(note);
            }
        }
        ResizeAction resizeAction = new ResizeAction(resizedNotes, lastDragLocation
                .getX() - dragStartLocation.getX(), compositionManager);
        compositionManager.actionCompleted(resizeAction);
    }

    /**
     * Handles the control click of the mouse.
     * <p>
     * Responds to the control click of the mouse and selects/unselects
     * the appropriate notes.
     *
     * @param x the x location of the mouse click on the pane
     * @param y the y location of the mouse click on the pane
     */
    private void handleControlClickAt(double x, double y) {
        Optional<NoteGroupable> noteAtClickLocation = compositionManager
                .getGroupableAtPoint(x, y);
        // if there is a note at the click location
        if (noteAtClickLocation.isPresent()) {
            // if this note is already selected, unselect it
            if (noteAtClickLocation.get().isSelected()) {
                compositionManager.deselectNote(noteAtClickLocation.get());
                CompositionAction action = new DeselectAction(noteAtClickLocation.get()
                        , compositionManager);
                compositionManager.actionCompleted(action);
            }
            // if it is not selected, select it
            else {
                compositionManager.selectGroupable(noteAtClickLocation.get());
                CompositionAction action = new SelectAction(noteAtClickLocation.get(),
                        compositionManager);
                compositionManager.actionCompleted(action);
            }
        }
        // add a new note
        else {
            compositionManager.addNoteToComposition(x, y);
        }
    }

    /**
     * Handles the click of the mouse.
     * <p>
     * Responds to the click of the mouse and selects/unselects the appropriate notes.
     *
     * @param x the x location of the mouse click on the pane
     * @param y the y location of the mouse click on the pane
     */
    private void handleClickAt(double x, double y) {
        compositionManager.stop();
        Optional<NoteGroupable> noteAtClickLocation = compositionManager
                .getGroupableAtPoint(x, y);
        if (noteAtClickLocation.isPresent()) {
            if (!noteAtClickLocation.get().isSelected()) {
                compositionManager.clearSelectedNotes();
                SelectAction selectAction = new SelectAction(noteAtClickLocation.get(),
                        compositionManager);
                compositionManager.actionCompleted(selectAction);
                compositionManager.selectGroupable(noteAtClickLocation.get());
            }
        } else {
            compositionManager.addNoteToComposition(x, y);
        }
    }

    /**
     * Creates and adds the drag box to the view.
     *
     * @param x X location of the box.
     * @param y Y location of the box.
     */
    private void createDragBox(double x, double y) {
        this.dragBox = new Rectangle(0, 0);
        this.dragBox.setX(x);
        this.dragBox.setY(y);
        this.dragBox.getStyleClass().add("dragBox");
        composition.getChildren().add(this.dragBox);
    }
}