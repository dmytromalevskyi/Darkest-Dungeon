final public class Paladin extends Character{         //Strong tank character

    public Paladin(int health, int damage, int agility, int defence){
        super(health, damage, agility, defence);
    }

    // Give the buff for a given character
    //
    public void useAbility(Character victimCharacter){
        Buff paladinBuff = this.makeBuff();
        victimCharacter.getBuffs().add(paladinBuff);
    }
    
    // Configure the ability here
    //
    public Buff makeBuff() {
        // Paramiters
        double prc = 0.1;
        int defenceAffect =  (int) (this.getHealth() * prc);
        int duration = 2;

        // Create the buff that is to be applied
        Buff paladinBuff = new Buff("The blessing of the King", 
        "Adds "+(prc*100)+"% of this character's health to defence of the chosen character for "+duration+" rounds. Defence buff works out to be "+ defenceAffect+".", 
        0, 0, 0, defenceAffect, duration, true);

        return paladinBuff;
    }
    
    // Return if the special ability is friendly
    //
    public boolean getIsAbilityFriendly() {
        return this.makeBuff().getIsFriendly();
    }

    // Return description of the buff for a given paladin
    //
    public String getAbilityDescription(){
        return makeBuff().getDescription();
    }

    // Return description of the buff for a given paladin
    //
    public String getAbilityName(){
        return makeBuff().getName();
    }
}
