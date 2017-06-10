/*
 * CombatInstance:
 * The CombatInstance object represents each damage inducing interaction from player combat. It only parses combat instances that were from you -> player/object or other player -> you
 * Basically  it takes an array of strings as an argument for the constructor, determines if its a combat instance, then formats the output to be more readable
 */

import java.math.BigDecimal;

public class CombatInstance {
	
	
	int type; //type = 0 if player is attacker, 1 if opponent is attacker
	String[] entry; //attacker reads from log either "You" or "Player"
	final static String you = "you";
	final static String player = "player";
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
	double damage;
	String output;
	
	
	//Constructor takes in a string array that represents each entry in the combat log
	//will be fed by LogReader
	//Constructor calls all the essential methods upon initializatio nso that once you have instantiated A combat intance you only need to call toString
	public CombatInstance(String[] entry)
	{
		this.entry = entry;
		calculateDamage();
		createOutput();
		
	}
	
	//remove whitespace from hp values
	public void formatHPVal()
	{
		entry[OLD_HP] = entry[OLD_HP].replaceAll("[^\\d.]", "");
		entry[NEW_HP] = entry[NEW_HP].replaceAll("[^\\d.]", "");
	}
	
	
	//sets damage amount done using bigDecimal
	public void calculateDamage()
	{
		
		formatHPVal();		
		//convert double to bigDecimal because java's double arithmetic is sad
		BigDecimal oldHP = BigDecimal.valueOf(Double.parseDouble(entry[OLD_HP]));
		BigDecimal newHP = BigDecimal.valueOf(Double.parseDouble(entry[NEW_HP]));;
		
		
		damage = oldHP.subtract(newHP).doubleValue();
	}
	
	
	//
	public void createOutput()
	{
		
		if(entry[ATTACKER].equalsIgnoreCase(you))
		{
			output = entry[TIME] + " ago: " + entry[ATTACKER] + " " + entry[ATTACKER_ID] +  " hit player " + entry[TARGET_ID] + " for " + damage + " with a " + entry[WEAPON] + " from " + entry[DISTANCE] + "\n";
		}
		else if(entry[ATTACKER].equalsIgnoreCase(player))
		{
			output =  entry[TIME] + " ago: " + entry[ATTACKER] + " " + entry[ATTACKER_ID] + " Hit you with a "+ entry[WEAPON] + "for " + damage + " from " + entry[DISTANCE] + " " + entry[TIME]+ "\n";

		}
		
	}
	
	
	//static method that determines if a line read is a valid combat instance and not garbage
	public static boolean isValid(String[] line)
	{
		
		//if the input line is null or less than 6 entries long, we know it is not a valid combat instance
		if(line == null || line.length < 6)
		{
			return false;
		}
		//if we know its not null and is >= 6 entries, all we need to determine is if the first entry is 'you' or 'Player' to determine validity
		if(line[ATTACKER].equals(you) || line[TARGET].equals(you))
			return true;
		return false;
	}

	public String toString()
	{
		return output;
	}
	
	

	/*
	 * 317.9s  you      329136 player        221531 assets/prefabs/weapons/stone pickaxe/stone_pickaxe.entity.prefab N/A                       head    0.7m     15.5   8.7            
	 * 313.8s  player   215465 you           329136 bow_hunting.entity                                               arrow_wooden              head    21.5m    6.5    0.0            
     * 49.5s   you      329136 player        221531 bow_hunting.entity                                               arrow_wooden              head    11.7m    52.7   0.0            
     * 40.1s   you      329136 player        282810 bow_hunting.entity                                               arrow_wooden              hand    10.3m    61.5   11.5           
     * 6.3s    player   91671  you           329136 pistol_semiauto.entity                                           pistolbullet              head    18.6m    64.6   4.0            
     * 2.4s    you      329136 player        91671  shotgun_waterpipe.entity                                         handmade_shell.projectile head    5.5m     90.2   84.6           
	 */
}
