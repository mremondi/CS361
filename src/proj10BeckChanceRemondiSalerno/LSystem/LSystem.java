/**
 * LSystem.java
 * Names: Graham Chance, Charlie Beck, Ryan Salerno, Mike Remondi
 * Class: CS361
 * Project: 10
 * Due Date: December 19, 2016
 */

package proj10BeckChanceRemondiSalerno.LSystem;

import java.io.*;
import java.util.HashMap;
import java.util.Optional;

/**
 * This class models an L-System where it takes a file and applies rules
 * to a base string and outputs a more complex result string.
 */
public class LSystem{
    /**
     * The file path to open
     */
    private String filename;

    /**
     * The base string to be manipulated
     */
    private String base;

    /**
     * The map of rules for a given character
     */
    private HashMap<String, String> rules;

    /**
     * Constructor
     *
     * @param filename the file path to be opened
     */
    public LSystem(Optional<String> filename){
        this.filename = filename.get();
        this.base = "";
        this.rules = new HashMap<>();
    }

    /**
     * Setter method for the base field.
     *
     * @param newBase a string to be set to the base field
     */
    private void setBase(String newBase){
        this.base = newBase;
    }

    /**
     * Adds a character and a corresponding rule to the rules map.
     * @param ruleKey the key for the corresponding rule
     * @param newRule the rule to be applied
     */
    private void addRule(String ruleKey, String newRule){
        this.rules.put(ruleKey, newRule);
    }

    /**
     * Reads the file and gets the base and any set of rules
     */
    public void read(){
        File file = new File(this.filename);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = reader.readLine()) != null) {
                String[] sArray = text.split(" ");
                if (sArray[0].equals("base")){
                    setBase(sArray[1]);
                }
                else if(sArray[0].equals("rule")){
                    addRule(sArray[1], sArray[2]);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * Applies the rules to each character in the input string
     *
     * @param input The string to be manipulated
     *
     * @return the result String that has been changed according to the rules map.
     */
    private String applyRules(String input){
        String resultString = "";
        for (int i=0; i<input.length(); i++){
            String c = input.substring(i,i+1);
            if (this.rules.containsKey(c)){
                resultString += this.rules.get(c);
            }
            else{
                resultString += c;
            }
        }
        return resultString;
    }

    /**
     * Builds the fina l string by applying the rules over several iterations
     *
     * @param iterations The number of times to apply all the rules
     * @return the resulting string
     */
    public String buildString(int iterations){
        String tempString = this.base;
        for (int i=0; i<iterations; i++){
            tempString = this.applyRules(tempString);
        }
        return tempString;
    }

    /**
     * Test method
     *
     * @param args any command line arguments
     */
    public static void main(String args[]){
        String filename = "systemH.txt";
        int iterations = 3;
        LSystem lsys = new LSystem(Optional.of(filename));
        lsys.read();
        String test = lsys.buildString(iterations);
        System.out.println(test);
    }
}