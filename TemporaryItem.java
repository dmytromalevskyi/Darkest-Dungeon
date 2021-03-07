public final class TemporaryItem extends Item{
    private Buff temporaryEffect;
    
    public TemporaryItem(String name, String description, int numberOf, boolean isFriendly, Buff temporaryEffect){
        super(name, description, numberOf, isFriendly);
        this.temporaryEffect = temporaryEffect;
    }

    public void use(Character victimCharacter) {
        victimCharacter.getBuffs().add(this.getTemporaryEffect());

        this.decrementAmount();
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