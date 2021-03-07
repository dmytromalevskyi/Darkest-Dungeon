public class Buff {
    //List<Buff> ints = new ArrayList<Buff>();

    private String name;
    private String description;
    // Atributes affected
    private int healthEffect;
    private int damageEffect;
    private int agilityEffect;
    private int defenceEffect; 
    private int duration;   // (rounds)
    private boolean isFriendly; // determines if you want to use it on your allis

    public Buff(String name, String description, int healthEffect,int damageEffect,
    int agilityEffect,int defenceEffect,int duration, boolean isFriendly){
        this.name = name;
        this.description = description;
        this.healthEffect = healthEffect;
        this.damageEffect =  damageEffect;
        this.agilityEffect = agilityEffect;
        this.defenceEffect = defenceEffect; 
        this.duration = duration;
        this.isFriendly = isFriendly;
    }

    //  Decrease the duration of buff by 1
    //
    public void decrementDuration() {
        this.duration = this.duration - 1;
    }

    public String getDescription(){
        return this.description;
    }

    public int getDuration(){
        return this.duration;
    }

    public int getDefenceEffect(){
        return this.defenceEffect;
    }
    
    public int getAgilityEffect(){
        return this.agilityEffect;
    }
    
    public int getDamageEffect(){
        return this.damageEffect;
    }

    public int getHealthEffect(){
        return this.healthEffect;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setIsFriendly(boolean isFriendly){
        this.isFriendly = isFriendly;
    }

    public boolean getIsFriendly(){
        return this.isFriendly;
    }
}