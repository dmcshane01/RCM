
public class CombatInstance {
	
	
	int type; //type = 0 if player is attacker, 1 if opponent is attacker
	String[] line; //attacker reads from log either "You" or "Player"
	String attacker;
	String victim;
	String oldHP;
	String newHP;
	String damageDone;
	String weapon;
	
	
	public CombatInstance(String[] line)
	{
		this.line = line;
		assignValues(s);
		
	}
	
	
	//need to grab a log sample so I can properly assign these
	public void assignValues()
	{
		
		attacker = line[0];
		victim = line[2];
		
		if(attacker.equals("you"))
		{
			type = 0;
		}
		else
		{
			type = 1;
		}
	}
	

}
