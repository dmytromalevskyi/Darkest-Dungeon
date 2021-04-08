import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Character implements Serializable{
    //private String name;
    private String className;
    private int health;
    private int damage;
    private int agility; //range [0,10] 
    private int defence;
    private int cooldown; //cooldown till next ability can be applied 
    private List<Buff> buffs = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public Character(String className, int health, int damage, int agility, int defence) {
        this.className = className;
        this.health =  health;
        this.damage = damage;
        this.agility =  agility; // range [1-10]
        this.defence =  defence;
        this.cooldown = 0;
    }

    // attack  the victim character and calculate the chance of a miss
    //
    public String attack(Character victimCharacter){
        return victimCharacter.getAttacked(this);
    }

    public String getAttacked(Character attackCharacter){ //to be overriden for characters with passives
        Character victimCharacter = this;
        String output = "";

        int agilityDifferecne = this.getAgility() - attackCharacter.getAgility();
        int damage;
        double missChance;

        output += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        if (agilityDifferecne <= 0){ // no change for a miss
            damage = Math.max(attackCharacter.getDamage() - this.getDefence(), 0);
            output += (damage + " damage delt!\n");
            output += (this.getDefence() + " damage blocked by the victim.\n");
            output += ("Attacker had no chance to miss.\n");
            output += ("New victim's health " + this.getHealth() + " ~~> " + Math.max(0, (this.getHealth() - damage)) + "\n");
        }else{
            missChance = 0.05 * agilityDifferecne; // 5% for every point
            double randomNum = Math.random();

            if (randomNum <= missChance){ // missed
                damage = 0;
                output += ("Missed!!!\n");
                output += ("The attacker had "+Math.round(missChance*100) + "% miss chance.\n");
            }else{
                damage = Math.max(attackCharacter.getDamage() - this.getDefence(), 0);
                output += (damage + " damage delt!\n");
                output += (this.getDefence() + " damage blocked by the victime.\n");
                output += ("The attacker had "+Math.round(missChance*100) + "% miss chance.\n");
                output += ("New victim's health " + this.getHealth() + " ~~> " + Math.max(0, (this.getHealth() - damage)) + "\n");
            }
        }
        output += ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        victimCharacter.setHealth(victimCharacter.getHealth() - damage);
        return output;
    }

    public String updateBuffs(){
        String output = "";
        for (int i = 0; i < this.getBuffs().size(); i++) {            
            if(this.getBuffs().get(i).getDuration() > 0){
                output += (this.getBuffs().get(i).getName() + " will last for " + this.getBuffs().get(i).getDuration() + " round/s more.\n");
                // Decrement each buffs' duration
                this.getBuffs().get(i).decrementDuration();
            }else{ // Remove buffs that are expired
                output += (this.getBuffs().get(i).getName() + " has just finished.\n");
                this.getBuffs().remove(i);
                i--;
            }
        }
        return output;
    }

    public String getBuffsStatus(){
        String output = "";
        for (int i = 0; i < this.getBuffs().size(); i++) {            
            if(this.getBuffs().get(i).getDuration() > 0){
                output += (this.getBuffs().get(i).getName() + " will last for " + this.getBuffs().get(i).getDuration() + " round/s more.\n");
            }else{ 
                output += (this.getBuffs().get(i).getName() + " will finish next round.\n");
            }
        }
        return output;
    }

    // Clone a given character
    //
    public Character clone(){
        Character characterCopy = new Character(
            className,
            health,
            damage,
            agility, 
            defence);
        
        List<Buff> buffsCopy = new ArrayList<>();
        for (int i = 0; i < getBuffs().size(); i++) {
            buffsCopy.add(getBuffs().get(i).clone());
        }
        characterCopy.setBuffs(buffsCopy);
        characterCopy.setCooldown(this.getCooldown());

        return characterCopy;
    }

    // Get stats of the character
    //
    public String toString() {
        String output = "";
        output += "Class: " + getClassName() + "\n";
        output += "Health: " + getHealth() + "\n";
        output += "Damage: " + getDamage() + "\n";
        output += "Agility: " + getAgility() + "\n";
        output += "Defence: " + getDefence();
        return output;
    }

    // Check if the character has any buffs
    //
    public boolean hasAnyBuffs(){
        if(this.getBuffs().size() > 0)
            return true;
        else
            return false;
    }

    // Return if the special ability is friendly
    //
    public boolean getIsAbilityFriendly() {
        return false; //for polymorthism
    }

    // Return description of the buff (for polymorthism)
    //
    public String getAbilityDescription(){
        return "Characters have no abilities";
    }

    // Return name of the buff (for polymorthism)
    //
    public String getAbilityName(){
        return "Characters have no abilities";
    }


    // Use special ability
    //
    public void useAbility(Character victimCharacter){
        return; //do nothing (for polymorthism)
    }

    public int getHealth() {
        if(!this.hasAnyBuffs()) // has no buffs
            return this.health;

        int healthAfterBuffs = this.health;
        for (int i = 0; i < this.getBuffs().size(); i++) { // has buffs
            healthAfterBuffs += this.getBuffs().get(i).getHealthEffect();
        }
        return Math.max(0, healthAfterBuffs);
    }

    public void setHealth(int newHealth) {
        this.health = Math.max(0, newHealth);
    }

    public int getDamage() {
        if(!this.hasAnyBuffs()) // has no buffs
            return this.damage;

        int damageAfterBuffs = this.damage;
        for (int i = 0; i < this.getBuffs().size(); i++) { // has buffs
            damageAfterBuffs += this.getBuffs().get(i).getDamageEffect();
        }
        return Math.max(0, damageAfterBuffs);
    }

    public void setDamage(int newDamage) {
        this.damage = Math.max(0, newDamage);
    }

    public int getAgility() {
        if(!this.hasAnyBuffs()) // has no buffs
            return this.agility;

        int agilityAfterBuffs = this.agility;
        for (int i = 0; i < this.getBuffs().size(); i++) { // has buffs
            agilityAfterBuffs += this.getBuffs().get(i).getAgilityEffect();
        }
        return Math.max(Math.min(agilityAfterBuffs, 10), 0); //range [0,10]
    }

    public void setAgility(int newAgility) {
        this.agility = Math.max(Math.min(newAgility, 10), 0); //range [0,10]
    }

    public int getDefence() {
        if(!this.hasAnyBuffs()) // has no buffs
            return this.defence;

        int degenceAfterBuffs = this.defence;
        for (int i = 0; i < this.getBuffs().size(); i++) { // has buffs
            degenceAfterBuffs += this.getBuffs().get(i).getDefenceEffect();
        }
        return Math.max(0, degenceAfterBuffs);
    }

    public void setDefence(int newDefence) {
        this.defence = Math.max(0, newDefence);
    }
    
    public List<Buff> getBuffs(){
        return this.buffs;
    }

    public void setBuffs(List<Buff> newBuffs){
        this.buffs = newBuffs;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return this.className;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void decrementCooldown() {
        this.cooldown = Math.max(0, this.cooldown - 1);
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public boolean isCooldownZero(){
        return (this.getCooldown() <= 0);
    }
}