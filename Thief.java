final public class Thief extends Character{             // Light and fast damage dealer
    double chanceToAttackBack; // Chance to attack back [Passive]

    public Thief(int health, double chanceToAttackBack){
        super("Thief", health, (int) ((double) health / 3D), HelperClass.getRandomNumber(3, 10), 0);
        this.chanceToAttackBack = Math.min(chanceToAttackBack, 1D);
    }

    public void getAttacked(Character attackCharacter){ // for characters with passives
        super.getAttacked(attackCharacter);
        
        double randomNum = Math.random();
        if (randomNum <= this.getChanceToAttackBack()){ // Attack back
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Attacks back!!!");
            System.out.println("The thief had "+Math.round(this.getChanceToAttackBack()*100) + "% attach-back chance.");    
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            this.attack(attackCharacter);
        }            
    }

    // Give the buff for a given character
    //
    public void useAbility(Character victimCharacter){
        Buff thiefBuff = this.makeBuff();
        this.setCooldown(thiefBuff.getDuration());
        victimCharacter.getBuffs().add(thiefBuff);
    }
    
    // Configure the ability here
    //
    public Buff makeBuff() {
        // Paramiters
        double prc = 0.3;
        int agilityAffect =  (int) (this.getAgility() * prc);
        int duration = 3;
        boolean isFriendly = true;

        // Create the buff that is to be applied
        Buff paladinBuff = new Buff("Thief's Dance", 
        "Adds "+(prc*100)+"% of this character's agility to agility of the chosen character for "+duration+" round/s. Agility buff works out to be "+ agilityAffect +".", 
        0, 0, agilityAffect, 0, duration, isFriendly);

        return paladinBuff;
    }

    public void setChanceToAttackBack(double chanceToAttackBack) {
        this.chanceToAttackBack = chanceToAttackBack;
    }

    public double getChanceToAttackBack(){
        return this.chanceToAttackBack;
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
