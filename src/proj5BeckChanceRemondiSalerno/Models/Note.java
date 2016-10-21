/*
 * File: MusicalNote.java
 * Names: Graham Chance, Jenny Lin, Ana Sofia Solis Canales, Mike Remondi
 * Class: CS361
 * Project: 4
 * Due Date: October 11, 2016
 */

package proj5BeckChanceRemondiSalerno.Models;

/**
 * This class models a musical note
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class Note implements Groupable{

    /**
     * The minimum width a note can be.
     */
    private static final int MINIMUM_DURATION = 5;

    /**
     * The audio volume of the note.
     */
    private static final int VOLUME = 100;

    /**
     * The instrument for the note.
     */
    private int channel;

    /**
     * The track index of the note.
     */
    private int trackIndex;

    /**
     * Whether the note is currently selected.
     */

    private int pitch;
    private int startTick;
    private double duration;
    private boolean selected;

    /**
     * Constructor
     *
     * @param channel    the channel that the note belongs to
     */
    public Note(double x, double y, int duration, int channel) {
        setPitch((int)y);
        setStartTick((int) x);
        this.duration = duration;
        this.channel = channel;
        this.trackIndex = 0;
    }


    /**
     * Accessor method for pitch
     *
     * @return Pitch of the note
     */
    public int getPitch() {
        return this.pitch;
    }

    public void setPitch(int y){
        this.pitch = 127 - ((int) y / 10);
    }

    /**
     * Accessor method for volume
     *
     * @return Volume of the note
     */
    public int getVolume() {
        return this.VOLUME;
    }

    /**
     * Accessor method for when the note starts
     *
     * @return Starting tick of the note
     */
    public int getStartTick() {
        return this.startTick;
    }

    public void setStartTick(int x){
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

    public void setDuration(double duration){
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
     * Resizes the note's rectangle in the right direction.
     *
     * @param dx the distance to move the right edge
     */
    public void changeDuration(double dx) {
        if (this.getDuration() < MINIMUM_DURATION) {
            this.setDuration(MINIMUM_DURATION);
        } else {
            this.setDuration(this.getDuration() + dx);
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

    public void select() {
        this.selected = true;
    }

    public void unselect(){
        this.selected = false;
    }
}