/*
 * File: VolumeController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
*/
package proj8BeckChanceRemondiSalerno.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * This class models a controller for the volume view.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class VolumeController {

    /**
     * The label displaying current volume
     */
    @FXML
    Label volumeLabel;

    /**
     * Slider for changing volume
     */
    @FXML
    Slider volumeSlider;

    /**
     * The default initializer method called by the FXML loader
     */
    public void initialize() {
        updateVolumeLabel();
        volumeSlider.valueProperty().addListener((arg0, arg1, arg2) ->
                updateVolumeLabel());
    }

    /**
     * Set a new volume
     *
     * @param volume new volume
     */
    public void setCurrentVolume(int volume) {
        volumeSlider.setValue(volume);
        updateVolumeLabel();
    }

    /**
     * Get current slider value
     *
     * @return the int value of the volume slider
     */
    public int getVolume() {
        return (int) Math.round(volumeSlider.getValue());
    }

    /**
     * Updates the volume label text
     */
    private void updateVolumeLabel() {
        volumeLabel.setText("Volume: " + (int) volumeSlider.getValue());
    }

}
