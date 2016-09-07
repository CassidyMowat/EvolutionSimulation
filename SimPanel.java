package assignment22;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Cassidy Mowat
 */
public class SimPanel extends JPanel{
    
    private JButton[] buttons = new JButton[2];

    private DrawingPanel drawPanel;
    private JPanel controlPanel;
    private JTextField timeStep;
    private JTextField gen;
    private JLabel stepLabel;
    private JLabel genLabel;
    private Integer steps = SpeciesEvo.timestep;

    private Timer timer;
    private final int DELAY = SpeciesEvo.DELAY_LENGTH;
    
    public SimPanel() {
        
        buttons[0] = new JButton("Start");
        buttons[1] = new JButton("Stop");

        timeStep = new JTextField(2);
        stepLabel = new JLabel("Timestep: ");
        gen = new JTextField(2);
        genLabel = new JLabel("Generation: ");
        
        drawPanel = new DrawingPanel();

        // add all buttons to the control panel
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(100, 1000));

        // Create the button listeners
        ButtonListener listener = new ButtonListener();

        // Loop to add buttons to control panel and set up listeners
        for (JButton i : buttons) {
            controlPanel.add(i);
            i.addActionListener(listener);
        }

        controlPanel.add(stepLabel);
        controlPanel.add(timeStep);
        controlPanel.add(genLabel);
        controlPanel.add(gen);

        timer = new Timer(DELAY, listener);

        add(controlPanel);
        add(drawPanel);
    }
    
    private class DrawingPanel extends JPanel {

        public DrawingPanel() {
            setPreferredSize(new Dimension(1000, 1000));
            setBackground(Color.LIGHT_GRAY);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Creature creature : SpeciesEvo.creatureArray) {
                if(creature.getEnergy() > 0){
                    creature.display(g);
                }   
            }
            for (Monster monster : SpeciesEvo.monsterArray) {
                monster.display(g);
            }
            for (int row = 0; row <= SpeciesEvo.arraySize - 1; row++) {
                for (int col = 0; col <= SpeciesEvo.arraySize - 1; col++) {
                    if (SpeciesEvo.strawberryLoc[row][col] == 1){
                        SpeciesEvo.displayStraw(g, row, col);
                    }
                }
            }
            for (int row = 0; row <= SpeciesEvo.arraySize - 1; row++) {
                for (int col = 0; col <= SpeciesEvo.arraySize - 1; col++) {
                    if (SpeciesEvo.mushroomLoc[row][col] == 1){
                        SpeciesEvo.displayMush(g, row, col);
                    }
                }
            }
            repaint();
            
        }
    }
    
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == timer) {

                for (Creature creature : SpeciesEvo.creatureArray) {
                    creature.setEnergy(creature.getEnergy() - SpeciesEvo.ENERGY_CHANGE);
                    creature.actionResult(creature.actionSelect());
                }
                for (Monster monster : SpeciesEvo.monsterArray) {
                    if(SpeciesEvo.timestep % 2 == 0){
                        monster.performAction();
                    }
                }
                
                if(SpeciesEvo.timestep == SpeciesEvo.genLength){
                    SpeciesEvo.bestCreatureOne.setEnergy(0);
                    SpeciesEvo.bestCreatureTwo.setEnergy(0);
                    for (Creature creature : SpeciesEvo.creatureArray) {
                        
                        if(creature.getEnergy() > SpeciesEvo.bestCreatureOne.getEnergy()){
                            SpeciesEvo.bestCreatureOne = creature;
                        } else if(creature.getEnergy() > SpeciesEvo.bestCreatureTwo.getEnergy()){
                            SpeciesEvo.bestCreatureTwo = creature;
                        }
                        
                        SpeciesEvo.averageEnergy += creature.getEnergy();
                    }
                    SpeciesEvo.averageEnergy = SpeciesEvo.averageEnergy / SpeciesEvo.creatureArray.size();
                    //System.out.println("Average energy for generation: " + SpeciesEvo.generation + " Energy: " + SpeciesEvo.averageEnergy);
                    System.out.print(SpeciesEvo.averageEnergy + " ");
                    
                    for (int i = 0; i <= SpeciesEvo.arraySize - 1; i++) {
                        for (int x = 0; x <= SpeciesEvo.arraySize - 1; x++) {
                            if (SpeciesEvo.creatureLoc[i][x] == 1) {
                                SpeciesEvo.creaturesAlive++;
                            }
                        }
                    }
                    //System.out.println("Creatures alive for generation: " + SpeciesEvo.generation + " Number: " + SpeciesEvo.creaturesAlive);
                    
                    SpeciesEvo.averageEnergy = 0.0;
                    SpeciesEvo.creaturesAlive = 0;
                    SpeciesEvo.generation++;
                    SpeciesEvo.timestep = 0;
                    
                    SpeciesEvo.setupGen();
                }
                SpeciesEvo.timestep++;
                
            } else {
                JButton button = (JButton) event.getSource();

                // Starts or stops the timer
                if (button.getText().equals("Start")) {
                    timer.start();
                }
                if (button.getText().equals("Stop")) {
                    timer.stop();
                }
            }
            timeStep.setText(SpeciesEvo.timestep.toString());
            gen.setText(SpeciesEvo.generation.toString());
        }
    }
}
