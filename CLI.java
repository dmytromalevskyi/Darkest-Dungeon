import java.util.ArrayList;
import java.util.List;

final public class CLI{
    Game game;

    public CLI(int sizeOfTheMap){
    this.game = new Game(sizeOfTheMap);

    }

    public void play() {
        if (true) {
            //game.getMap().draw();
            //System.out.println("Current tile: "+(map.getCurrentCoordinates()[1]+1)+","+(map.getCurrentCoordinates()[0]+1));
            //this.fight();

            //map.move(new byte[] {1,1});
            //map.draw();
            //System.out.println("Current tile: "+(map.getCurrentCoordinates()[1]+1)+","+(map.getCurrentCoordinates()[0]+1));
            
            List<Character> enemies = new ArrayList<>();    // ENEMY
            enemies.add(new Paladin(100, 0.2));
            enemies.add(new Thief(55, 0.15));
            enemies.add(new Preacher(70));           
            game.getMap().getCurrentTile().setEnemies(enemies);

            fight();
        }
        
    }

        // Enter "fight" or tell user that the tile is empty
    //
    public void fight() {
        if (!game.getMap().getCurrentTile().areEnemiesPresent()) { // if no enemies
            System.out.println("There are no enemies to fight in this tile.");
            return;
        }
        
        // if there are enemies in this tile...
        System.out.println("");
        while (game.getMap().getCurrentTile().areEnemiesPresent() && !game.isTeamDead()) { // check if enemies or team is dead

            // Player's turn
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<< Your Turn >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            for (int i = 0; i < game.getPlayersTeam().size(); i++) {
                Character currentCharacter = game.getPlayersTeam().get(i);

                System.out.println("========== Current Character ("+ currentCharacter.getClassName() +") [" + (i + 1) + "] ==========");
                currentCharacter.decrementCooldown();
                if (currentCharacter.hasAnyBuffs()) {
                    System.out.println("");
                    System.out.println(currentCharacter.updateBuffs());
                }

                drawFight();

                String cooldowString = (currentCharacter.isCooldownZero()) ? "" : " (cooldown: "+String.valueOf(currentCharacter.getCooldown())+" round/s)";
                while (true) { // single character loop                    
                    int userInput = HelperClass.inputInt(
                            "Enter a number for what you want to do: [1] Basic attack, [2] Special ability"+cooldowString+", [3] Open Inventory, [4] Skip: ",
                            1, 4);
                    if (userInput == 1) { // basic attack
                        userInput = HelperClass.inputInt("Enter a number for the enemy you want to attack (0 to cancel): ", 0, game.getMap().getCurrentEnemies().size());
                        if (userInput == 0) continue;
                        System.out.println(currentCharacter.attack(game.getMap().getCurrentEnemies().get(userInput-1)));
                    
                    } else if (userInput == 2) { // special ability
                        if (!currentCharacter.isCooldownZero()){
                            System.out.println("You cannot use "+ currentCharacter.getAbilityName() +" right now as there is "+ currentCharacter.getCooldown()+" round/s cooldown left.");
                            continue;
                        }
                        System.out.println("Name of the ability: " + currentCharacter.getAbilityName());
                        System.out.println("Description: " + currentCharacter.getAbilityDescription());

                        // Apply the buff to an enemy or team
                        List<Character> charactersToApplyAbility = new ArrayList<>();
                        if (currentCharacter.getIsAbilityFriendly()) 
                            charactersToApplyAbility = game.getPlayersTeam();
                        else
                            charactersToApplyAbility = game.getMap().getCurrentEnemies();
                        

                        userInput = HelperClass.inputInt("Enter a number for the character you want to use "+ currentCharacter.getAbilityName() + " ability on (0 to cancel): ",0, charactersToApplyAbility.size());
                        if (userInput == 0)
                            continue;
                        else { // apply the special ability
                            currentCharacter.useAbility(charactersToApplyAbility.get(userInput - 1));
                        }
                    
                    } else if (userInput == 3){ // Use an item
                        drawInventory();
                        userInput = HelperClass.inputInt("Enter a number of the item you want to use (0 to cancel): ", 0, game.getInventory().size());
                        if (userInput == 0)
                            continue;
                        else{ // use the item
                            Item currentItem = game.getInventory().get(userInput-1);
                            
                            // Apply the item to an enemy or team
                            List<Character> charactersToApplyItemTo = new ArrayList<>();
                            if (currentItem.getIsFriendly())
                                charactersToApplyItemTo = game.getPlayersTeam();
                            else
                                charactersToApplyItemTo = game.getMap().getCurrentEnemies();
                                
                            userInput = HelperClass.inputInt("Enter a number for the character you want to use "+ currentItem.getName() + " item on (0 to cancel): ",0, charactersToApplyItemTo.size());
                            if (userInput == 0)
                                continue;
                            else { // apply the item
                                currentItem.use(charactersToApplyItemTo.get(userInput-1));;
                            }
                            game.updateInventory(); // remove finished items
                            drawFight();
                            continue;
                        }
                    } else if (userInput == 4) { // Skipp turn
                        System.out.println("Turn skipped.");
                    }

                    game.removeDead();
                    break; // stop asking the used for this character
                }
            }

            if (game.getMap().getCurrentTile().areEnemiesPresent() && !game.isTeamDead()) {
                // Enemies' turn
                randomisedAttackForEnemies();
            }

            if (!game.getMap().getCurrentTile().areEnemiesPresent()) {
                System.out.println("\n<---ALL ENAMIES DEFETED!--->");
            } else if (game.isTeamDead()) {
                System.out.println("\n<---GAME OVER--->");
                System.exit(0);
            }
        }
    }

