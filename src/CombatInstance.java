/**
 * CombatInstance:
 * The CombatInstance object represents each damage inducing interaction from player combat. It only parses combat instances that were from you -> player/object or other player -> you
 * Basically  it takes an array of strings as an argument for the constructor, determines if its a combat instance, then formats the output to be more readable
 * <p>
 * Example input of log
 * 317.9s  you      329136 player        221531 assets/prefabs/weapons/stone pickaxe/stone_pickaxe.entity.prefab N/A                       head    0.7m     15.5   8.7
 * 313.8s  player   215465 you           329136 bow_hunting.entity                                               arrow_wooden              head    21.5m    6.5    0.0
 * 49.5s   you      329136 player        221531 bow_hunting.entity                                               arrow_wooden              head    11.7m    52.7   0.0
 * 40.1s   you      329136 player        282810 bow_hunting.entity                                               arrow_wooden              hand    10.3m    61.5   11.5
 * 6.3s    player   91671  you           329136 pistol_semiauto.entity                                           pistolbullet              head    18.6m    64.6   4.0
 * 2.4s    you      329136 player        91671  shotgun_waterpipe.entity                                         handmade_shell.projectile head    5.5m     90.2   84.6
 */

import java.math.BigDecimal;

public class CombatInstance {

    /**Define constant strings*/
    final static String YOU = "you";
    final static String PLAYER = "player";
    final static int TIME = 0;
    final static int ATTACKER = 1;
    final static int ATTACKER_ID = 2;
    final static int TARGET = 3;
    final static int TARGET_ID = 4;
    final static int WEAPON = 5;
    final static int PROJECTILE = 6;
    final static int AOI = 7;
    final static int DISTANCE = 8;
    final static int OLD_HP = 9;
    final static int NEW_HP = 10;

    String[] entry;
    double damage;
    String output;

    /**
     * Constructor receives string array to be used as combat instance
     * throws exception and terminates if it receives an invalid string
     * if valid the constructor calls methods to format ouput string
     */
    public CombatInstance(String[] entry) {
        try {
            if (!CombatInstance.isValid(entry))
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        this.entry = entry;
        calculateDamage();
        createOutput();
    }

    /**
     * static method that determines if a line read is a valid combat instance and not garbage
     */
    public static boolean isValid(String[] line) {

        //if the input line is null or less than 6 entries long, we know it is not a valid combat instance
        if (line == null || line.length < 10 || line[AOI].equals("generic")) {
            return false;
        } else if (line[ATTACKER].equals(YOU) || line[TARGET].equals(YOU)) {
            return true;
        }
        return false;
    }

    /**
     * remove whitespace from hp values
     */
    public void formatHPVal() {
        entry[OLD_HP] = entry[OLD_HP].replaceAll("[^\\d.]", "");
        entry[NEW_HP] = entry[NEW_HP].replaceAll("[^\\d.]", "");
    }

    /**
     * Use BigDecimal class to calculate damage done in this instance by subtracting newHp from oldHp
     */
    public void calculateDamage() {
        formatHPVal();
        BigDecimal oldHP = BigDecimal.valueOf(Double.parseDouble(entry[OLD_HP]));
        BigDecimal newHP = BigDecimal.valueOf(Double.parseDouble(entry[NEW_HP]));

        damage = oldHP.subtract(newHP).doubleValue();
    }

    /**
     * Format string output to be readable by user
     */
    public void createOutput() {

        if (entry[ATTACKER].equalsIgnoreCase(YOU)) {
            output = entry[TIME] + " ago: " + entry[ATTACKER] + " hit PLAYER " + entry[TARGET_ID] + " in the " + entry[AOI] + " for " + damage + " from " + entry[DISTANCE] + " to " + entry[NEW_HP] + "\n";
        } else if (entry[ATTACKER].equalsIgnoreCase(PLAYER)) {
            output = entry[TIME] + " ago YOU were hit with a " + entry[WEAPON] + "for " + damage + " from " + entry[DISTANCE] + "\n";
        }
    }

    /**
     * returns string of current instance
     */
    public String toString() {
        return output;
    }


}
