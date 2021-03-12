import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

final public class GUI extends JFrame implements ActionListener{
    Game game;
    Character selectedCharacter;

    // Panels
    JPanel fightJPanel = new JPanel();
        JPanel playersTeamJPanel = new JPanel();
        JPanel currentEnemiesJPanel = new JPanel();
    JPanel abilitiesJPanel = new JPanel();
        JButton useAbilityButton;
    JPanel statsJPanel = new JPanel();
        JTextArea statsTextArea;
    JPanel inventoryJPanel = new JPanel();
    JPanel mapJPanel = new JPanel();

    public GUI(int sizeOfTheMap){
        this.game = new Game(sizeOfTheMap);
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
        attackButton.setFocusable(false);
        useAbilityButton = new JButton("Ability"); useAbilityButton.setFocusable(false);
        JButton skipButton = new JButton("Skip"); skipButton.setFocusable(false);
        abilitiesJPanel.setLayout(new GridLayout(3,1,1,1));
        abilitiesJPanel.add(attackButton);
        abilitiesJPanel.add(useAbilityButton);
        abilitiesJPanel.add(skipButton);
        useAbilityButton.setToolTipText("Cooldown: 0");


        statsJPanel.setLayout(new GridLayout(1,1)); // STATS PANEL

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
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        game.getPlayersTeam().remove(0);
        selectedCharacter = game.getPlayersTeam().get(1);
        update();
        update();
    }

    public void update() {
        updateFightPanel(); // update fight panel
        //updateAblilityPanel(); // update ability panel
        updateStatsPanel();
        // update invintory panel
        // update map panel
    }

    public void updateStatsPanel() {
        statsJPanel.removeAll();

        if (selectedCharacter == null){
            statsTextArea = new JTextArea("No character is selected.");
        } else {
            statsTextArea = new JTextArea(selectedCharacter.toString());
        }

        statsTextArea.setEditable(false);
        statsTextArea.setLineWrap(true);
        statsTextArea.setOpaque(false);
        statsTextArea.setWrapStyleWord(false);
        statsJPanel.add(statsTextArea);

    }

    public void updateFightPanel(){
        List<Character> playersTeam = game.getPlayersTeam();
        List<Character> enemies = game.getMap().getCurrentEnemies();

        playersTeamJPanel.removeAll();
        playersTeamJPanel.setLayout(new GridLayout(1,playersTeam.size(),10,10)); // playersTeam PANEL      
        System.out.println(playersTeam.size());
        for (int i = 0; i < playersTeam.size(); i++) {
            Character character = game.getPlayersTeam().get(i);
            JLabel characterLabel = new JLabel(convertToMultiline(character.toString()));

            playersTeamJPanel.add(characterLabel);
        }

        currentEnemiesJPanel.removeAll();
        currentEnemiesJPanel.setLayout(new GridLayout(1,enemies.size(),10,10)); // currentEnemies PANEL     
        System.out.println(enemies.size());
        for (int i = 0; i < enemies.size(); i++) {
            Character character = game.getMap().getCurrentEnemies().get(i);
            JLabel characterLabel = new JLabel(convertToMultiline(character.toString()));

            currentEnemiesJPanel.add(characterLabel);
        }
    }

    public void updateAblilityPanel(){
        // disable the ability button if on cooldown
        /*if(game.getSelectedCharacter().isCooldownZero()){
            useAbilityButton.setEnabled(true);
        }else{
            useAbilityButton.setEnabled(false);
            // TODO: add a comment for mouse hover
        }
        */

    }

    @Override
    public void actionPerformed(ActionEvent e) {
              
    }

    public static String convertToMultiline(String orig){
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}