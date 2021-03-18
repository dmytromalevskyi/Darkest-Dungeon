import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

final public class GUI extends JFrame implements ActionListener{
    Game game;
    int selectedCharacterIndex = 0;
    Character selectedCharacter;


    // Panels
    JPanel fightJPanel = new JPanel();
        JPanel playersTeamJPanel = new JPanel();
        JPanel currentEnemiesJPanel = new JPanel();
    JPanel abilitiesJPanel = new JPanel();
        JButton useAbilityButton;
    JPanel statsJPanel = new JPanel();
        JTextArea statsTextArea = new JTextArea();
    JPanel inventoryJPanel = new JPanel();
    JPanel mapJPanel = new JPanel();

    public GUI(int sizeOfTheMap){
        this.game = new Game(sizeOfTheMap);
        selectedCharacter = game.getPlayersTeam().get(selectedCharacterIndex);
        setTitle("Game");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ToolTipManager.sharedInstance().setInitialDelay(60);

        fightJPanel.setBackground(Color.cyan);
        abilitiesJPanel.setBackground(Color.BLUE);
        statsJPanel.setBackground(Color.GRAY);
        inventoryJPanel.setBackground(Color.MAGENTA);
        mapJPanel.setBackground(Color.GREEN);

        JPanel fightJPanel = new JPanel(); // CENTER PANEL - FIGHT PANEL
        fightJPanel.setLayout(new GridLayout(1,2,10,10)); 
        playersTeamJPanel.setBackground(Color.BLUE);
        fightJPanel.add(playersTeamJPanel);
        currentEnemiesJPanel.setBackground(Color.GRAY);
        fightJPanel.add(currentEnemiesJPanel);

        JPanel southJPanel = new JPanel(); // SOUTH PANEL
        southJPanel.setLayout(new GridLayout(1,4,5,5));
        southJPanel.add(abilitiesJPanel);
        southJPanel.add(statsJPanel);
        southJPanel.add(inventoryJPanel);
        southJPanel.add(mapJPanel);
        

        JButton attackButton = new JButton("Attack"); // ABILITY PANEL
        attackButton.setFocusable(false); attackButton.addActionListener(this);
        useAbilityButton = new JButton("Ability"); useAbilityButton.setFocusable(false); useAbilityButton.addActionListener(this);
        JButton skipButton = new JButton("Skip"); skipButton.setFocusable(false); skipButton.addActionListener(this);
        abilitiesJPanel.setLayout(new GridLayout(3,1,1,1));
        abilitiesJPanel.add(attackButton);
        abilitiesJPanel.add(useAbilityButton);
        abilitiesJPanel.add(skipButton);


        statsJPanel.setLayout(new GridLayout(1,1)); // STATS PANEL
        statsJPanel.add(statsTextArea);
        statsTextArea.setEditable(false);
        statsTextArea.setLineWrap(true);
        statsTextArea.setOpaque(false);
        statsTextArea.setWrapStyleWord(false);


        add(fightJPanel, BorderLayout.CENTER);
        add(southJPanel, BorderLayout.SOUTH);

        // Adding enemies
        List<Character> enemies = new ArrayList<>();    // ENEMY
        enemies.add(new Paladin(100, 0.2));
        enemies.add(new Thief(55, 0.15));
        enemies.add(new Preacher(70));           
        game.getMap().getCurrentTile().setEnemies(enemies);

        setVisible(true);
        ///pack();
        update();

        //fight();
        
        update();
    }

    public void update() {
        updateFightPanel(); // update fight panel
        updateAblilityPanel(); // update ability panel
        updateStatsPanel(); //update display of stats of the selected char.
        updateInvintoryPanel(); // update invintory panel
        // update map panel
        
        //change the length of the frame to refresh it (a bug that makes some elements not visible)
        this.setSize(getWidth()-1, getHeight()-1);
        this.setSize(getWidth()+1, getHeight()+1);
    }

    public void fight(){
        
    }

