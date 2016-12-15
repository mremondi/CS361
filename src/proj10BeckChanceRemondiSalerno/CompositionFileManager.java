/*
 * File: CompositionFileManager.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno;

import javafx.stage.FileChooser;
import proj10BeckChanceRemondiSalerno.Models.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.xml.bind.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * The file chooser for opening xml files
     */
    private final FileChooser xmlFileChooser = new FileChooser();

    /**
     * The file chooser for saving midi files
     */
    private final FileChooser midiFileChooser = new FileChooser();

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
        xmlFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                ("XML Files (.xml)", "*.xml"));
        midiFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter
                ("Midi Files (.mid)", "*.mid"));
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
        File file = xmlFileChooser.showSaveDialog(Main.getPrimaryStage());
        marshaller.marshal(composition, file);
        currentSavePath = Optional.of(file.getAbsolutePath());
        Main.setPrimaryStageTitle(file.getName());
        return currentSavePath.isPresent();
    }

    /**
     * Exports a composition as a midi file
     * @param composition Composition to export
     * @throws IOException
     */
    public void exportCompositionAsMidiFile(Composition composition) throws IOException {
        File file = midiFileChooser.showSaveDialog(Main.getPrimaryStage());
        Sequence sequence = createSequence(composition.getNotes());
        int[] writers = MidiSystem.getMidiFileTypes(sequence);
        if (writers.length == 0) return;
        MidiSystem.write(sequence, writers[0], new FileOutputStream(file));
        return;
    }


    /**
     * Loads a composition from a file of the user's choice
     *
     * @return an Optional containing a composition or empty
     * @throws LoadCompositionFileException Thrown if loading fails
     */
    public Optional<Composition> openComposition() throws LoadCompositionFileException {
        File selectedFile = xmlFileChooser.showOpenDialog(Main.getPrimaryStage());
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
     * Creates a Sequence given notes
     * @param noteGroupables Notes to use in Sequence creation
     * @return  The sequence
     */
    private Sequence createSequence(Iterable<NoteGroupable> noteGroupables) {
        MidiPlayer player = new MidiPlayer(100,60);
        for (NoteGroupable noteGroupable : noteGroupables) {
            for(Note note: noteGroupable.getNotes()) {
                player.addNote(note.getPitch(), note.getVolume(), note.getStartTick(),
                        note.getDuration(),
                        note.getChannel(), note.getTrackIndex());
            }
        }
        return player.getSequence();
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
