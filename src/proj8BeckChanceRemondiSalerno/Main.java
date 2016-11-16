/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */

package proj8BeckChanceRemondiSalerno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Launches JavaFX application
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class Main extends Application {

    /**
     * The primary stage of the application
     */
    public static Stage primaryStage;

    /**
     * Sets up the main GUI to play a scale.
     * Player contains a menu bar:
     * File menu: exit option
     * Edit menu: Select all and Delete options
     * Action menu: Play and Stop options
     *
     * @param primaryStage the stage to display the gui
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Views/Main.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        primaryStage.setTitle("New Composition");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
        Main.primaryStage = primaryStage;
    }

    /**
     * Launches application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