    public void updateInvintoryPanel(){
        List<Item> invintory = game.getInventory();

        inventoryJPanel.removeAll();
        inventoryJPanel.setLayout(new GridLayout(2,2,10,10)); // playersTeam PANEL      
        for (int i = 0; i < invintory.size(); i++) {
            Item item = invintory.get(i);
            JButton itemButton = new  JButton();

            itemButton.setActionCommand("Item: "+i); //use to identify that item is used what is its index
            itemButton.setText(item.getName()+" ("+ item.getNumberOf() +")");
            itemButton.setToolTipText(item.getDescription());
            itemButton.setFocusable(false);
            itemButton.addActionListener(this);

            inventoryJPanel.add(itemButton);
        }
    }

    public void updateStatsPanel() {
        if (selectedCharacter == null){
            statsTextArea.setText("No character is selected.");
        } else {
            statsTextArea.setText(selectedCharacter.toString());
        }
    }

    public void updateFightPanel(){
        List<Character> playersTeam = game.getPlayersTeam();
        List<Character> enemies = game.getMap().getCurrentEnemies();

        playersTeamJPanel.removeAll();
        playersTeamJPanel.setLayout(new GridLayout(1,playersTeam.size(),10,10)); // playersTeam PANEL      
        for (int i = 0; i < playersTeam.size(); i++) {
            Character character = game.getPlayersTeam().get(i);
            JLabel characterLabel = new JLabel(convertToMultiline(character.toString()));
            if (character.hasAnyBuffs()){
                //characterLabel.setToolTipText(character.getBuffsStatus());
                characterLabel.setToolTipText(convertToMultiline(character.getBuffsStatus()));
            }

            playersTeamJPanel.add(characterLabel);
        }

        currentEnemiesJPanel.removeAll();
        currentEnemiesJPanel.setLayout(new GridLayout(1,enemies.size(),10,10)); // currentEnemies PANEL     
        for (int i = 0; i < enemies.size(); i++) {
            Character character = game.getMap().getCurrentEnemies().get(i);
            JLabel characterLabel = new JLabel(convertToMultiline(character.toString()));
            if (character.hasAnyBuffs()){
                characterLabel.setToolTipText(convertToMultiline(character.getBuffsStatus()));
            }

            currentEnemiesJPanel.add(characterLabel);
        }
    }

