/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */

package proj8BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import proj8BeckChanceRemondiSalerno.CompositionManager;

/**
 * This class initializes and controls the horizontal
 * Composition Lines and the vertical Tempo Line.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class Controller {
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

    @FXML
    private MenuBarController menuBarController;

    @FXML
    private InstrumentPaneController instrumentPaneController;

    @FXML
    private CompositionController compositionController;

    /**
     * Seeds our CompositionPaneManager and TempoLine objects with the
     * fields from the FXML file after the FXML has been initialized
     */
    public void initialize() {
        createLinePane();
        CompositionManager compositionManager = new CompositionManager();
        compositionController.setCompositionManager(compositionManager);
        compositionManager.setCompositionController(compositionController);
        compositionManager.setInstrumentPaneController(instrumentPaneController);
        compositionManager.setTempoLineController(new TempoLineController(tempoLineContainerPane));
        menuBarController.setCompositionManager(compositionManager);
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
