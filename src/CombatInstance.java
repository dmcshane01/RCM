
public class CombatInstance {
	
	
	int type; //type = 0 if player is attacker, 1 if opponent is attacker
	String[] entry; //attacker reads from log either "You" or "Player"
	final int TIME = 0;
	final int ATTACKER = 1;
	final int ATTACKER_ID = 2;
	final int TARGET = 3;
	final int TARGET_ID = 4;
	final int WEAPON = 5;
	final int PROJECTILE = 6;
	final int AOI = 7;
	final int DISTANCE = 8;
	final int OLD_HP = 9;
	final int NEW_HP = 10;
	double damage;
	String output;
	
	
	//Constructor takes in a string array that represents each entry in the combat log
	public CombatInstance(String[] entry)
	{
		this.entry = entry;
		calculateDamage();
		createOutput();
		
	}
	
	//sets damage amount done
	public void calculateDamage()
	{
		damage = Double.parseDouble(entry[OLD_HP]) - Double.parseDouble(entry[NEW_HP]);
	}
	
	
	public void createOutput()
	{
		
		if(entry[ATTACKER].equalsIgnoreCase("you"))
		{
			output = entry[ATTACKER] + " hit player " + entry[TARGET_ID] + "for " + damage + " with a " + entry[WEAPON] + " from " + entry[DISTANCE] + " " + entry[TIME] + " ago";
		}
		else if(entry[ATTACKER].equalsIgnoreCase("player"))
		{
			output =  entry[ATTACKER] + " " + entry[ATTACKER_ID] + "Hit you with a "+ entry[WEAPON] + "for " + damage + " from " + entry[DISTANCE] + " " + entry[TIME] + " ago";

		}
		
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
