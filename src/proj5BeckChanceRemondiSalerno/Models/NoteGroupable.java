/*
 * File: NoteGroupable.java
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
public interface NoteGroupable {

    ArrayList<Note> getNotes();
    boolean isSelected();
    void setSelected(boolean selected);
    void changeNoteDurations(double dx);
    double getDuration();
    int getStartTick();
    int getEndTick();
    int getMaxPitch();
    int getMinPitch();
    void changePitch(double dy);
    void changeStartTick(double dx);

}