    // Draw the "fight"
    //
    public void drawFight() {
        if (!game.areEnemiesPresentHere()) { // if no enemies
            System.out.println("There are no enemies to draw in this tile.");
            return;
        }

        // if there are enemies in this tile...
        System.out.println("\n------------------------------------------------------------");
        this.printCharactersInfo();
        System.out.println("------------------------------------------------------------\n");
    }

    // Print all characters' info
    //
    public void printCharactersInfo() {
        String seperation = "\t\t";
        String smallSeperation = "\t";
        int numOfCharacters = game.getPlayersTeam().size();
        int numOfEnemies = game.getMap().getCurrentEnemies().size();

        for (int i = 0; i < numOfCharacters; i++) { // team character labels
            if (i == 0)
                System.out.print(smallSeperation + "[" + (i + 1) + "]" + smallSeperation);
            else if (i != numOfCharacters - 1)
                System.out.print("[" + (i + 1) + "]" + smallSeperation);
            else
                System.out.print("[" + (i + 1) + "]");
        }

        System.out.print(seperation); // space between

        for (int i = 0; i < numOfEnemies; i++) { // team character labels
            if (i == 0)
                System.out.print("[" + (i + 1) + "]" + smallSeperation);
            else if (i != numOfCharacters - 1)
                System.out.print("[" + (i + 1) + "]" + smallSeperation);
            else
                System.out.print("[" + (i + 1) + "]");
        }
        System.out.println();

        int totalNumOfCharacters = numOfCharacters + numOfEnemies;
        // HEALTH
        System.out.print("Health" + smallSeperation);
        for (int i = 0; i < totalNumOfCharacters; i++) { // print the atributes
            if (i < numOfCharacters) { // it is player's team
                int health = game.getPlayersTeam().get(i).getHealth();

                if (i == 0)
                    System.out.print(health + smallSeperation);
                else if (i != numOfCharacters - 1)
                    System.out.print(health + smallSeperation);
                else
                    System.out.print(health);
            } else { // it is an enemy
                if (i == numOfCharacters)
                    System.out.print(seperation);
                int health = game.getMap().getCurrentEnemies().get(i - numOfCharacters).getHealth();

                if (i != totalNumOfCharacters - 1)
                    System.out.print(health + smallSeperation);
                else
                    System.out.print(health);
            }
        }
        // DAMAGE
        System.out.print("\nDamage" + smallSeperation);
        for (int i = 0; i < totalNumOfCharacters; i++) { // print the atributes
            if (i < numOfCharacters) { // it is player's team
                int damage = game.getPlayersTeam().get(i).getDamage();

                if (i == 0)
                    System.out.print(damage + smallSeperation);
                else if (i != numOfCharacters - 1)
                    System.out.print(damage + smallSeperation);
                else
                    System.out.print(damage);
            } else { // it is an enemy
                if (i == numOfCharacters)
                    System.out.print(seperation);
                int damage = game.getMap().getCurrentEnemies().get(i - numOfCharacters).getDamage();

                if (i != totalNumOfCharacters - 1)
                    System.out.print(damage + smallSeperation);
                else
                    System.out.print(damage);
            }
        }
        // AGILITY
        System.out.print("\nAgility" + smallSeperation);
        for (int i = 0; i < totalNumOfCharacters; i++) { // print the atributes
            if (i < numOfCharacters) { // it is player's team
                int agility = game.getPlayersTeam().get(i).getAgility();

                if (i == 0)
                    System.out.print(agility + smallSeperation);
                else if (i != numOfCharacters - 1)
                    System.out.print(agility + smallSeperation);
                else
                    System.out.print(agility);
            } else { // it is an enemy
                if (i == numOfCharacters)
                    System.out.print(seperation);
                int agility = game.getMap().getCurrentEnemies().get(i - numOfCharacters).getAgility();

                if (i != totalNumOfCharacters - 1)
                    System.out.print(agility + smallSeperation);
                else
                    System.out.print(agility);
            }
        }
        // DEFENCE
        System.out.print("\nDefence" + smallSeperation);
        for (int i = 0; i < totalNumOfCharacters; i++) { // print the atributes
            if (i < numOfCharacters) { // it is player's team
                int defence = game.getPlayersTeam().get(i).getDefence();

                if (i == 0)
                    System.out.print(defence + smallSeperation);
                else if (i != numOfCharacters - 1)
                    System.out.print(defence + smallSeperation);
                else
                    System.out.print(defence);
            } else { // it is an enemy
                if (i == numOfCharacters)
                    System.out.print(seperation);
                int defence = game.getMap().getCurrentEnemies().get(i - numOfCharacters).getDefence();

                if (i != totalNumOfCharacters - 1)
                    System.out.print(defence + smallSeperation);
                else
                    System.out.print(defence);
            }
        }

        System.out.println();
    }

