package assignment22;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Cassidy Mowat
 */
public class SpeciesEvo {

    public static final Integer arraySize = 100;
    //public static final Integer arraySize = 50;
    
    public static Integer[][] creatureLoc = new Integer[arraySize][arraySize];
    public static Integer[][] monsterLoc = new Integer[arraySize][arraySize];
    public static Integer[][] strawberryLoc = new Integer[arraySize][arraySize];   
    public static Integer[][] mushroomLoc = new Integer[arraySize][arraySize];
    public static ArrayList<Creature> creatureArray = new ArrayList();
    public static ArrayList<Monster> monsterArray = new ArrayList();
    
    public static final Integer SIZE = 10;
    public static final Integer Display_OFFSET = 10;
//    public static final Integer Display_OFFSET = 20;
//    public static final Integer SIZE = 20;
    public static final Integer DELAY_LENGTH = 1;
    public static final Integer ENERGY_CHANGE = 3;
    public static final Integer MAX_ENERGY = 150;
    public static final Integer ENERGY_RESTORE = 70;
    
    public static final Integer NUM_CREATURES = 800;
    public static final Integer NUM_MONSTERS = 500;
    public static final Integer NUM_STRAWBERRYS = 1000;
    public static final Integer NUM_MUSHROOMS = 600;
    
//    public static final Integer NUM_CREATURES = 100;
//    public static final Integer NUM_MONSTERS = 50;
//    public static final Integer NUM_STRAWBERRYS = 200;
//    public static final Integer NUM_MUSHROOMS = 150;
    
    public static Creature bestCreatureOne = new Creature();
    public static Creature bestCreatureTwo = new Creature();
    
   
    public static Integer timestep = 0;
    public static Double averageEnergy = 0.0;
    public static Integer generation = 0;
    public static Integer genLength = 32;
    public static Integer creaturesAlive = 0;
    
    public enum Direction {
        NORTH, SOUTH, EAST, WEST,  NULL
    }
    
    public enum Type {
        CREATURE, MONSTER, STRAWBERRY, MUSHROOM
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        setup();
        
        JFrame simFrame = new JFrame();
        simFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        simFrame.getContentPane().add(new SimPanel());
        simFrame.pack();
        simFrame.setVisible(true);
        simFrame.setTitle("Species Simulation");
        
    }
    
    public static void displayStraw(Graphics g, Integer x, Integer y) {
        g.setColor(new Color(0, 255, 0));
        g.fillOval(x * Display_OFFSET, y * Display_OFFSET, SIZE, SIZE);
    }

    public static void displayMush(Graphics g, Integer x, Integer y) {
        g.setColor(new Color(255, 0, 0));
        g.fillOval(x * Display_OFFSET, y * Display_OFFSET, SIZE, SIZE);
    }
    
    public static void setup(){
        
        Random rnd = new Random();
        
        for (int i = 0; i <= arraySize - 1; i++) {
            for (int x = 0; x <= arraySize - 1; x++) {
                monsterLoc[i][x] = 0;
                creatureLoc[i][x] = 0;
                strawberryLoc[i][x] = 0;
                mushroomLoc[i][x] = 0;
            }
        }

        for (int i = 0; i <= NUM_CREATURES - 1; i++) {
            creatureArray.add(new Creature());
        }
        for (int i = 0; i <= NUM_MONSTERS - 1; i++) {
            monsterArray.add(new Monster());
        }

        for (Creature creature : creatureArray) {
            creatureLoc[creature.getxLoc()][creature.getyLoc()] = 1;
        }
        for (Monster monster : monsterArray) {
            monsterLoc[monster.getxLoc()][monster.getyLoc()] = 1;
        }
        
        for (int i = 0; i <= NUM_STRAWBERRYS - 1; i++){
            strawberryLoc[(rnd.nextInt(arraySize) + 1) - 1][(rnd.nextInt(arraySize) + 1) - 1] = 1;
        }
        
        for (int i = 0; i <= NUM_MUSHROOMS - 1; i++){
            Integer x = (rnd.nextInt(arraySize) + 1) - 1;
            Integer y = (rnd.nextInt(arraySize) + 1) - 1;
            
            while((strawberryLoc[x][y] == 1)){
                x = (rnd.nextInt(arraySize) + 1) - 1;
                y = (rnd.nextInt(arraySize) + 1) - 1;
            }  
            
            mushroomLoc[x][y] = 1;
        }
    }
    
    public static void setupGen(){
        
        Random rnd = new Random();
        
        creatureArray.clear();
        monsterArray.clear();
        
        for (int i = 0; i <= arraySize - 1; i++) {
            for (int x = 0; x <= arraySize - 1; x++) {
                monsterLoc[i][x] = 0;
                creatureLoc[i][x] = 0;
                strawberryLoc[i][x] = 0;
                mushroomLoc[i][x] = 0;
            }
        }

        for (int i = 0; i <= NUM_CREATURES - 1; i++) {
            creatureArray.add(new Creature(bestCreatureOne, bestCreatureTwo));
        }
        for (int i = 0; i <= NUM_MONSTERS - 1; i++) {
            monsterArray.add(new Monster());
        }

        for (Creature creature : creatureArray) {
            creatureLoc[creature.getxLoc()][creature.getyLoc()] = 1;
        }
        for (Monster monster : monsterArray) {
            monsterLoc[monster.getxLoc()][monster.getyLoc()] = 1;
        }
        
        for (int i = 0; i <= NUM_STRAWBERRYS - 1; i++){
            strawberryLoc[(rnd.nextInt(arraySize) + 1) - 1][(rnd.nextInt(arraySize) + 1) - 1] = 1;
        }
        
        for (int i = 0; i <= NUM_MUSHROOMS - 1; i++){
            Integer x = (rnd.nextInt(arraySize) + 1) - 1;
            Integer y = (rnd.nextInt(arraySize) + 1) - 1;
            
            while((strawberryLoc[x][y] == 1)){
                x = (rnd.nextInt(arraySize) + 1) - 1;
                y = (rnd.nextInt(arraySize) + 1) - 1;
            }  
            
            mushroomLoc[x][y] = 1;
        }
    }
    
}
