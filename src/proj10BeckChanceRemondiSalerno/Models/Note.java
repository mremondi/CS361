/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 9
 * Due Date: December 7, 2016
 */

package proj10BeckChanceRemondiSalerno.Models;

import javafx.beans.property.SimpleIntegerProperty;
import proj10BeckChanceRemondiSalerno.Views.NoteRectangle;

import javax.xml.bind.annotation.*;
import java.beans.Transient;
import java.util.ArrayList;

/**
 * This class models a musical note
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
@XmlRootElement(name = "note")
@XmlAccessorType(XmlAccessType.FIELD)
public class Note implements NoteGroupable {


    /**
     * The minimum width a note can be.
     */
    private static final int MINIMUM_DURATION = 5;

    /**
     * The audio volume of the note.
     */
    private int volume = 100;

    /**
     * Separate from primitive volume so Note can be serializable
     */
    private transient SimpleIntegerProperty volumeProperty;


    /**
     * The instrument for the note.
     */
    @XmlAttribute
    private int channel;

    /**
     * The track index of the note.
     */
    @XmlAttribute
    private int trackIndex;

    /**
     * The pitch of the note
     */
    @XmlAttribute
    private int pitch;

    /**
     * the start tick of the note
     */
    @XmlAttribute
    private int startTick;

    /**
     * the time duration of the note
     */
    @XmlAttribute
    private double duration;

    /**
     * A boolean indicator of whether the note is currently selected
     */
    @XmlTransient
    private boolean selected;

    /**
     * Constructor
     *
     * @param channel the channel that the note belongs to
     */
    public Note(double x, double y, int duration, int channel) {
        setPitch((int) y);
        setStartTick((int) x);
        this.duration = duration;
        this.channel = channel;
        this.trackIndex = 0;
        volumeProperty = new SimpleIntegerProperty(volume);
    }

    /**
     * No argument constructor for the compiler
     */
    public Note() {
    }

    /**
     * Accessor method for pitch
     *
     * @return Pitch of the note
     */
    public int getPitch() {
        return this.pitch;
    }

    /**
     * Setter for pitch
     *
     * @param y the new pitch
     */
    private void setPitch(int y) {
        this.pitch = 127 - ((int) y / 10);
    }

    /**
     * Accessor method for volume
     *
     * @return Volume of the note
     */
    public int getVolume() {
        return volume;
    }

    public SimpleIntegerProperty volumeProperty() {
        if (volumeProperty==null) {
            volumeProperty = new SimpleIntegerProperty(volume);
        }
        return volumeProperty;
    }

    /**
     * Setter for volume
     *
     * @param volume New volume
     */
    public void setVolume(int volume) {
        this.volume = volume;
        this.volumeProperty.setValue(volume);
    }

    /**
     * Accessor method for when the note starts
     *
     * @return Starting tick of the note
     */
    @Override
    public int getStartTick() {
        return this.startTick;
    }


    /**
     * Setter for start tick
     *
     * @param x the new start tick
     */
    private void setStartTick(int x) {
        this.startTick = x;
    }

    /**
     * Accessor method for note duration
     *
     * @return How long the note plays
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Setter for the note duration
     *
     * @param duration the new duration
     */
    private void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Accessor method for channel information
     *
     * @return Channel that the note is played on
     */
    public int getChannel() {
        return this.channel;
    }

    /**
     * Setter for channel
     *
     * @param channel New channel
     */
    public void setChannel(int channel) {
        this.channel = channel;
    }

    /**
     * Resizes the note's rectangle in the right direction.
     *
     * @param dx the distance to move the right edge
     */
    public void changeNoteDurations(double dx) {
        double newDuration = this.getDuration() + dx;
        if (newDuration < MINIMUM_DURATION) {
            this.setDuration(MINIMUM_DURATION);
        } else {
            this.setDuration(newDuration);
        }
    }

    /**
     * Accessor method for track number
     *
     * @return Track for the note
     */
    public int getTrackIndex() {
        return this.trackIndex;
    }


    /**
     * Accessor method for if a note is selected or not
     *
     * @return Whether or not the note is selected
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Updates the selected state
     *
     * @param selected new selected state
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Adds the note to the notes list and gets the notes
     *
     * @return the list of notes
     */
    @Override
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(this);
        return notes;
    }

    /**
     * Gets the end tick of the note
     *
     * @return the end tick
     */
    @Override
    public int getEndTick() {
        return startTick + (int) duration;
    }


    /**
     * Gets the end tick of the note
     *
     * @return the end tick
     */
    @Override
    public int getMaxPitch() {
        return pitch;
    }

    /**
     * Gets the min pitch of the note
     *
     * @return the min pitch
     */
    @Override
    public int getMinPitch() {
        return pitch;
    }

    /**
     * Changes the pitch of the note
     *
     * @param dy how much to change the pitch by
     */
    @Override
    public void changePitch(double dy) {
        pitch += dy;
    }

    /**
     * Changes the start tick of the note
     *
     * @param dx how much to change the start tick by
     */
    @Override
    public void changeStartTick(double dx) {
        startTick += dx;
    }

    /**
     * Returns a cloned version of the Note
     *
     * @return the Note or null
     */
    @Override
    public Note clone() {
        try {
            return (Note) super.clone();
        } catch (CloneNotSupportedException e) {
            // Do nothing
            return null;
        }
    }

}