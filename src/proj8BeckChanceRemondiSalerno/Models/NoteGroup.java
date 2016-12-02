/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 8
 * Due Date: November 18, 2016
 */


package proj8BeckChanceRemondiSalerno.Models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * This class models a group of musical notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
@XmlRootElement(name = "group")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoteGroup implements NoteGroupable {

    /**
     * the groupables contained in the group
     */
    @XmlElements({
            @XmlElement(name = "note", type = Note.class),
            @XmlElement(name = "group", type = NoteGroup.class)
    })

    /**
     * an arraylist of the noteGroupables contained in this NoteGroupable
     */
    private ArrayList<NoteGroupable> noteGroupables;

    /**
     * current selection state of the group
     */
    private boolean isSelected = false;

    /**
     * Constructor
     *
     * @param noteGroupables groupables
     */
    public NoteGroup(ArrayList<NoteGroupable> noteGroupables) {
        this.noteGroupables = noteGroupables;
    }

    /**
     * No argument constructor for the compiler
     */
    public NoteGroup(){}

    /**
     * Gets all notes contained within the group
     *
     * @return the notes in the group
     */
    @Override
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (NoteGroupable group : noteGroupables) {
            for (Note note : group.getNotes()) {
                notes.add(note);
            }
        }
        return notes;
    }

    /**
     * Getter for current selection state
     *
     * @return Whether the group is selected.
     */
    @Override
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Setter for selected
     *
     * @param selected new selection state
     */
    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Getter for duration
     *
     * @return the duration of the group
     */
    @Override
    public double getDuration() {
        return getEndTick() - getStartTick();
    }

    /**
     * Getter for the start tick
     *
     * @return The start tick of the group
     */
    @Override
    public int getStartTick() {
        if (getNotes().isEmpty()) {
            return 0;
        }
        int minStartTick = noteGroupables.get(0).getStartTick();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getStartTick() < minStartTick) {
                minStartTick = noteGroupable.getStartTick();
            }
        }
        return minStartTick;
    }

    /**
     * getter for end tick
     *
     * @return the end tick of the group
     */
    @Override
    public int getEndTick() {
        if (getNotes().isEmpty()) {
            return 0;
        }
        int maxEndTick = noteGroupables.get(0).getEndTick();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getEndTick() > maxEndTick) {
                maxEndTick = noteGroupable.getEndTick();
            }
        }
        return maxEndTick;
    }

    /**
     * Changes all the note durations in the group
     *
     * @param dx how much to change it by
     */
    @Override
    public void changeNoteDurations(double dx) {
        for (NoteGroupable noteGroupable : noteGroupables) {
            noteGroupable.changeNoteDurations(dx);
        }
    }

    /**
     * Getter for the max pitch
     *
     * @return The max pitch in the group
     */
    @Override
    public int getMaxPitch() {
        if (getNotes().isEmpty()) {
            return 0;
        }
        int maxPitch = noteGroupables.get(0).getMaxPitch();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getMaxPitch() > maxPitch) {
                maxPitch = noteGroupable.getMaxPitch();
            }
        }
        return maxPitch;
    }

    /**
     * Getter for min pitch
     *
     * @return The min pitch for the group
     */
    @Override
    public int getMinPitch() {
        if (getNotes().isEmpty()) {
            return 0;
        }
        int minPitch = noteGroupables.get(0).getMinPitch();
        for (NoteGroupable noteGroupable : noteGroupables) {
            if (noteGroupable.getMinPitch() < minPitch) {
                minPitch = noteGroupable.getMinPitch();
            }
        }
        return minPitch;
    }

    /**
     * Gets the groupables contained in the group
     *
     * @return all sub groupables of the group
     */
    public ArrayList<NoteGroupable> getNoteGroupables() {
        return noteGroupables;
    }

    /**
     * Changes the start tick of the group
     *
     * @param dx changes the start tick of the group
     */
    @Override
    public void changeStartTick(double dx) {
        for (NoteGroupable noteGroupable : noteGroupables) {
            noteGroupable.changeStartTick(dx);
        }
    }

    /**
     * Changes the pitches of all the sub groups
     *
     * @param dy how much to change the pitches by
     */
    @Override
    public void changePitch(double dy) {
        for (NoteGroupable noteGroupable : noteGroupables) {
            noteGroupable.changePitch(dy);
        }
    }

    /**
     * Clones method that clones this note groupable
     *
     * @return a clone of this NoteGroupable
     */
    @Override
    public NoteGroup clone() {
        NoteGroup clone;
        try {
            clone = (NoteGroup) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
            return null;
        }
        ArrayList<NoteGroupable> clonedNotes = new ArrayList<>();
        for (NoteGroupable note : noteGroupables) {
            clonedNotes.add(note.clone());
        }
        clone.noteGroupables = clonedNotes;
        return clone;
    }

    /**
     * Setter for channel
     * @param channel New channel
     */
    @Override
    public void setChannel(int channel) {
        for(NoteGroupable noteGroupable: noteGroupables) {
            noteGroupable.setChannel(channel);
        }
    }

    /**
     * Setter for channel
     * @param volume New volume
     */
    @Override
    public void setVolume(int volume) {
        for (NoteGroupable noteGroupable: noteGroupables) {
            noteGroupable.setVolume(volume);
        }
    }
}
