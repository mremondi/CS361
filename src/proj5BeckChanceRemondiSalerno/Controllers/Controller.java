/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 5
 * Due Date: October 23, 2016
 */

package proj5BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import proj5BeckChanceRemondiSalerno.CompositionManager;

/**
 * This class handles all user GUI interactions
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
    private Pane fxLinePane;

    /**
     * The container pane for the tempo line
     */
    @FXML
    private Pane fxTempoLineContainerPane;


    /**
     * Seeds our CompositionPaneManager and TempoLine objects with the
     * fields from the FXML file after the FXML has been initialized
     */
    public void initialize() {
        createLinePane();
        CompositionManager.getInstance().setTempoLineController(new TempoLineController(fxTempoLineContainerPane));
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
            fxLinePane.getChildren().add(staffLine);
        }
    }


}
