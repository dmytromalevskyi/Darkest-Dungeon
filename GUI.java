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
        ToolTipManager.sharedInstance().setInitialDelay(0);

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
        
        /*
        // Player's team
        for (int i = 0; i < game.getPlayersTeam().size(); i++) {
            selectedCharacterIndex = i;
            selectedCharacter = game.getPlayersTeam().get(selectedCharacterIndex);
            if (i == 1)
                selectedCharacter.setCooldown(3);
            update();
    
            // some fight
            HelperClass.inputString("Player's fight loop for index: "+i);
        }
        */
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

            playersTeamJPanel.add(characterLabel);
        }

        currentEnemiesJPanel.removeAll();
        currentEnemiesJPanel.setLayout(new GridLayout(1,enemies.size(),10,10)); // currentEnemies PANEL     
        for (int i = 0; i < enemies.size(); i++) {
            Character character = game.getMap().getCurrentEnemies().get(i);
            JLabel characterLabel = new JLabel(convertToMultiline(character.toString()));

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
            useAbilityButton.setToolTipText("Cooldown: "+selectedCharacter.getCooldown());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton){ // Buttons
            System.out.println("A button pressed: "+ e.getActionCommand());
            
            //JOptionPane.showMessageDialog(this, "Enter a valid Number","Name of frame", JOptionPane.PLAIN_MESSAGE);
            // TODO add radio buttons to dialog window to apply stuff to team/enemies

            String buttonActionCommand = e.getActionCommand(); // ABILITY panel buttons
            if (buttonActionCommand.equals("Attack")){
                iterateSelectedCharacter();
            } else if (buttonActionCommand.equals("Ability")){
                iterateSelectedCharacter();
            } else if (buttonActionCommand.equals("Skip")){
                iterateSelectedCharacter();
            }else if (buttonActionCommand.contains("Item: ")){
                System.out.print("Inventory item used. ");
                int indexOfItem = Integer.parseInt(buttonActionCommand.replace("Item: ", ""));
                System.out.println("Index is: "+ indexOfItem);
            } else { // OTHER buttons
                System.out.println("The button is not handled in actionPerformed function.");
            }
        }else{

        }
    }

    public void setSelectedCharacterToFirst(){
        selectedCharacterIndex = 0;
        selectedCharacter = game.getPlayersTeam().get(selectedCharacterIndex);
        updateStatsPanel();
    }

    public void iterateSelectedCharacter(){
        if(selectedCharacterIndex >= game.getPlayersTeam().size()-1)
            selectedCharacterIndex = 0;
        else
            selectedCharacterIndex++;
        
        selectedCharacter = game.getPlayersTeam().get(selectedCharacterIndex);
        updateStatsPanel();
    }

    public static String convertToMultiline(String orig){
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}