final public class Preacher extends Character{             // Support character
    // NO [Passive]

    public Preacher(int health){
        super("Preacher", health, (int) ((double) health / 10D), HelperClass.getRandomNumber(1, 4), (int) ((double) health / 20D));
    }

    // Give the buff for a given character
    //
    public void useAbility(Character victimCharacter){
        Buff preachertBuff = this.makeBuff();
        this.setCooldown(preachertBuff.getDuration());
        victimCharacter.getBuffs().add(preachertBuff);
    }
    
    // Configure the ability here
    //
    public Buff makeBuff() {
        // Paramiters
        double prc = 0.2;
        int damageEffect =  (int) (this.getHealth() * prc);
        int duration = 1;
        boolean isFriendly = true;

        // Create the buff that is to be applied
        Buff preachertBuff = new Buff("The Voices of Prayers", 
        "Adds "+(prc*100)+"% of this character's health to the damage of the chosen character for "+duration+" round/s. Additional damage works out to be "+ damageEffect+".",
        0, damageEffect, 0, 0, duration, isFriendly);

        return preachertBuff;
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
