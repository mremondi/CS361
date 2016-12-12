/**
 * MusicalInterpreter.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno.LSystem;

import proj10BeckChanceRemondiSalerno.CompositionManager;
import proj10BeckChanceRemondiSalerno.Models.Note;

import java.awt.*;
import java.util.Stack;

/**
 * A class that essentially translates between the LSystem string and the
 * actual notes that are dictated by it
 */
public class MusicalInterpreter {

    /**
     * The composition manger to be used to allow notes to be added to the composition
     */
    private CompositionManager compositionManager;

    /**
     * Constructor
     *
     * @param compositionManager the composition manager
     */
    public MusicalInterpreter(CompositionManager compositionManager){
        this.compositionManager = compositionManager;
    }

    /**
     * Converts a given string to notes to place on the composition
     *
     * @param dstring the LSystem string
     * @param duration the standard note duration
     * @param startingPitch the starting pitch
     */
    public void stringToNotes(String dstring, int duration, int startingPitch){
        String modString = "";
        int modVal = -1;
        boolean modGrab = false;
        Stack<Point> stateStack = new Stack<>();
        Point currentPosition = new Point(0, startingPitch);

        Stack<Integer> instrumentStack = new Stack<>();
        Integer currentInstrument = 0;

        for (char c: dstring.toCharArray()) {
            if (c == '(') {
                modString = "";
                modGrab = true;
                continue;
            } else if (c == ')') {
                modVal = Integer.valueOf(modString);
                modGrab = false;
                continue;
            } else if (modGrab) {
                modString += c;
                continue;
            }

            if (c == 'F'){
                if (modVal == -1){
                    this.compositionManager.addGroupable(new Note(currentPosition.x,
                            currentPosition.y, duration, currentInstrument));
                    currentPosition.x += duration;
                    if (currentPosition.x > 1900){
                        currentPosition.x = 1900;
                    }
                }
                else{
                    this.compositionManager.addGroupable(new Note(currentPosition.x,
                            currentPosition.y,
                            modVal,
                            currentInstrument));
                }
            }
            else if (c == '['){
                stateStack.push((Point)currentPosition.clone());
            }
            else if (c == ']'){
                currentPosition = stateStack.pop();

            }
            else if (c == '-'){
                if (modVal == -1) {
                    currentPosition.y += 10;
                    if (currentPosition.y > 1260) {
                        currentPosition.y = 1260;
                    }
                }
                else{
                    currentPosition.y += modVal;
                    if (currentPosition.y > 1260) {
                        currentPosition.y = 1260;
                    }
                }
            }
            else if (c == '+'){
                if (modVal == -1) {
                    currentPosition.y -= 10;
                    if (currentPosition.y < 0) {
                        currentPosition.y = 0;
                    }
                }
                else{
                    currentPosition.y -= modVal;
                    if (currentPosition.y > 0) {
                        currentPosition.y = 0;
                    }
                }
            }
            else if (c == '_'){
                if (modVal == -1) {
                    currentPosition.x += duration;
                    if (currentPosition.x > 1900) {
                        currentPosition.x = 1900;
                    }
                }
                else{
                    currentPosition.x += duration;
                    if (currentPosition.x > 1900) {
                        currentPosition.x = 1900;
                    }
                }
            }
            else if (c == '<'){
                instrumentStack.push(currentInstrument);
            }
            else if (c == '>'){
                currentInstrument = instrumentStack.pop();
            }
            else if (c == 'p'){
                currentInstrument = 0;
            }
            else if (c == 'h'){
                currentInstrument = 1;
            }
            else if (c == 'm'){
                currentInstrument = 2;
            }
            else if (c == 'o'){
                currentInstrument = 3;
            }
            else if (c == 'a'){
                currentInstrument = 4;
            }
            else if (c == 'g'){
                currentInstrument = 5;
            }
            else if (c == 'v'){
                currentInstrument = 6;
            }
            else if (c == 'f'){
                currentInstrument = 7;
            }
            modVal = -1;
        }
    }
}
