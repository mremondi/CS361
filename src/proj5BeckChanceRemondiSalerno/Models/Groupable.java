package proj5BeckChanceRemondiSalerno.Models;

import javafx.geometry.Bounds;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by mremondi on 10/20/16.
 */
public interface Groupable {

    public ArrayList<Note> getNotes();
    public boolean isSelected();
    public void setSelected(boolean selected);
    public void changeNoteDurations(double dx);
    public double getDuration();
    public int getStartTick();
    public int getEndTick();
}
