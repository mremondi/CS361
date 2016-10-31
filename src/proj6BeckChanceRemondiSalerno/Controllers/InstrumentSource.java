/*
 * File: InstrumentSource.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */


package proj6BeckChanceRemondiSalerno.Controllers;

/**
 * This interface models something that can give its current instrument
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public interface InstrumentSource {
    /**
     * Getter for the current instrument index
     * @return The current instrument index
     */
    int getCurrentInstrumentIndex();
}