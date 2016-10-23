package proj5BeckChanceRemondiSalerno.Models;

import java.util.ArrayList;

/**
 * Created by mremondi on 10/20/16.
 */
public class NoteGroup implements Groupable {

    ArrayList<Groupable> groupables;
    private boolean isSelected = false;

    public NoteGroup(ArrayList<Groupable> groupables) {
        this.groupables = groupables;
    }

    @Override
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (Groupable group : groupables) {
            for (Note note : group.getNotes()) {
                notes.add(note);
            }
        }
        return notes;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public double getDuration() {
        return getEndTick() - getStartTick();
    }

    @Override
    public int getStartTick() {
        if (getNotes().isEmpty()) { return 0; }
        int minStartTick = groupables.get(0).getStartTick();
        for (Groupable groupable : groupables) {
            if (groupable.getStartTick() < minStartTick) {
                minStartTick = groupable.getStartTick();
            }
        }
        return minStartTick;
    }

    @Override
    public int getEndTick() {
        if (getNotes().isEmpty()) { return 0; }
        int maxEndTick = groupables.get(0).getEndTick();
        for (Groupable groupable : groupables) {
            if (groupable.getEndTick() > maxEndTick) {
                maxEndTick = groupable.getEndTick();
            }
        }
        return maxEndTick;
    }

    @Override
    public void changeNoteDurations(double dx) {
        for (Groupable groupable : groupables) {
            groupable.changeNoteDurations(dx);
        }
    }

    @Override
    public int getMaxPitch() {
        if (getNotes().isEmpty()) { return 0; }
        int maxPitch = groupables.get(0).getMaxPitch();
        for (Groupable groupable : groupables) {
            if (groupable.getMaxPitch() > maxPitch) {
                maxPitch = groupable.getMaxPitch();
            }
        }
        return maxPitch;
    }

    @Override
    public int getMinPitch() {
        if (getNotes().isEmpty()) { return 0; }
        int minPitch = groupables.get(0).getMinPitch();
        for (Groupable groupable : groupables) {
            if (groupable.getMinPitch() < minPitch) {
                minPitch = groupable.getMinPitch();
            }
        }
        return minPitch;
    }

    public ArrayList<Groupable> getGroupables() {
        return groupables;
    }

    @Override
    public void changeStartTick(double dx) {
        for (Groupable groupable : groupables) {
            groupable.changeStartTick(dx);
        }
    }

    @Override
    public void changePitch(double dy) {
        for (Groupable groupable : groupables) {
            groupable.changePitch(dy);
        }
    }
}
