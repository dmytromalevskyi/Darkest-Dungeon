public class Buff {
    //List<Buff> ints = new ArrayList<Buff>();

    private String name;
    private String description;
    // Atributes affected
    private int healthAffect;
    private int damageAffect;
    private int agilityAffect;
    private int defenceAffect; 
    private int duration;   // (rounds)
    private boolean isFriendly; // determines if you want to use it on your allis

    public Buff(String name, String description, int healthAffect,int damageAffect,
    int agilityAffect,int defenceAffect,int duration, boolean isFriendly){
        this.name = name;
        this.description = description;
        this.healthAffect = healthAffect;
        this.damageAffect =  damageAffect;
        this.agilityAffect = agilityAffect;
        this.defenceAffect = defenceAffect; 
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

    public int getDefenceAffect(){
        return this.defenceAffect;
    }
    
    public int getAgilityAffect(){
        return this.agilityAffect;
    }
    
    public int getDamageAffect(){
        return this.damageAffect;
    }

    public int getHealthAffect(){
        return this.healthAffect;
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