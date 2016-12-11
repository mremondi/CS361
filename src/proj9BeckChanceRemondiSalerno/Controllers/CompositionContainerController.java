/*
 * File: CompositionContainerController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */


package proj9BeckChanceRemondiSalerno.Controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;
import proj9BeckChanceRemondiSalerno.CompositionManager;

/**
 * This class models a controller for a container for a composition pane
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class CompositionContainerController {

    /**
     * The pane containing the composition sheet lines.
     */
    @FXML
    private Pane linePane;

    /**
     * The container pane for the tempo line
     */
    @FXML
    private Pane tempoLineContainerPane;

    /**
     * The CompositionController loaded by the FXML Loader
     */
    @FXML
    private CompositionController compositionController;

    /**
     * The group used for zooming
     */
    @FXML
    private Group zoomGroup;

    /**
     * The currently applied zoom transform
     */
    private Scale zoomScaleTransform;

    /**
     * The current zoom scale
     */
    private double currentZoomScale = 1;

    /**
     * The amount to increment the zoom factor by
     */
    private final double zoomScaleIncrements = 0.125;

    /**
     * The maximum possible zoom scale
     */
    private final double maxZoom = 3.0;

    /**
     * Property for whether another zoom in is possible
     */
    private SimpleBooleanProperty canZoomIn = new SimpleBooleanProperty(true);

    /**
     * Property for whether another zoom out is possible
     */
    private SimpleBooleanProperty canZoomOut = new SimpleBooleanProperty(true);


    public void initialize() {
        createLinePane();
    }


    /**
     * Getter for the tempo line container pane
     *
     * @return The tempo line container pane
     */
    public Pane getTempoLineContainerPane() {
        return tempoLineContainerPane;
    }

    /**
     * Sets the composition manager for the composition controller
     *
     * @param compositionManager The composition manager to set
     */
    public void setCompositionManager(CompositionManager compositionManager) {
        compositionController.setCompositionManager(compositionManager);
        compositionManager.setCompositionController(compositionController);
    }

    /**
     * Zooms in on the composition
     */
    public void zoomIn() {
        if (currentZoomScale == maxZoom) {
            return;
        }
        currentZoomScale += zoomScaleIncrements;
        updateZoomTransform();
        updateCanZoomProperties();
    }

    /**
     * Zooms out on the composition
     */
    public void zoomOut() {

        if (currentZoomScale == zoomScaleIncrements ) {
            return;
        }
        currentZoomScale -= zoomScaleIncrements;
        updateZoomTransform();
        updateCanZoomProperties();
    }



    /**
     * Getter for can zoom in property
     * @return canZoomInProperty
     */
    public SimpleBooleanProperty canZoomInProperty() {
        return canZoomIn;
    }

    /**
     * Getter for can zoom out property
     * @return canZoomOutProperty
     */
    public SimpleBooleanProperty canZoomOutProperty() {
        return canZoomOut;
    }

    /**
     * Updates the can zoom in and can zoom out properties
     */
    private void updateCanZoomProperties() {
        canZoomOut.set(currentZoomScale != zoomScaleIncrements);
        canZoomIn.set(currentZoomScale != maxZoom);
    }

    /**
     * Updates the current transform
     */
    private void updateZoomTransform() {
        if (zoomScaleTransform != null)
            zoomGroup.getTransforms().remove(zoomScaleTransform);
        zoomScaleTransform = new Scale(currentZoomScale, currentZoomScale, 0, 0);
        zoomGroup.getTransforms().add(zoomScaleTransform);
    }

    /**
     * Creates a visual representation of a composition panel
     * similar to a staff lined workbook
     */
    private void createLinePane() {
        Line staffLine;
        for (int i = 0; i < 127; i++) {
            staffLine = new Line(0, i * 10, 2000, i * 10);
            staffLine.getStyleClass().add("staffLine");
            linePane.getChildren().add(staffLine);
        }
    }
}
