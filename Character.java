import java.util.ArrayList;
import java.util.List;

public class Character {
    //private String name;
    private int health;
    private int damage;
    private int agility;
    private int defence;
    //private int haste; //change to attac back when being hit 
    private List<Buff> buffs = new ArrayList<>();


    public Character(int health, int damage, int agility, int defence) {
        this.health =  health;
        this.damage = damage;
        this.agility =  agility; // range [1-10]
        this.defence =  defence;
    }

    // attack  the victim character and calculate the chance of a miss
    //
    public void attack(Character victimCharacter){
        int agilityDifferecne = victimCharacter.getAgility() - this.getAgility();
        int damage;
        double missChance;

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        if (agilityDifferecne <= 0){ // no change for a miss
            damage = Math.max(this.getDamage() - victimCharacter.getDefence(), 0);
            System.out.println(damage + " damage delt!");
            System.out.println(victimCharacter.getDefence() + " damage blocked by the victime.");
            System.err.println("Attacker had no chance to miss.");
            System.out.println("New victim's health " + victimCharacter.getHealth() + " ~~> " + Math.max(0, (victimCharacter.getHealth() - damage)));
        }else{
            missChance = 0.05 * agilityDifferecne; // 5% for every point
            double randomNum = Math.random();

            if (randomNum <= missChance){ // missed
                damage = 0;
                System.out.println("Missed!!!");
                System.out.println("The attacker had "+Math.round(missChance*100) + "% miss chance.");
            }else{
                damage = Math.max(this.getDamage() - victimCharacter.getDefence(), 0);
                System.out.println(damage + " damage delt!");
                System.out.println(victimCharacter.getDefence() + " damage blocked by the victime.");
                System.out.println("The attacker had "+Math.round(missChance*100) + "% miss chance.");
                System.out.println("New victim's health " + victimCharacter.getHealth() + " ~~> " + Math.max(0, (victimCharacter.getHealth() - damage)) );
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        victimCharacter.setHealth(victimCharacter.getHealth() - damage);
    }

    public void updateBuffs(){
        for (int i = 0; i < this.getBuffs().size(); i++) {            
            if(this.getBuffs().get(i).getDuration() > 0){
                System.out.println(this.getBuffs().get(i).getName() + " will last for " + this.getBuffs().get(i).getDuration() + " round/s more.");
                // Decrement each buffs' duration
                this.getBuffs().get(i).decrementDuration();
            }else{ // Remove buffs that are expired
                System.out.println(this.getBuffs().get(i).getName() + " has just finished.");
                this.getBuffs().remove(i);
                i--;
            }
        }
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
            healthAfterBuffs += this.getBuffs().get(i).getHealthAffect();
        }
        return healthAfterBuffs;
    }

    public void setHealth(int newHealth) {
        this.health = Math.max(0, newHealth);
    }

    public int getDamage() {
        if(!this.hasAnyBuffs()) // has no buffs
            return this.damage;

        int damageAfterBuffs = this.damage;
        for (int i = 0; i < this.getBuffs().size(); i++) { // has buffs
            damageAfterBuffs += this.getBuffs().get(i).getDamageAffect();
        }
        return damageAfterBuffs;
    }

    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }

    public int getAgility() {
        if(!this.hasAnyBuffs()) // has no buffs
            return this.agility;

        int agilityAfterBuffs = this.agility;
        for (int i = 0; i < this.getBuffs().size(); i++) { // has buffs
            agilityAfterBuffs += this.getBuffs().get(i).getAgilityAffect();
        }
        return agilityAfterBuffs;
    }

    public void setAgility(int newAgility) {
        this.agility = newAgility;
    }

    public int getDefence() {
        if(!this.hasAnyBuffs()) // has no buffs
            return this.defence;

        int degenceAfterBuffs = this.defence;
        for (int i = 0; i < this.getBuffs().size(); i++) { // has buffs
            degenceAfterBuffs += this.getBuffs().get(i).getDefenceAffect();
        }
        return degenceAfterBuffs;
    }

    public void setDefence(int newDefence) {
        this.defence = newDefence;
    }
    
    public List<Buff> getBuffs(){
        return this.buffs;
    }

    public void setBuffs(List<Buff> newBuffs){
        this.buffs = newBuffs;
    }
}