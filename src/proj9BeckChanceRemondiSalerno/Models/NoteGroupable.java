/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */

package proj9BeckChanceRemondiSalerno.Models;

import proj9BeckChanceRemondiSalerno.XMLAdapters.NoteGroupableAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This interface models something that can be grouped with other groupables on a
 * composition
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
@XmlRootElement
@XmlJavaTypeAdapter(NoteGroupableAdapter.class)
public interface NoteGroupable extends Cloneable, Serializable {

    /**
     * Returns all of the notes in the Groupable object
     *
     * @return an ArrayList of Notes
     */
    ArrayList<Note> getNotes();

    /**
     * Returns whether the Groupable is currently selected
     *
     * @return a boolean indicator
     */
    boolean isSelected();

    /**
     * Sets the Groupable's selected field
     *
     * @param selected a boolean indicator of whether the Groupable is selected
     */
    void setSelected(boolean selected);

    /**
     * Changes the duration of the notes within the Groupable
     *
     * @param dx the change in the x direction
     */
    void changeNoteDurations(double dx);

    /**
     * Returns the duration of the Groupable
     *
     * @return the duration
     */
    double getDuration();

    /**
     * Returns the start tick of the Groupable
     *
     * @return the start tick
     */
    int getStartTick();

    /**
     * Returns the end tick of the Groupable
     *
     * @return the end tick
     */
    int getEndTick();

    /**
     * Returns the Max pitch of the groupable
     *
     * @return the max pitch
     */
    int getMaxPitch();

    /**
     * Returns the Min pitch of the Groupable
     *
     * @return the min pitch
     */
    int getMinPitch();

    /**
     * Changes the pitch of the Groupable
     *
     * @param dy the change in the y direction
     */
    void changePitch(double dy);

    /**
     * Changes the start tick of the Groupable
     *
     * @param dx the change in the x direction
     */
    void changeStartTick(double dx);

    /**
     * Clones the NoteGroupable
     *
     * @return the cloned NoteGroupable
     */
    NoteGroupable clone();

    /**
     * Setter for channel
     *
     * @param channel New channel
     */
    void setChannel(int channel);

    /**
     * Setter for channel
     *
     * @param volume New volume
     */
    void setVolume(int volume);

}
