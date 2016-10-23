/*
 * File: NoteGroup.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 5
 * Due Date: October 23, 2016
 */

package proj5BeckChanceRemondiSalerno.Models;

import java.util.ArrayList;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteGroup implements NoteGroupable {

    /**
     * the groupables contained in the group
     */
    ArrayList<NoteGroupable> noteGroupables;

    /**
     * current selection state of the group
     */
    private boolean isSelected = false;

    /**
     * Constructor
     * @param noteGroupables groupables
     */
    public NoteGroup(ArrayList<NoteGroupable> noteGroupables) {
        this.noteGroupables = noteGroupables;
    }

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
     * @return Whether the group is selected.
     */
    @Override
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Setter for selected
     * @param selected new selection state
     */
    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Getter for duration
     * @return the duration of the group
     */
    @Override
    public double getDuration() {
        return getEndTick() - getStartTick();
    }

    /**
     * Getter for the start tick
     * @return The start tick of the group
     */
    @Override
    public int getStartTick() {
        if (getNotes().isEmpty()) { return 0; }
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
     * @return the end tick of the group
     */
    @Override
    public int getEndTick() {
        if (getNotes().isEmpty()) { return 0; }
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
     * @return The max pitch in the group
     */
    @Override
    public int getMaxPitch() {
        if (getNotes().isEmpty()) { return 0; }
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
     * @return The min pitch for the group
     */
    @Override
    public int getMinPitch() {
        if (getNotes().isEmpty()) { return 0; }
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
     * @return all sub groupables of the group
     */
    public ArrayList<NoteGroupable> getNoteGroupables() {
        return noteGroupables;
    }

    /**
     * Changes the start tick of the group
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
     * @param dy how much to change the pitches by
     */
    @Override
    public void changePitch(double dy) {
        for (NoteGroupable noteGroupable : noteGroupables) {
            noteGroupable.changePitch(dy);
        }
    }
}
