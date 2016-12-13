/**
 * NoteGroupableComparator.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */
package proj10BeckChanceRemondiSalerno;

import proj10BeckChanceRemondiSalerno.Models.NoteGroupable;

import java.util.Comparator;

/**
 * This class models a comparator for comparing notes
 *
 * @author Graham Chance
 * @author Charlie Beck
 * @author Ryan Salerno
 * @author Mike Remondi
 */
public class NoteGroupableComparator implements Comparator<NoteGroupable> {
    @Override
    public int compare(NoteGroupable o1, NoteGroupable o2) {
        return o1.getStartTick() - o2.getStartTick();
    }
}