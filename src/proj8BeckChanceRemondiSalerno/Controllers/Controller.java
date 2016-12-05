/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */

package proj8BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
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
     * The MenuBarController loaded by the FXML Loader
     */
    @FXML
    private MenuBarController menuBarController;

    /**
     * The InstrumentPaneController loaded by the FXML Loader
     */
    @FXML
    private InstrumentPaneController instrumentPaneController;

    /**
     * The CompositionContainerController loaded by the FXML Loader
     */
    @FXML
    private CompositionContainerController compositionContainerController;

    /**
     * Seeds our CompositionPaneManager and TempoLine objects with the
     * fields from the FXML file after the FXML has been initialized
     */
    public void initialize() {
        CompositionManager compositionManager = new CompositionManager();
        compositionContainerController.setCompositionManager(compositionManager);
        compositionManager.setInstrumentPaneController(instrumentPaneController);
        compositionManager.setTempoLineController(new TempoLineController
                (compositionContainerController.getTempoLineContainerPane()));
        menuBarController.setCompositionManager(compositionManager);
        menuBarController.setCompositionContainerController(compositionContainerController);
    }



}
