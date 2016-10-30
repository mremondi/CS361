/*
 * File: Controller.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 6
 * Due Date: November 1, 2016
 */


package proj5BeckChanceRemondiSalerno.Models;


import java.util.ArrayList;

/**
 * This interface models something that can be grouped with other groupables on a composition
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
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
