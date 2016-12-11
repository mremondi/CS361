/*
 * File: CompositionFileManager.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj9BeckChanceRemondiSalerno;

import javafx.stage.FileChooser;
import proj9BeckChanceRemondiSalerno.Models.Composition;
import proj9BeckChanceRemondiSalerno.Models.Note;
import proj9BeckChanceRemondiSalerno.Models.NoteGroup;

import javax.xml.bind.*;
import java.io.File;
import java.util.Optional;

/**
 * This class models an object that can save and load compositions from files
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class CompositionFileManager {

    /**
     * The file chooser for opening files
     */
    private final FileChooser fileChooser = new FileChooser();

    /**
     * The current save path of the current file. Empty if there is none.
     */
    private Optional<String> currentSavePath = Optional.empty();

    /**
     * Used for marshalling compositions
     */
    private Marshaller marshaller;

    /**
     * Used for unmarshalling compositions
     */
    private Unmarshaller unmarshaller;

    /**
     * Constructor
     */
    public CompositionFileManager() {
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter
                ("XML Files (.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(fileExtensions);
        try {
            JAXBContext context = JAXBContext.newInstance(Note.class, NoteGroup.class,
                    Composition.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = context.createUnmarshaller();
        } catch (PropertyException e) {
            // Catch the potential JAXB Exception
        } catch (JAXBException e) {
            // Catch the potential JAXB Exception
        }
    }

    /**
     * Saves a composition to either the current saved composition or new file if there
     * is none
     *
     * @param composition The composition to save
     * @return a boolean indicating whether the save operation was cancelled or not
     * @throws JAXBException Thrown if saving fails
     */
    public boolean saveComposition(Composition composition) throws JAXBException {
        if (currentSavePath.isPresent()) {
            File file = new File(currentSavePath.get());
            marshaller.marshal(composition, file);
        } else {
            return saveCompositionAsNew(composition);
        }
        return true;
    }

    /**
     * Saves a composition to a new file of the user's choice
     *
     * @param composition The composition to save
     * @return a boolean indicating whether the save operation was cancelled or not
     * @throws JAXBException Thrown if saving fails
     */
    public boolean saveCompositionAsNew(Composition composition) throws JAXBException {
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        marshaller.marshal(composition, file);
        currentSavePath = Optional.of(file.getAbsolutePath());
        Main.setPrimaryStageTitle(file.getName());
        return currentSavePath.isPresent();
    }

    /**
     * Loads a composition from a file of the user's choice
     *
     * @return an Optional containing a composition or empty
     * @throws LoadCompositionFileException Thrown if loading fails
     */
    public Optional<Composition> openComposition() throws LoadCompositionFileException {
        File selectedFile = fileChooser.showOpenDialog(Main.getPrimaryStage());
        try {
            if (selectedFile != null) {
                Composition composition = (Composition) unmarshaller.unmarshal(selectedFile);
                currentSavePath = Optional.of(selectedFile.getAbsolutePath());
                Main.setPrimaryStageTitle(selectedFile.getName());
                return Optional.of(composition);
            }
        } catch (Exception e) {
            // Opening a malformed file could throw several different exceptions
            // catch all exceptions and treat them as a LoadCompositionFileException
            throw new LoadCompositionFileException();
        }
        return Optional.empty();
    }

    /**
     * Removes the current save path (sets it to empty)
     */
    public void removeCurrentSavePath() {
        currentSavePath = Optional.empty();
    }

    /**
     * This class models an exception for failed composition loading
     */
    class LoadCompositionFileException extends Exception {

        @Override
        public String getLocalizedMessage() {
            return "Failed to load composition. Check that the file format is correct and all elements are of the expected type.";
        }

    }
}
