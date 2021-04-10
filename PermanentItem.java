public final class PermanentItem extends Item{
    private int healthEffect;
    private int damageEffect;
    private int agilityEffect;
    private int defenceEffect;

    private static final long serialVersionUID = 1L;
    
    public PermanentItem(String name, String description, int numberOf, boolean isFriendly, int healthEffect, int damageEffect, int agilityEffect, int defenceEffect){
        super(name, description, numberOf, isFriendly);
        this.healthEffect = healthEffect;
        this.damageEffect = damageEffect;
        this.agilityEffect = agilityEffect;
        this.defenceEffect = defenceEffect;
    }

    public String use(Character victimCharacter){
        Character charBeforeItem = victimCharacter.clone();
        this.decrementAmount();
        String output = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        output += "Using "+ this.getName() + " on "+ victimCharacter.getClassName() + "\n";
        if (healthEffect != 0){
            victimCharacter.setHealth(victimCharacter.getHealth() + this.healthEffect);
            output += "New health "+charBeforeItem.getHealth()+" ~~> "+ victimCharacter.getHealth() +"\n";
        }if (damageEffect != 0){
            victimCharacter.setDamage(victimCharacter.getDamage() + this.damageEffect);
            output += "New damage "+charBeforeItem.getDamage()+" ~~> "+ victimCharacter.getDamage() +"\n";
        }if (agilityEffect != 0){
            victimCharacter.setAgility(victimCharacter.getAgility() + this.agilityEffect);
            output += "New agility "+charBeforeItem.getAgility()+" ~~> "+ victimCharacter.getAgility() +"\n";
        }if (defenceEffect != 0){
            victimCharacter.setDefence(victimCharacter.getDefence() + this.defenceEffect);
            output += "New defence "+charBeforeItem.getDefence()+" ~~> "+ victimCharacter.getDefence() +"\n";
        }
        
        if (this.getNumberOf() != 0)
            output += "Number of "+this.getName()+" left in the inventory: "+ this.getNumberOf()+"\n";
        else 
            output += "No "+this.getName()+" left in the inventory.\n";
        
        output += ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        return output;
    }

    public static PermanentItem getRedPotion(int healthEffect, int numberOf) {
        return new PermanentItem("Red Potion", "Adds "+healthEffect+" point to health of a chosen character.", numberOf, true, 
        healthEffect, 0, 0, 0);
    }

    public static PermanentItem getNastyPoison(int healthEffect, int numberOf) {
        return new PermanentItem("Nasty Poison", "Takes "+healthEffect+" point from health of a chosen enemy. Note: it ignores any defence points.", numberOf, false, 
        (-1) * healthEffect, 0, 0, 0);
    }

    public int getHealthEffect() {
        return this.healthEffect;
    }

    public void setHealthEffect(int healthEffect) {
        this.healthEffect = healthEffect;
    }

    public int getDamageEffect() {
        return this.damageEffect;
    }

    public void setDamageEffect(int damageEffect) {
        this.damageEffect = damageEffect;
    }

    public int getAgilityEffect() {
        return this.agilityEffect;
    }

    public void setAgilityEffect(int agilityEffect) {
        this.agilityEffect = agilityEffect;
    }

    public int getDefenceEffect() {
        return this.defenceEffect;
    }

    public void setDefenceEffect(int defenceEffect) {
        this.defenceEffect = defenceEffect;
    }
} 