    public void updateAblilityPanel(){
        // disable the ability button if on cooldown
        if(selectedCharacter == null){
            useAbilityButton.setEnabled(false);
            useAbilityButton.setToolTipText("No character is selected.");
        }else if(selectedCharacter.isCooldownZero()){
            useAbilityButton.setEnabled(true);
            useAbilityButton.setToolTipText(null);
        }else{
            useAbilityButton.setEnabled(false);
            useAbilityButton.setToolTipText("Cooldown: "+selectedCharacter.getCooldown()+ " round/s");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton){ // Buttons
            System.out.println("A button pressed: "+ e.getActionCommand());
            
            //System.out.println(inputIntDialog("Choose an int", "Input int", 3, true));
            //showMessageDialog("message", "title");

            String buttonActionCommand = e.getActionCommand(); // ABILITY panel buttons
            if (buttonActionCommand.equals("Attack")){
                int enemyToAttack = inputIntDialog("Enter index of the enemy to attack", "Attack", game.getMap().getCurrentEnemies().size(), true);
                if (enemyToAttack == -1) return; // return if canceled
                showMessageDialog(selectedCharacter.attack(game.getMap().getCurrentEnemies().get(enemyToAttack-1)), "Fight outcome");
                iterateSelectedCharacter();
            } else if (buttonActionCommand.equals("Ability")){
                if (selectedCharacter.getIsAbilityFriendly()){
                    int allyToUseAbilityOn = inputIntDialog("Name: "+ selectedCharacter.getAbilityName() + "\nDescription: "+ selectedCharacter.getAbilityDescription() +"\nEnter index of the ally to use ability on", "Use Frendly Ability", game.getPlayersTeam().size(), true);
                    if (allyToUseAbilityOn == -1) return; // return if canceled
                    selectedCharacter.useAbility(game.getPlayersTeam().get(allyToUseAbilityOn-1));
                }else{
                    int enemyToUseAbilityOn = inputIntDialog("Name: "+ selectedCharacter.getAbilityName() + "\nDescription: "+ selectedCharacter.getAbilityDescription() +"\nEnter index of the enemy to use ability on", "Use Non-Frendly Ability", game.getMap().getCurrentEnemies().size(), true);
                    if (enemyToUseAbilityOn == -1) return; // return if canceled
                    selectedCharacter.useAbility(game.getMap().getCurrentEnemies().get(enemyToUseAbilityOn-1));
                }                
                iterateSelectedCharacter();
            } else if (buttonActionCommand.equals("Skip")){
                iterateSelectedCharacter();
            }else if (buttonActionCommand.contains("Item: ")){
                System.out.print("Inventory item used. ");
                int indexOfItem = Integer.parseInt(buttonActionCommand.replace("Item: ", ""));
                System.out.println("Index is: "+ indexOfItem);
                Item itemUsed = game.getInventory().get(indexOfItem);

                if (itemUsed.getIsFriendly()){
                    int allyToUseItemOn = inputIntDialog("Name: "+ itemUsed.getName() + "\nDescription: "+ itemUsed.getDescription() +"\nEnter index of the ally to use item on", "Use Frendly Item", game.getPlayersTeam().size(), true);
                    if (allyToUseItemOn == -1) return; // return if canceled
                    showMessageDialog(itemUsed.use(game.getPlayersTeam().get(allyToUseItemOn-1)), "Using Item");
                }else{
                    int enemyToUseItemOn = inputIntDialog("Name: "+ itemUsed.getName() + "\nDescription: "+ itemUsed.getDescription() +"\nEnter index of the enemy to use item on", "Use Non-Frendly Item", game.getMap().getCurrentEnemies().size(), true);
                    if (enemyToUseItemOn == -1) return; // return if canceled
                    showMessageDialog(itemUsed.use(game.getMap().getCurrentEnemies().get(enemyToUseItemOn-1)), "Using Item");
                }

                game.updateInventory();
            } else { // OTHER buttons
                System.out.println("The button is not handled in actionPerformed function.");
            }
        }else{ // Non-buttons

        }
        update();
    }

    public void setSelectedCharacterToFirst(){
        selectedCharacterIndex = 0;
        selectedCharacter = game.getPlayersTeam().get(selectedCharacterIndex);
        updateStatsPanel();
    }

    public void iterateSelectedCharacter(){
        if(selectedCharacterIndex >= game.getPlayersTeam().size()-1){
            selectedCharacterIndex = 0;
            randomisedAttackForEnemies();
        }else
            selectedCharacterIndex++;
        
        selectedCharacter = game.getPlayersTeam().get(selectedCharacterIndex);
        selectedCharacter.decrementCooldown();
        if (selectedCharacter.hasAnyBuffs())
            selectedCharacter.updateBuffs();
        
        updateStatsPanel();
        game.removeDead();
        updateFightPanel();
    }