    public void drawInventory() {
        if (game.getInventory().size() <= 0){
            System.out.println("Your inventory is empty.");
            return;
        }

        System.out.println("<+=----------------------------------------=+>");
        System.out.println("The item inside your bag is/are: ");
        for (int i = 0; i < game.getInventory().size(); i++) {
            Item currentItem = game.getInventory().get(i);
            System.out.println("["+(i+1)+"] "+currentItem.getName()+ " ("+currentItem.getNumberOf()+")");
            System.out.println(currentItem.getDescription());
        }
        System.out.println("<+=----------------------------------------=+>");

    }

    // Randomised attact by the enemy
    public void randomisedAttackForEnemies() {
        List<Character> playersTeam = game.getPlayersTeam();
        List<Character> enemies = game.getMap().getCurrentEnemies();
        int prcToUseSpecialAbility = 20; // [0, 100] range

        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<< Enemies' Turn >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
        for (int i = 0; i < enemies.size(); i++) {
            if (game.isTeamDead())
                return;
            
            Character currentEnemy = enemies.get(i);
            System.out.println("========== Current Enemy ("+ currentEnemy.getClassName() +") ["+(i+1)+"] ==========");            
            currentEnemy.decrementCooldown();
            if (currentEnemy.hasAnyBuffs()) {
                System.out.println("");
                System.out.println(currentEnemy.updateBuffs());
            }
            this.drawFight();
            HelperClass.inputString("Press enter to continue: ");
            /*try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            if(!currentEnemy.isCooldownZero()){ // if cooldown is not 0, only attack
                System.out.println("Cooldown left for "+currentEnemy.getAbilityName()+" is "+ currentEnemy.getCooldown()+" round/s.");
                int playerToAttack = HelperClass.getRandomNumber(0, playersTeam.size()-1);
                System.out.println("Attacking Character ["+(playerToAttack+1)+"] ("+ playersTeam.get(playerToAttack).getClassName() +")");
                System.out.println(currentEnemy.attack(playersTeam.get(playerToAttack)));
                continue;
            }
            
            int randomNum = HelperClass.getRandomNumber(1, 100);
            if (randomNum <= (100 - prcToUseSpecialAbility)){ // Attac a team character
                int playerToAttack = HelperClass.getRandomNumber(0, playersTeam.size()-1);
                System.out.println("Attacking Character ["+(playerToAttack+1)+"] ("+ playersTeam.get(playerToAttack).getClassName() +")");
                System.out.println(currentEnemy.attack(playersTeam.get(playerToAttack)));
            }else{ // Use special ability
                if (currentEnemy.getIsAbilityFriendly()){ // if true use on a random enemy
                    int enemyToCastAbilityOn = HelperClass.getRandomNumber(0, enemies.size()-1);
                    System.out.println("Using "+ currentEnemy.getAbilityName() +" on Enemy ["+(enemyToCastAbilityOn+1)+"] ("+ enemies.get(enemyToCastAbilityOn).getClassName() +")");
                    currentEnemy.useAbility(enemies.get(enemyToCastAbilityOn));
                }else{ // use on a team character
                    int charToCastAbilityOn = HelperClass.getRandomNumber(0, playersTeam.size()-1);
                    System.out.println("Using "+ currentEnemy.getAbilityName() +" on Character ["+(charToCastAbilityOn+1)+"] ("+ playersTeam.get(charToCastAbilityOn).getClassName() +")");
                    currentEnemy.useAbility(playersTeam.get(charToCastAbilityOn));
                }
                
            }

            game.removeDead();
        }
        
    }
}