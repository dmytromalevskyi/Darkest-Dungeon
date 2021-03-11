import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import java.util.ArrayList;
import java.util.List;

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

        ///---------------------------------------------------------//
        String character1 = "Paladin";
        JLabel label1 = new JLabel();
        ImageIcon icon1 = new ImageIcon("Graphics/"+character1+".png");
        label1.setText("Graphics/"+character1+".png");
        label1.setIcon(icon1);
        playersTeamJPanel.add(label1);

        String character2 = "Paladin";
        JLabel label2 = new JLabel();
        ImageIcon icon2 = new ImageIcon("Graphics/"+character2+".png");
        label1.setText("Graphics/"+character2+".png");
        label1.setIcon(icon2);
        playersTeamJPanel.add(label2);
        ///---------------------------------------------------------//


        JPanel southJPanel = new JPanel(); // SOUTH PANEL
        southJPanel.setLayout(new GridLayout(1,4,5,5));
        southJPanel.add(abilitiesJPanel);
        southJPanel.add(statsJPanel);
        southJPanel.add(inventoryJPanel);
        southJPanel.add(mapJPanel);
        

        JButton attackButton = new JButton("Attack"); // ABILITY PANEL
        useAbilityButton = new JButton("Ability");
        JButton skipButton = new JButton("Skip");
        abilitiesJPanel.setLayout(new GridLayout(3,1,1,1));
        abilitiesJPanel.add(attackButton);
        abilitiesJPanel.add(useAbilityButton);
        abilitiesJPanel.add(skipButton);
        useAbilityButton.setToolTipText("Cooldown: 0");


        statsJPanel.setLayout(new GridLayout(1,1)); // STATS PANEL
        statsTextArea = new JTextArea("Health: 90\nDamage: 6\nAgility: 1\nDefence: 11");
        statsTextArea.setEditable(false);
        statsTextArea.setLineWrap(true);
        statsTextArea.setOpaque(false);
        statsTextArea.setWrapStyleWord(false);
        statsJPanel.add(statsTextArea);

        add(fightJPanel, BorderLayout.CENTER);
        add(southJPanel, BorderLayout.SOUTH);

        //JLabel lable = new JLabel();
        //lable.setText("This is where the text goes!");
        //add(lable);

        setVisible(true);
        ///pack();
    }

    public void update() {
        updateFightPanel(); // update fight panel
        //updateAblilityPanel(); // update ability panel
        // update stats panel
        // update invintory panel
        // update map panel
    }

    public void updateFightPanel(){
        List<Character> playersTeam = game.getPlayersTeam();
        List<Character> enemies = game.getMap().getCurrentEnemies();

        playersTeamJPanel.setLayout(new GridLayout(1,playersTeam.size(),10,10)); // playersTeam PANEL

        for (int i = playersTeam.size(); i > 0; i--) {
            Character character = game.getPlayersTeam().get(i);
            JLabel label = new JLabel();
            ImageIcon icon = new ImageIcon("Graphics/"+character.getClassName()+".png");

            label.setText("Graphics/"+character.getClassName()+".png");
            label.setIcon(icon);
            playersTeamJPanel.add(label);
        }

        currentEnemiesJPanel.setLayout(new GridLayout(1,enemies.size(),10,10)); // currentEnemies PANEL
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
}