    // Randomised attact by the enemy
    public void randomisedAttackForEnemies() {
        List<Character> playersTeam = game.getPlayersTeam();
        List<Character> enemies = game.getMap().getCurrentEnemies();
        int prcToUseSpecialAbility = 20; // [0, 100] range
        
        if (!game.getMap().getCurrentTile().areEnemiesPresent()){
            showMessageDialog("No enemies to fight in this tile.", "Empty tile");
            return;
        }else{
            showMessageDialog("Enemies' turn", "Warning");
        }

        for (int i = 0; i < enemies.size(); i++) {
            if (game.isTeamDead()){
                showMessageDialog("The team is dead, you lost!", "Game Over");
                System.exit(0);
            }
            
            Character currentEnemy = enemies.get(i);
            String output = "";
            output += ("=== Current Enemy ("+ currentEnemy.getClassName() +") ["+(i+1)+"] ===\n");            
            currentEnemy.decrementCooldown();
            if (currentEnemy.hasAnyBuffs()) {
                output += ("\n"+currentEnemy.updateBuffs()+"\n");
            }

            if(!currentEnemy.isCooldownZero()){ // if cooldown is not 0, only attack
                output += ("Cooldown left for "+currentEnemy.getAbilityName()+" is "+ currentEnemy.getCooldown()+" round/s.\n");
                int playerToAttack = HelperClass.getRandomNumber(0, playersTeam.size()-1);
                output += ("Attacking Character ["+(playerToAttack+1)+"] ("+ playersTeam.get(playerToAttack).getClassName() +")\n");
                output += (currentEnemy.attack(playersTeam.get(playerToAttack))+"\n");
                showMessageDialog(output, "Enemy Attack");
                continue;
            }
            
            int randomNum = HelperClass.getRandomNumber(1, 100);
            if (randomNum <= (100 - prcToUseSpecialAbility)){ // Attac a team character
                int playerToAttack = HelperClass.getRandomNumber(0, playersTeam.size()-1);
                output += ("Attacking Character ["+(playerToAttack+1)+"] ("+ playersTeam.get(playerToAttack).getClassName() +")\n");
                output += (currentEnemy.attack(playersTeam.get(playerToAttack))+"\n");
            }else{ // Use special ability
                if (currentEnemy.getIsAbilityFriendly()){ // if true use on a random enemy
                    int enemyToCastAbilityOn = HelperClass.getRandomNumber(0, enemies.size()-1);
                    output += ("Using "+ currentEnemy.getAbilityName() +" on Enemy ["+(enemyToCastAbilityOn+1)+"] ("+ enemies.get(enemyToCastAbilityOn).getClassName() +")\n");
                    currentEnemy.useAbility(enemies.get(enemyToCastAbilityOn));
                }else{ // use on a team character
                    int charToCastAbilityOn = HelperClass.getRandomNumber(0, playersTeam.size()-1);
                    output += ("Using "+ currentEnemy.getAbilityName() +" on Character ["+(charToCastAbilityOn+1)+"] ("+ playersTeam.get(charToCastAbilityOn).getClassName() +")\n");
                    currentEnemy.useAbility(playersTeam.get(charToCastAbilityOn));
                }
                
            }

            game.removeDead();
            update();
            showMessageDialog(output, "Enemy Attack");
        }
        
    }

    public static String convertToMultiline(String orig){
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    // Ask user to choose an int from the int array
    //
    public int inputIntDialog(String message, String title, int[] values, boolean cancelable){
        Integer[] integerValues = Arrays.stream( values).boxed().toArray( Integer[]::new );

        Integer selectedInteger = (Integer) JOptionPane.showInputDialog(this, message, title, JOptionPane.DEFAULT_OPTION, null, integerValues, values[0]);
        if ( selectedInteger != null ){
            return selectedInteger.intValue();
        }else if (!cancelable){
            return inputIntDialog(message, title, values, cancelable);
        }else {
            return -1;
        }
    }

    // Overloading
    // Ask user to choose an int between min and max with dialog box
    //
    public int inputIntDialog(String message, String title, int min, int max, boolean cancelable){
        int[] values = new int[max-min+1];
        for (int i = 0; i < values.length; i++) {
            values[i] = min+i;
        } 

        return inputIntDialog(message, title, values, cancelable);
    }

    // Overloading
    // Ask user to chose a number bettween 1 and max
    //
    public int inputIntDialog(String message, String title, int max, boolean cancelable){
        return inputIntDialog(message, title, 1, max, cancelable);
    }

    public void showMessageDialog(String message, String title){
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.DEFAULT_OPTION);
    }
}