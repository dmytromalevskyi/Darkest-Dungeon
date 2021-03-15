final public class Paladin extends Character{         //Strong tank character
    double chanceToBlock; // Chance to fully block an attack  [Passive]

    public Paladin(int health, double chanceToBlock){
        super("Paladin", health, (int) ((double) health / 15D), HelperClass.getRandomNumber(0, 2), (int) ((double) health / 8D));
        this.chanceToBlock = Math.min(chanceToBlock, 1D);
    }

    public String getAttacked(Character attackCharacter){ // for characters with passives
        double randomNum = Math.random();

        if (randomNum <= this.getChanceToBlock()){ // Blocked the attack fully
            String output = "";
            output += ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            output += ("Fully blocked!!!\n");
            output += ("The paladin had "+Math.round(this.getChanceToBlock()*100) + "% block chance.\n");    
            output += ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return output;
        }else{ // Take damage
            return super.getAttacked(attackCharacter);
        }    
    }

    // Give the buff for a given character
    //
    public void useAbility(Character victimCharacter){
        Buff paladinBuff = this.makeBuff();
        this.setCooldown(paladinBuff.getDuration());
        victimCharacter.getBuffs().add(paladinBuff);
    }
    
    // Configure the ability here
    //
    public Buff makeBuff() {
        // Paramiters
        double prc = 0.1;
        int defenceEffect =  (int) (this.getHealth() * prc);
        int duration = 4;
        boolean isFriendly = true;

        // Create the buff that is to be applied
        Buff paladinBuff = new Buff("The Blessing of The King", 
        "Adds "+(prc*100)+"% of this character's health to defence of the chosen character for "+duration+" round/s. Defence buff works out to be "+ defenceEffect+".", 
        0, 0, 0, defenceEffect, duration, isFriendly);

        return paladinBuff;
    }

    public void setChanceToBlock(double chanceToBlock) {
        this.chanceToBlock = chanceToBlock;
    }

    public double getChanceToBlock(){
        return this.chanceToBlock;
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
