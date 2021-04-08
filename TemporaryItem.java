public final class TemporaryItem extends Item {
    private Buff temporaryEffect;
    
    private static final long serialVersionUID = 1L;
    
    public TemporaryItem(String name, String description, int numberOf, boolean isFriendly, Buff temporaryEffect){
        super(name, description, numberOf, isFriendly);
        this.temporaryEffect = temporaryEffect;
    }

    public String use(Character victimCharacter) {
        Character charBeforItem = victimCharacter.clone();
        victimCharacter.getBuffs().add(this.getTemporaryEffect());
        // victimCharacter is now updated

        this.decrementAmount();
        String output = ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        output += "Using "+ this.getName() + " on "+ victimCharacter.getClassName() + "\n";
        output += "The effect will last for " + temporaryEffect.getDuration() + " round/s.";
        if (temporaryEffect.getHealthEffect() != 0){
            output += "New health "+charBeforItem.getHealth()+" ~~> "+ victimCharacter.getHealth() +"\n";
        }if (temporaryEffect.getDamageEffect() != 0){
            output += "New damage "+charBeforItem.getDamage()+" ~~> "+ victimCharacter.getDamage() +"\n";
        }if (temporaryEffect.getAgilityEffect() != 0){
            output += "New agility "+charBeforItem.getAgility()+" ~~> "+ victimCharacter.getAgility() +"\n";
        }if (temporaryEffect.getDefenceEffect() != 0){
            output += "New defence "+charBeforItem.getDefence()+" ~~> "+ victimCharacter.getDefence() +"\n";
        }
        
        if (this.getNumberOf() != 0)
            output += "Number of "+this.getName()+" left in the inventory: "+ this.getNumberOf()+"\n";
        else 
            output += "No "+this.getName()+" left in the inventory.\n";
        
        output += ("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        return output;
    }

    public static TemporaryItem getFishingNet(int agilityEffect, int duration, int numberOf) {
        String name = "Fishing Net";
        String description =  "Takes away "+agilityEffect+" point/s of agility from a given enemy for "+duration+" round/s.";
        Buff temporaryEffect = new Buff(name, description, 0, 0, (-1) * agilityEffect, 0, duration, false);
        return new TemporaryItem( name, description, numberOf, false, temporaryEffect);
    }

    public static TemporaryItem getHydroAcid(int defenceEffect, int duration, int numberOf) {
        String name = "Hydro Acid";
        String description =  "Takes away "+defenceEffect+" point/s of defence from a given enemy for "+duration+" round/s.";
        Buff temporaryEffect = new Buff(name, description, 0, 0, 0, (-1) * defenceEffect, duration, false);
        return new TemporaryItem( name, description, numberOf, false, temporaryEffect);
    }

    public Buff getTemporaryEffect() {
        return this.temporaryEffect;
    }

    public void setTemporaryEffect(Buff temporaryEffect){
        this.temporaryEffect = temporaryEffect;
    }
}