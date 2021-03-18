public class Item{
    private String name;
    private String description;
    private int numberOf;
    private boolean isFriendly;
    
    public Item(String name, String description, int numberOf, boolean isFriendly){
        this.name = name;
        this.description = description;
        this.numberOf = numberOf;
        this.isFriendly = isFriendly;
    }

    public String use(Character victimCharacter){
        // for polymorphism
        return "";
    }

    public void decrementAmount() {
        this.setNumberOf(getNumberOf() - 1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setNumberOf(int numberOf) {
        this.numberOf = numberOf;
    }

    public int getNumberOf() {
        return this.numberOf;
    }

    public void setIsFriendly(boolean isFriendly) {
        this.isFriendly = isFriendly;
    }

    public boolean getIsFriendly() {
        return this.isFriendly;
    }
}