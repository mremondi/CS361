/*
 * File: TempoLineController.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno.Controllers;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * This class models a controller for the a tempo line pane.
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class TempoLineController {
    /**
     * The main pane containing the tempo line.
     */
    private Pane tempoLineContainerPane;

    /**
     * The line graphic representing the tempo line
     */
    private Line tempoLine;

    /**
     * The animation for animating the tempo line across the pane.
     */
    private TranslateTransition tempoAnimation = new TranslateTransition();

    /**
     * The stop time of the animation
     */
    private double stopTime = 0;

    /**
     * The start time of the animation
     */
    private double startTime = 0;

    /**
     * Constructor
     *
     * @param tempoLineContainerPane The main pane to create the tempo line in.
     */
    public TempoLineController(Pane tempoLineContainerPane) {
        this.tempoLineContainerPane = tempoLineContainerPane;
        this.tempoLine = createTempoLine();
        this.tempoAnimation.setNode(this.tempoLine);
        this.tempoAnimation.setInterpolator(Interpolator.LINEAR); // Don't ease
        this.tempoAnimation.setOnFinished(event -> hideTempoLine());
    }

    /**
     * Hides the tempo line
     */
    public void hideTempoLine() {
        this.tempoLine.setVisible(false);
    }


    /**
     * Updates the information in the tempo line and animation for
     * the reproduction of a composition
     */
    private void updateAnimation() {
        this.tempoAnimation.stop();
        this.tempoLine.setTranslateX(startTime);
        this.tempoAnimation.setDuration(new Duration(stopTime * 10 - startTime * 10));
        this.tempoAnimation.setToX(stopTime);
        this.tempoLine.setVisible(true);
    }

    /**
     * Setter for stop time
     *
     * @param stopTime New stop time
     */
    public void setStopTime(double stopTime) {
        this.stopTime = stopTime;
        updateAnimation();
    }

    /**
     * Setter for start time
     *
     * @param startTime New start time
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
        updateAnimation();
    }

    /**
     * Accessor method for the animation field's play feature
     */
    public void playAnimation() {
        this.tempoAnimation.play();
    }

    /**
     * Accessor method for the animation field's stop feature
     */
    public void stopAnimation() {
        this.tempoAnimation.stop();
    }

    /**
     * Creates the tempo line and adds it to the container pane.
     *
     * @return
     */
    private Line createTempoLine() {
        Line tempoLine = new Line();
        tempoLineContainerPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            tempoLine.setEndY(tempoLineContainerPane.getHeight());
        });
        tempoLine.setVisible(false);
        tempoLine.setStartY(0);
        tempoLine.setStartX(0);
        tempoLine.setEndX(1);
        tempoLine.setStrokeWidth(1);
        tempoLineContainerPane.getChildren().add(tempoLine);
        tempoLine.setStroke(Color.RED);
        return tempoLine;
    }
}
